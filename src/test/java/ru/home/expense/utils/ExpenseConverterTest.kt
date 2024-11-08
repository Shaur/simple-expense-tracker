package ru.home.expense.utils

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import ru.home.expense.dto.CategoryDto
import ru.home.expense.dto.ExpenseDto
import ru.home.expense.model.Category
import ru.home.expense.model.Expense
import ru.home.expense.model.Role
import ru.home.expense.model.User

class ExpenseConverterTest {

    private val user = User(1L, "TestUser", "", "", Role.USER)

    private val expense = Expense(
        1L,
        user,
        Category(1L, "Goods", user),
        1000L,
        100L,
        "USD"
    )

    private val expectedDto = ExpenseDto(
        id = 1L,
        amount = 1000L,
        creationTime = 100L,
        currency = "USD",
        category = CategoryDto(1L, "Goods")
    )

    @Test
    fun `convert expense to dto by extension`() {
        assertThat(expense.toDto()).isEqualTo(expectedDto)
    }
}