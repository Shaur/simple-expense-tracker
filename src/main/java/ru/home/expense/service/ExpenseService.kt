package ru.home.expense.service

import ru.home.expense.dto.ExpenseCreateDto
import ru.home.expense.dto.ExpenseDto
import ru.home.expense.dto.ExpenseUpdateDto
import java.time.LocalDate

interface ExpenseService {

    fun create(createDto: ExpenseCreateDto): ExpenseDto

    fun find(from: Long?, to: Long?, limit: Long, offset: Long): List<ExpenseDto>

    fun findById(id: Long): ExpenseDto?

    fun delete(id: Long)

}

//fun update(updateDto: ExpenseUpdateDto): ExpenseDto?
