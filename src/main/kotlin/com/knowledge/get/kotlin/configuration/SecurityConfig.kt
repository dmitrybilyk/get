//package com.knowledge.get.kotlin.configuration
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.ReactiveAuthenticationManager
//import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.server.SecurityWebFilterChain
//import reactor.core.publisher.Mono
//
//@Configuration
//@EnableWebFluxSecurity
//class SecurityConfig {
//
//    @Bean
//    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
//        return http
//            .csrf { it.disable() }
//            .authorizeExchange {
////                it.pathMatchers("/items/learn-group**").permitAll() // Allow anonymous access
////                it.pathMatchers("/items").authenticated() // Require authentication for /items?page=2&size=10
////                it.pathMatchers("/items/**").hasRole("USER") // Require USER role for /items/{id}
////                it.anyExchange().authenticated() // Default: require authentication
//                it.anyExchange().permitAll() // Default: require authentication
//            }
//            .httpBasic(Customizer.withDefaults())
//            .build()
//    }
//
////    @Bean
////    fun userDetailsService(): MapReactiveUserDetailsService {
////        val user = User
////            .withUsername("user")
////            .password("{noop}password") // No encoding
////            .roles("USER")
////            .build()
////
////        return MapReactiveUserDetailsService(user)
////    }
//
//    @Bean
//    fun authenticationManager(userDetailsService: ReactiveUserDetailsService): ReactiveAuthenticationManager {
//        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
//        authenticationManager.setPasswordEncoder(passwordEncoder())
//        return authenticationManager
//    }
//
//    @Bean
//    fun userDetailsService(): ReactiveUserDetailsService {
//        // Map-based user storage
//        val users = mapOf(
//            "user" to User.withUsername("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build(),
//            "user2" to User.withUsername("user2")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER2")
//                .build()
//        )
//
//        return object : ReactiveUserDetailsService {
//            override fun findByUsername(username: String): Mono<UserDetails> {
//                return users[username]?.let { Mono.just(it) } ?: Mono.empty()
//            }
//        }
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//}
