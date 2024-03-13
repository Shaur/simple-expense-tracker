package ru.home.expense.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public record User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,

        String name,

        String password,

        String salt,

        @Enumerated(value = EnumType.STRING)
        Role role
) {

}
