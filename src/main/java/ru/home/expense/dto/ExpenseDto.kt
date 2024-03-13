package ru.home.expense.dto

data class ExpenseDto(
    val id: Long,
    val amount: Long,
    val creationTime: Long = System.currentTimeMillis(),
    val currency: String,
    val category: CategoryDto? = null
)
