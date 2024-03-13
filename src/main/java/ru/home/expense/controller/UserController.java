package ru.home.expense.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.expense.dto.CredentialsDto;
import ru.home.expense.dto.RegisterUserDto;
import ru.home.expense.dto.TokenDto;
import ru.home.expense.dto.UserDto;
import ru.home.expense.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userDetailsService;

    public UserController(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody CredentialsDto credentials) {
        return userDetailsService.authenticate(credentials.name(), credentials.password());
    }

    @PostMapping
    public UserDto register(@RequestBody RegisterUserDto registerUserDto) {
        return userDetailsService.register(registerUserDto);
    }

}
