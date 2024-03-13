package ru.home.expense.service

import org.springframework.security.core.userdetails.UserDetailsService
import ru.home.expense.dto.RegisterUserDto
import ru.home.expense.dto.TokenDto
import ru.home.expense.dto.UserDto

interface UserService : UserDetailsService {

    fun verify(name: String, password: String): Boolean

    fun createToken(username: String): TokenDto

    fun authenticate(username: String, password: String): TokenDto

    fun register(registerUserDto: RegisterUserDto): UserDto

}