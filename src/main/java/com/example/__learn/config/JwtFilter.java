package com.example.__learn.config;

import com.example.__learn.service.JwtService;
import com.example.__learn.service.MyUserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private MyUserService myUserService;

    public JwtFilter(JwtService jwtService, MyUserService myUserService) {
        this.jwtService = jwtService;
        this.myUserService = myUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String splitToken = header.substring(7);

            String userEmail = jwtService.extractUserEmail(splitToken);

            if(!userEmail.isBlank()){
                UserDetails userDetails = myUserService.loadUserByUsername(userEmail);

                if(jwtService.validateToken(splitToken, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities() ); //
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
