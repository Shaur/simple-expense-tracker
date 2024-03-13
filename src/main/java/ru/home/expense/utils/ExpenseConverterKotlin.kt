package ru.home.expense.utils

import ru.home.expense.dto.CategoryDto
import ru.home.expense.dto.ExpenseDto
import ru.home.expense.model.Expense

object ExpenseConverterKotlin {

    fun convertTo(expense: Expense): ExpenseDto {
        return ExpenseDto(
            id = expense.id,
            amount = expense.amount,
            currency = expense.currency,
            creationTime = expense.time,
            category = CategoryDto(expense.category.id, expense.category.name)
        )
    }

}