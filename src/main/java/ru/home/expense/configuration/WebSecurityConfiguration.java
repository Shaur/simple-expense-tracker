package ru.home.expense.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.home.expense.configuration.properties.AuthenticationProperties;
import ru.home.expense.repository.TokenRepository;
import ru.home.expense.repository.UserRepository;
import ru.home.expense.service.implementation.DefaultUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            AuthenticationProperties properties
    ) {
        return new DefaultUserDetailsService(userRepository, tokenRepository, properties);
    }

}
