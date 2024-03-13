package ru.home.expense.repository;

import ru.home.expense.model.Token;

public interface TokenRepository {

    Token findByValue(String token);

    void create(String name, String key, Long expiration);

}
