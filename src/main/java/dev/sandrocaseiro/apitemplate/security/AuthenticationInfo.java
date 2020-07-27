package dev.sandrocaseiro.apitemplate.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationInfo implements IAuthenticationInfo {
    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public Integer getId() {
        if (!isAuthenticated())
            return null;

        return ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
