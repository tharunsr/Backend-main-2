package com.example.SS.util;

import com.example.SS.service.PersonServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        String role = null;

        System.out.println("Reached \n"+authHeader);
        if(authHeader!= null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
           //  jwtService.extractBegin(token);
             email=jwtService.extractEmail(token);
             role = jwtService.extractRole(token);

            System.out.println("Extraction Done:\nEmail: " + email + "\nRole: " + role);

        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = context.getBean(PersonServiceImpl.class).loadUserByUsername(email);

            if(jwtService.validateToken(token,userDetails)){

                UsernamePasswordAuthenticationToken upat = new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }

        }
        filterChain.doFilter(request,response);
    }
}
