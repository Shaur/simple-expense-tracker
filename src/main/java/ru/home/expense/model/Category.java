package ru.home.expense.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public record Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,

        String name,

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        User user
) {
}
