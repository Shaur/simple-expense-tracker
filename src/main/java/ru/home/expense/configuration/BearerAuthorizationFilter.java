package ru.home.expense.configuration;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.home.expense.configuration.properties.AuthenticationProperties;
import ru.home.expense.repository.TokenRepository;

import java.io.IOException;

@Component
public class BearerAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenRepository tokenRepository;

    private final String secret;

    public BearerAuthorizationFilter(
            TokenRepository tokenRepository,
            AuthenticationProperties properties
    ) {
        this.tokenRepository = tokenRepository;
        this.secret = properties.getSecretKey();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var accessToken = resolveToken(request);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var hmacKey = Keys.hmacShaKeyFor(secret.getBytes("UTF-8"));
        try {
            var parser = Jwts.parser()
                    .verifyWith(hmacKey)
                    .build();

            parser.parse(accessToken);
        } catch (JwtException | IllegalArgumentException e) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = tokenRepository.findByValue(accessToken);
        if (token == null || token.expireAt() < System.currentTimeMillis()) {
            throw new CredentialsExpiredException("Token expired");
        }

        Authentication authentication = new AuthenticationContext(token.name(), token.userId());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

}
