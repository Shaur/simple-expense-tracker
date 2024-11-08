package ru.home.expense.configuration

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.home.expense.configuration.properties.AuthenticationProperties
import ru.home.expense.repository.TokenRepository
import java.nio.charset.StandardCharsets

@Component
class BearerAuthorizationFilter(
    private val tokenRepository: TokenRepository,
    properties: AuthenticationProperties
) : OncePerRequestFilter() {

    private val secret = properties.secretKey

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = resolveToken(request)
        if (accessToken == null) {
            filterChain.doFilter(request, response)
            return
        }

        val hmacKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        try {
            val parser = Jwts.parser()
                .verifyWith(hmacKey)
                .build()

            parser.parse(accessToken)
        } catch (e: RuntimeException) {
            filterChain.doFilter(request, response)
            return
        }

        val token = tokenRepository.findByValue(accessToken)
        if (token == null || token.expireAt < System.currentTimeMillis()) {
            throw CredentialsExpiredException("Token expired")
        }

        val authentication: Authentication = AuthenticationContext(token.name, token.userId)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(TOKEN_HEADER)
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length)
        }

        return null
    }

    companion object {
        private const val TOKEN_HEADER = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
    }
}
