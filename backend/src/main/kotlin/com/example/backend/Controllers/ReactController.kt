package com.example.backend.Controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ReactController {

    @GetMapping("/allusers")
    fun forward(): String {
        return "forward:/"
    }

    @GetMapping("/search")
    fun forwardSearch(): String {
        return "redirect:/"
    }
}