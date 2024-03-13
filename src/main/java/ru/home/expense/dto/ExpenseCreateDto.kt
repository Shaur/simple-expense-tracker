package ru.home.expense.dto

data class ExpenseCreateDto(
    val amount: Long,
    val currency: String,
    val categoryId: Long? = null
)
