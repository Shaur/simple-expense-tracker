package ru.home.expense.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class AuthenticationContext implements Authentication {

    private final String principal;

    private final Long userId;

    private boolean isAuthenticated = false;

    public AuthenticationContext(String username, Long userId) {
        this.principal = username;
        this.userId = userId;
        isAuthenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Long getDetails() {
        return userId;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal;
    }
}
