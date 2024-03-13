package ru.home.expense.utils

import ru.home.expense.dto.CategoryDto
import ru.home.expense.model.Category

fun Category.toDto(): CategoryDto = CategoryDto(this.id, this.name)