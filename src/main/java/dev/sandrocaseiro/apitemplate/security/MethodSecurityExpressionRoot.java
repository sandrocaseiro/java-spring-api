package dev.sandrocaseiro.apitemplate.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class MethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final UserPrincipal principal;

    public MethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
        principal = (UserPrincipal) authentication.getPrincipal();
    }

    public boolean isGroup(int groupId) {
        return principal.getGroupId() == groupId;
    }

    public boolean canAccessUser(int userId) {
        //Example only
        return userId < 99;
    }

    public boolean hasRoles() {
        return !principal.getRoles().isEmpty();
    }

    @Override
    public void setFilterObject(Object o) {
        //Method not needed
    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {
        //Method not needed
    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
