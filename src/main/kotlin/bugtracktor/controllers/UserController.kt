package bugtracktor.controllers

import bugtracktor.ext.currentUser
import bugtracktor.repositories.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        val userRepository: UserRepository
) {
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    fun getUserInfo() = currentUser
}