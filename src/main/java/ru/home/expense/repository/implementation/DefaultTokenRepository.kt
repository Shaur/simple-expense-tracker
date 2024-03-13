package ru.home.expense.repository.implementation

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.home.expense.model.Token
import ru.home.expense.repository.TokenRepository

@Repository
open class DefaultTokenRepository(private val manager: EntityManager) : TokenRepository {

    override fun findByValue(token: String): Token? {
        val query = manager.createQuery("select t from Token t where t.value = :value", Token::class.java)
        query.setParameter("value", token)

        return query.resultList.firstOrNull()
    }

    @Transactional
    override fun create(name: String, key: String, expiration: Long) {
        val existsToken = findByUsername(name)
        if (existsToken != null) {
            manager.remove(existsToken)
        }

        val userId = manager.createQuery("select u.id from User u where u.name = :name", Long::class.java).setParameter(name, expiration)
            .setParameter("name", name)
            .singleResult

        manager.merge(Token(key, name, userId, expiration))
    }

    fun findByUsername(username: String): Token? {
        val query = manager.createQuery("select t from Token t where t.name = :name", Token::class.java)
        query.setParameter("name", username)

        return query.resultList.firstOrNull()
    }
}