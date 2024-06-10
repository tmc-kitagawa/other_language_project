package com.example.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(val repository: Repository) {

    @GetMapping("/users")
    fun getUsers(): List<User> {
        return repository.getUsers()
    }

    @PostMapping("/users")
    fun saveUser(@RequestBody userRequest: UserRequest): String {
        return repository.saveUser(userRequest)
    }
}