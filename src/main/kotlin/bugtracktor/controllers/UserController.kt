package bugtracktor.controllers

import bugtracktor.ext.currentUser
import bugtracktor.models.ErrorResponse
import bugtracktor.models.User
import bugtracktor.repositories.UserRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        val userRepository: UserRepository
) {
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    fun getUserInfo() = currentUser

    @PostMapping("/register")
    fun createNewUser(@RequestBody user: User): ResponseEntity<*> {
        try {
            val u = userRepository.save(user)
            return ResponseEntity.ok(u)
        } catch (e: DuplicateKeyException) {
            return ResponseEntity.badRequest().body(ErrorResponse("User with the same email is already registered!"))
        }
    }
}