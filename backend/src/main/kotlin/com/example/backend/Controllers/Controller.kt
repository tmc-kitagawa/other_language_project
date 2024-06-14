package com.example.backend.Controllers

import com.example.backend.Repositories.Repository
import com.example.backend.dataClass.User
import com.example.backend.dataClass.UserRequest
import org.springframework.web.bind.annotation.*

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class Controller(val repository: Repository) {

    @GetMapping("/api/users")
    fun getUsers(): List<User> {
        return repository.getUsers()
    }

    @PostMapping("/api/users")
    fun saveUser(@RequestBody userRequest: UserRequest): String {
        return repository.saveUser(userRequest)
    }

    @DeleteMapping("/api/users/{username}")
    fun deleteUser(@PathVariable("username") username: String): String {
        return repository.deleteUser(username)
    }
}