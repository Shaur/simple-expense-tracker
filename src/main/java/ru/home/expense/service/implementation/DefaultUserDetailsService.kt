package ru.home.expense.service.implementation

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import ru.home.expense.configuration.properties.AuthenticationProperties
import ru.home.expense.dto.RegisterUserDto
import ru.home.expense.dto.TokenDto
import ru.home.expense.dto.UserDto
import ru.home.expense.exception.UserAlreadyExistsException
import ru.home.expense.model.Role
import ru.home.expense.repository.TokenRepository
import ru.home.expense.repository.UserRepository
import ru.home.expense.service.UserService
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

@Service
class DefaultUserDetailsService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    authentificationProperties: AuthenticationProperties
) : UserService {

    private val secretKey = authentificationProperties.secretKey

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username) ?: throw UsernameNotFoundException("User $username not found")
        return User(user.name, user.password, listOf<GrantedAuthority>(user.role))
    }

    override fun authenticate(username: String, password: String): TokenDto {
        val isValid = verify(username, password)
        if (!isValid) {
            throw UsernameNotFoundException("User $username or password not found")
        }

        return createToken(username)
    }

    override fun register(registerUserDto: RegisterUserDto): UserDto {
        val user = userRepository.findByName(registerUserDto.username)
        if (user != null) {
            throw UserAlreadyExistsException(registerUserDto.username)
        }

        val salt = BCrypt.gensalt()
        val encryptedPassword = BCrypt.hashpw(registerUserDto.password, salt)
        val newUser = ru.home.expense.model.User(
            null,
            registerUserDto.username,
            encryptedPassword,
            salt,
            Role.USER
        )

        userRepository.save(newUser)
        return UserDto(registerUserDto.username)
    }

    override fun verify(name: String, password: String): Boolean {
        val user = userRepository.findByName(name) ?:return false
        if (user.password != BCrypt.hashpw(password, user.salt)) {
            return false
        }

        return true
    }

    override fun createToken(username: String): TokenDto {
        val hmacKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
        val expiration = Date() + 10.days
        val key = Jwts.builder()
            .expireAfter(10.days)
            .signWith(hmacKey)
            .compact();

        tokenRepository.create(username, key, expiration.time)

        return TokenDto(
            name = username,
            token = key,
            expireAt = expiration.time
        )
    }

    private fun JwtBuilder.expireAfter(duration: Duration) = this.expiration(Date() + duration)

    operator fun Date.plus(duration: Duration): Date {
        return Date(this.time + duration.inWholeMilliseconds)
    }
}