package com.sandrocaseiro.apitemplate.filters;

import com.sandrocaseiro.apitemplate.properties.JwtProperties;
import com.sandrocaseiro.apitemplate.security.TokenUser;
import com.sandrocaseiro.apitemplate.security.UserPrincipal;
import com.sandrocaseiro.apitemplate.services.JwtAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtAuthService jwtAuthService;
    private final JwtProperties jwtProperties;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtAuthService jwtAuthService, JwtProperties jwtProperties) {
        super(authenticationManager);
        this.jwtAuthService = jwtAuthService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.isEmpty(header) || !header.startsWith(jwtProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(getAuthentication(header));
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        TokenUser tokenUser = jwtAuthService.parseBearerToken(header.replace(jwtProperties.getTokenPrefix() + " ", ""));
        UserPrincipal principal = new UserPrincipal(
            tokenUser.getId(),
            tokenUser.getName(),
            tokenUser.getUsername(),
            tokenUser.getGroupId(),
            new HashSet<>(tokenUser.getRoles())
        );

        return new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
    }
}
