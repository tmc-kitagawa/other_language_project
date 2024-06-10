package com.example.backend

import org.springframework.core.io.ClassPathResource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ReactController {

    @GetMapping("/allusers")
    fun forward(): String {
        return "forward:/"
    }
}