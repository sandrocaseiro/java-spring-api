package com.sandrocaseiro.apitemplate.filters;

import com.sandrocaseiro.apitemplate.exceptions.UnauthorizedException;
import com.sandrocaseiro.apitemplate.exceptions.UnsupportedMediaTypeException;
import com.sandrocaseiro.apitemplate.properties.JwtProperties;
import com.sandrocaseiro.apitemplate.security.TokenAuthResponse;
import com.sandrocaseiro.apitemplate.security.TokenUser;
import com.sandrocaseiro.apitemplate.services.JwtAuthService;
import com.sandrocaseiro.apitemplate.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthService jwtAuthService;
    private final JwtProperties jwtProperties;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(jwtProperties.getHeader());
        if (!StringUtils.isEmpty(header) && header.startsWith(jwtProperties.getTokenPrefix())) {
            String token = header.replace(jwtProperties.getTokenPrefix() + " ", "");
            if (jwtAuthService.isRefreshToken(token)) {
                TokenUser principal = jwtAuthService.parseBearerToken(token);
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList()));
            }
        }

        if (!request.getContentType().startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
            throw new UnsupportedMediaTypeException();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException {
        TokenUser user = (TokenUser) authResult.getPrincipal();
        TokenAuthResponse tokenResp = jwtAuthService.generateTokenResponse(user);

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtil.serialize(tokenResp));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        throw new UnauthorizedException(failed);
    }
}
