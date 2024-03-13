package ru.home.expense.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.home.expense.configuration.properties.AuthenticationProperties

@EnableConfigurationProperties(
    AuthenticationProperties::class
)
@Configuration
class PropertiesConfiguration