package ru.home.expense.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.home.expense.model.Category

@Repository
interface CategoryRepository : CrudRepository<Category, Long> {

    fun findByIdAndUserId(id: Long, userId: Long): Category?

}