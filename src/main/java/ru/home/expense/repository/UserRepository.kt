package ru.home.expense.repository

import ru.home.expense.model.User

interface UserRepository {

    fun findByName(name: String): User?

    fun getById(id: Long): User

    fun save(user: User)
}