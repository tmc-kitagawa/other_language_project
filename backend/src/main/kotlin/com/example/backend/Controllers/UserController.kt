package com.example.backend.Controllers

import com.example.backend.dataClass.CreateUserRequest
import net.sf.jsqlparser.Model
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner.Mode
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UserController(
    @Autowired val userDetailsManager: UserDetailsManager
) {
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/signup")
    fun getSignup(): String {
        return "signup"
    }

    @PostMapping("/signup")
    fun signup(
        @RequestParam("username") username: String,
        @RequestParam("password") password: String
    ): String {
        userDetailsManager.createUser(makeUser(username, password, "USER"))
        return "redirect: login"
    }

    private fun makeUser(user: String, pw: String, role: String): UserDetails {
        return User.withUsername(user)
            .password(
                PasswordEncoderFactories
                    .createDelegatingPasswordEncoder()
                    .encode(pw)
            )
            .roles(role)
            .build()
    }

}