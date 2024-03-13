package ru.home.expense.exception

class UserAlreadyExistsException(username: String) : RuntimeException("User $username already exists")