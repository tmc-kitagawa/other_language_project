package com.example.backend

import com.example.backend.dataClass.CreateUserRequest
import org.springframework.web.bind.annotation.*

import org.springframework.core.io.ClassPathResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
}