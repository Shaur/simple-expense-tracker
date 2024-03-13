package ru.home.expense.utils;

import ru.home.expense.dto.CategoryDto;
import ru.home.expense.dto.ExpenseDto;
import ru.home.expense.model.Expense;

public class ExpenseConverterJava {

    public static ExpenseDto convertTo(Expense expense) {
        return new ExpenseDto(
                expense.id(),
                expense.amount(),
                expense.time(),
                expense.currency(),
                new CategoryDto(
                        expense.category().id(),
                        expense.category().name()
                )
        );
    }

}
