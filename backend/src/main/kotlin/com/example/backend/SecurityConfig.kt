package com.example.backend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Autowired
    private val dataSource: DataSource? = null

    @Bean
    fun userDetailsManager(): UserDetailsManager {
        val users = JdbcUserDetailsManager(this.dataSource)
//        users.createUser(makeUser("user", "password", "USER"))
        return users
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

    @Bean
    fun configureHttpSecurity(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf { authorize ->
            authorize.ignoringRequestMatchers("/api/users")
        }
        httpSecurity
            .authorizeHttpRequests(Customizer {authorize ->
                authorize
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/signup").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/api/users").permitAll() //開発中は公開しておく
                    .requestMatchers(HttpMethod.POST,"/api/users").permitAll() //開発中は公開しておく
                    .anyRequest().authenticated()
            })
        httpSecurity.formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
            form
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .defaultSuccessUrl("/")
        }
        return httpSecurity.build()
    }

//    @Bean
//    fun userDetailsManager(): InMemoryUserDetailsManager {
//        val username = "user"
//        val password = "pass"
//        val user = User.withUsername(username)
//            .password(
//                PasswordEncoderFactories
//                    .createDelegatingPasswordEncoder()
//                    .encode(password)
//            )
//            .roles("USER")
//            .build()
//        return InMemoryUserDetailsManager(user)
//    }


}

