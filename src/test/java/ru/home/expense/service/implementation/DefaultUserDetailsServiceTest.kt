package ru.home.expense.service.implementation

import io.jsonwebtoken.Jwts
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.any
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import ru.home.expense.model.User
import org.springframework.security.crypto.bcrypt.BCrypt
import ru.home.expense.configuration.properties.AuthenticationProperties
import ru.home.expense.model.Role
import ru.home.expense.repository.TokenRepository
import ru.home.expense.repository.UserRepository

class DefaultUserDetailsServiceTest {

    private val userRepository = mock(UserRepository::class.java)

    private val tokenRepository = mock(TokenRepository::class.java)

    private val service = DefaultUserDetailsService(
        userRepository = userRepository,
        tokenRepository = tokenRepository,
        authentificationProperties = AuthenticationProperties(secretKey = String(Jwts.SIG.HS256.key().build().encoded))
    )

    @Test
    fun `exists user credentials verifications`() {
        val now = System.currentTimeMillis()

        val salt = BCrypt.gensalt()
        val password = BCrypt.hashpw(PASSWORD, salt)
        val user = User(1L, USERNAME, password, salt, Role.USER)

        `when`(userRepository.findByName(USERNAME)).thenReturn(user)

        val isValid = service.verify(USERNAME, PASSWORD)
        assertThat(isValid).isTrue

        val dto = service.createToken(USERNAME)
        assertThat(dto.name).isEqualTo(USERNAME)
        assertThat(dto.token).isNotNull
        assertThat(dto.expireAt).isGreaterThan(now)

        verify(tokenRepository).create(eq(USERNAME), any(), anyLong())
    }

    companion object {
        private const val USERNAME = "user"
        private const val PASSWORD = "password"
    }
}