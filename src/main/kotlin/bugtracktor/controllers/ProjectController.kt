package bugtracktor.controllers

import bugtracktor.models.CreateProjectData
import bugtracktor.models.Project
import bugtracktor.models.Views
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import com.fasterxml.jackson.annotation.JsonView
import org.bson.types.ObjectId
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/projects")
@RestController
class ProjectController(
        val projectRepository: ProjectRepository,
        val userRepository: UserRepository
) {
    @JsonView(Views.Summary::class)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    fun findAll() = projectRepository.findAll()

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: ObjectId) = projectRepository.findOne(projectId)

    @PreAuthorize("hasRole('ROLE_PROJECT_CREATOR')")
    @PostMapping("")
    fun create(@RequestBody projectData: CreateProjectData) {
        with(projectData) {
            projectRepository.save(Project(name, shortDescription, userRepository.findAll()[0]))
        }
    }

    @PreAuthorize("hasPermission(#projectId, 'bugtracktor.models.Project', 'delete')")
    @DeleteMapping("/{projectId}")
    fun delete(@PathVariable projectId: ObjectId) {
        projectRepository.delete(projectId)
    }

}