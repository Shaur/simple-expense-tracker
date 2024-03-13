package ru.home.expense.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "authentication")
data class AuthenticationProperties(
    val secretKey: String
)
