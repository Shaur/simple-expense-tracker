package ru.home.expense.repository.implementation;

import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.home.expense.model.Expense;
import ru.home.expense.repository.ExpenseRepository;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class DefaultExpenseRepository implements ExpenseRepository {

    private final EntityManager manager;

    public DefaultExpenseRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Expense> find(Long from, Long to, Long userId, Long limit, Long offset) {
        return List.of();
    }

    @Override
    public Expense findById(Long id) {
        var query = manager.createQuery("select e from Expense e where e.id = :id", Expense.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Expense create(Expense expense) {
        return manager.merge(expense);
    }

    @Override
    public void delete(Long id) {
        manager.createQuery("delete from Expense e where e.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<Expense> findAll() {
        return manager.createQuery("select e from Expense e", Expense.class).getResultList();
    }

    public List<Expense> findAll(Function<Expense, Expense> modify) {
        return findAll().stream().map(modify).collect(Collectors.toUnmodifiableList());
    }

    public List<Expense> findAll(BiFunction<Expense, Long, Expense> modify) {
        return findAll().stream()
                .map( expense -> modify.apply(expense, System.currentTimeMillis()))
                .collect(Collectors.toUnmodifiableList());
    }
}
