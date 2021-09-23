package io.github.godsantos.security.jwt;

import io.github.godsantos.service.impl.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserServiceImpl userServiceImpl;

    public JwtAuthFilter( JwtService jwtService, UserServiceImpl userServiceImpl ) {
        this.jwtService = jwtService;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if( authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);

            if(isValid){
                String loginUser = jwtService.getUserLogin(token);
                UserDetails user = userServiceImpl.loadUserByUsername(loginUser);
                UsernamePasswordAuthenticationToken userAuth = new
                        UsernamePasswordAuthenticationToken(user,null,
                        user.getAuthorities());
                userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(userAuth);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
