package ru.home.expense.utils

import ru.home.expense.dto.CategoryDto
import ru.home.expense.dto.ExpenseDto
import ru.home.expense.model.Expense

fun Expense.toDto(): ExpenseDto {
    return ExpenseDto(
        id = this.id,
        amount = this.amount,
        currency = this.currency,
        creationTime = this.time,
        category = this.category?.toDto()
    )
}