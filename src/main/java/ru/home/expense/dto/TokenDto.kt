package ru.home.expense.dto

data class TokenDto(
    val name: String,
    val token: String,
    val expireAt: Long
)
