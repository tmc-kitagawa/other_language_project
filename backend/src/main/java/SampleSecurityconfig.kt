//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
//import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.crypto.factory.PasswordEncoderFactories
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//
//@Configuration
//@EnableWebSecurity
//class SampleSecurityconfig {
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.csrf().disable()
//        http.authorizeHttpRequests(Customizer { authorize: AuthorizationManagerRequestMatcherRegistry ->
//            authorize
//                .requestMatchers("/").permitAll()
//                .requestMatchers("/js/**").permitAll()
//                .requestMatchers("/css/**").permitAll()
//                .requestMatchers("/img/**").permitAll()
//                .anyRequest().authenticated()
//        })
//        http.formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
//            form.defaultSuccessUrl("/secret")
//        }
//        return http.build()
//    }
//
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
//}
//
