package ru.home.expense.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExpenseDtoTest {

    @Test
    fun `copy dto test`() {
        val dto = ExpenseDto(
            id = 1,
            amount = 1000L,
            currency = "USD",
            category = CategoryDto(1L, "Goods")
        )

        val copy = dto.copy(amount = 2000L)
        assertEquals(copy.amount, 2000L)
        println(copy)
    }

}