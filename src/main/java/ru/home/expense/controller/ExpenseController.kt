package ru.home.expense.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.home.expense.dto.ExpenseCreateDto
import ru.home.expense.dto.ExpenseDto
import ru.home.expense.service.ExpenseService

@RestController
@RequestMapping("/expense")
class ExpenseController(private val service: ExpenseService) {

    @PostMapping
    fun create(@RequestBody createDto: ExpenseCreateDto): ExpenseDto = service.create(createDto)
}