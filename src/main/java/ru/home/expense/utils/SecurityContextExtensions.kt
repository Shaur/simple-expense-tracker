package ru.home.expense.utils

import org.springframework.security.core.context.SecurityContext
import ru.home.expense.configuration.AuthenticationContext

fun SecurityContext.getUserId(): Long? {
    val authentication = this.authentication
    if (authentication !is AuthenticationContext) {
        return null
    }

    return authentication.details
}