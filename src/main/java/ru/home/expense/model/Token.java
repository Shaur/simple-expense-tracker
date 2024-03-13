package ru.home.expense.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public record Token(
        @Id
        String value,
        String name,
        Long userId,
        Long expireAt
) {
}
