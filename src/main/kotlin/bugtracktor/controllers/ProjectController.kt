package bugtracktor.controllers

import bugtracktor.models.CreateProjectData
import bugtracktor.models.Project
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/projects")
@RestController
class ProjectController(
        val projectRepository: ProjectRepository,
        val userRepository: UserRepository
) {
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    fun findAll() = projectRepository.findAll()

    @PreAuthorize("hasRole('ROLE_PROJECT_CREATOR')")
    @PostMapping("")
    fun create(@RequestBody projectData: CreateProjectData) {
        with(projectData) {
            projectRepository.save(Project(name, shortDescription, userRepository.findAll()[0]))
        }
    }
}