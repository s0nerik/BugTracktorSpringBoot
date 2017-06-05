package bugtracktor.controllers

import bugtracktor.models.CreateProjectData
import bugtracktor.models.Project
import bugtracktor.models.User
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import org.springframework.web.bind.annotation.*

@RequestMapping("/projects")
@RestController()
class ProjectController(val repository: ProjectRepository, val userRepository: UserRepository) {
    @GetMapping("")
    fun findAll() = repository.findAll()

    @PostMapping("")
    fun create(@RequestBody projectData: CreateProjectData) {
        with(projectData) {
            repository.save(Project(name, shortDescription, userRepository.findAll()[0]))
        }
    }
}