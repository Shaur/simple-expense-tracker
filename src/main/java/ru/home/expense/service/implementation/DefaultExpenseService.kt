package ru.home.expense.service.implementation

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.home.expense.dto.ExpenseCreateDto
import ru.home.expense.dto.ExpenseDto
import ru.home.expense.model.Expense
import ru.home.expense.repository.CategoryRepository
import ru.home.expense.repository.ExpenseRepository
import ru.home.expense.repository.UserRepository
import ru.home.expense.service.ExpenseService
import ru.home.expense.utils.getUserId
import ru.home.expense.utils.toDto
import java.util.function.BiFunction
import java.util.function.Function
import java.util.stream.Collectors
import javax.security.auth.login.AccountNotFoundException

@Service
class DefaultExpenseService(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ExpenseService {

    override fun create(createDto: ExpenseCreateDto): ExpenseDto {
        val userId = SecurityContextHolder.getContext().getUserId() ?: throw AccountNotFoundException()
        val (amount, currency, categoryId) = createDto

        val category = categoryId?.let {
            categoryRepository.findById(it).orElse(null)
        }

        val expense = Expense(
            null,
            userRepository.getById(userId),
            category,
            amount,
            System.currentTimeMillis(),
            currency
        )

        return expenseRepository.create(expense).toDto()
    }

    override fun find(from: Long?, to: Long?, limit: Long, offset: Long): List<ExpenseDto> {
        val userId = SecurityContextHolder.getContext().getUserId() ?: throw AccountNotFoundException()
        return expenseRepository.find(from, to, userId, limit, offset).map(Expense::toDto)
    }

    override fun findById(id: Long): ExpenseDto? {
        return expenseRepository.findById(id)?.let(Expense::toDto)
    }

    override fun delete(id: Long) = expenseRepository.delete(id)

    fun findAll(modify: (Expense) -> Expense): List<Expense> {
        return expenseRepository.findAll().map(modify)
    }

    fun findAll(modify: (Expense, Long) -> Expense): List<Expense> {
        return expenseRepository.findAll()
            .map { expense -> modify(expense, System.currentTimeMillis()) }
    }
}
