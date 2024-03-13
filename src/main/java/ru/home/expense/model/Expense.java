package ru.home.expense.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public record Expense(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        User user,

        @OneToOne
        @JoinColumn(name = "category_id", referencedColumnName = "id")
        Category category,

        Long amount,

        Long time,

        String currency
) {
}
