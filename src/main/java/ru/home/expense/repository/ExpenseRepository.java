package ru.home.expense.repository;

import ru.home.expense.model.Expense;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ExpenseRepository {

    List<Expense> find(Long from, Long to, Long userId, Long limit, Long offset);

    Expense findById(Long id);

    Expense create(Expense expense);

    void delete(Long id);

    List<Expense> findAll();

}
