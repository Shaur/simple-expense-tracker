package ru.home.expense.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityFilterChainConfoguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf(CsrfConfigurer<HttpSecurity>::disable) //it.disable()
            .authorizeHttpRequests { requests ->
                requests.requestMatchers("/user/**").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }
}