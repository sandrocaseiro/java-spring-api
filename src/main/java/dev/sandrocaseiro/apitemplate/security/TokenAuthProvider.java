package dev.sandrocaseiro.apitemplate.security;

import dev.sandrocaseiro.apitemplate.services.JwtAuthService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TokenAuthProvider implements AuthenticationProvider {
    private final JwtAuthService jwtAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication.getPrincipal() instanceof TokenUser)
            return authentication;

        TokenUser user = (TokenUser)jwtAuthService.loadUserByUsername(authentication.getPrincipal().toString());

        if (StringUtils.isBlank(authentication.getCredentials().toString())
            || !CustomPasswordEncoder.getInstance().matches((CharSequence)authentication.getCredentials(), user.getPassword()))
            throw new BadCredentialsException("Invalid credentials");

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
