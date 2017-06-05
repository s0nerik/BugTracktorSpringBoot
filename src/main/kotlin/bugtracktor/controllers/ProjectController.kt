package bugtracktor.controllers

import bugtracktor.models.CreateProjectData
import bugtracktor.models.Project
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/projects")
@RestController()
class ProjectController(val repository: ProjectRepository, val userRepository: UserRepository) {
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    fun findAll() = repository.findAll()

    @PreAuthorize("hasRole('ROLE_PROJECT_CREATOR')")
    @PostMapping("")
    fun create(@RequestBody projectData: CreateProjectData) {
        with(projectData) {
            repository.save(Project(name, shortDescription, userRepository.findAll()[0]))
        }
    }
}