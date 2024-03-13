package ru.home.expense.repository.implementation

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.home.expense.model.User
import ru.home.expense.repository.UserRepository

@Repository
class DefaultUserRepository(private val manager: EntityManager) : UserRepository {

    override fun findByName(name: String): User? {
        val query = manager.createQuery("select u from users u where name = :name", User::class.java)
        query.setParameter("name", name)

        return query.resultList.firstOrNull()
    }

    override fun getById(id: Long): User {
        return manager.createQuery("select u from users u where id = :id", User::class.java)
            .setParameter("id", id)
            .resultList
            .first()
    }

    @Transactional
    override fun save(user: User) {
        manager.persist(user)
    }
}