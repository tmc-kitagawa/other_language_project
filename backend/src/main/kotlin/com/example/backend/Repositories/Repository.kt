package com.example.backend.Repositories

import com.example.backend.dataClass.User
import com.example.backend.dataClass.UserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestBody
import java.sql.ResultSet

@Component
class UserRowMapper: RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        return User(rs.getLong(1), rs.getString(2))
    }
}

@Repository
class Repository(
    @Autowired val jdbcTemplate: JdbcTemplate,
    @Autowired val userRowMapper: UserRowMapper
) {
    fun getUsers(): List<User> {
        return jdbcTemplate.query("SELECT id, username FROM users", userRowMapper)
    }

    fun saveUser(@RequestBody userRequest: UserRequest): String {
        jdbcTemplate.update("INSERT INTO users (username) VALUES (?)", userRequest.username)
        return "ok"
    }

    fun deleteUser(username: String): String {
        jdbcTemplate.update("DELETE FROM authorities WHERE username = (?)", username)
        jdbcTemplate.update("DELETE FROM users WHERE username = (?)", username)
        return "ok"
    }
}