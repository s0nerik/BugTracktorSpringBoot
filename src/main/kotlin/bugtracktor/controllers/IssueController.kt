package bugtracktor.controllers

import bugtracktor.ext.currentUser
import bugtracktor.models.*
import bugtracktor.repositories.IssueRepository
import bugtracktor.repositories.ProjectRepository
import com.fasterxml.jackson.annotation.JsonView
import org.bson.types.ObjectId
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

data class CreateIssueData(
        val type: IssueType,
        val shortDescription: String,
        val fullDescription: String? = null,
        val attachments: List<IssueAttachment> = emptyList(),
        val assignees: List<User> = emptyList()
)

// TODO: allow only project members
@RequestMapping("/projects/{projectId}/issues")
@PreAuthorize("hasRole('ROLE_USER')")
@RestController
class IssueController(
        val issueRepository: IssueRepository,
        val projectRepository: ProjectRepository
) {
    @JsonView(Views.Summary::class)
    @GetMapping("")
    fun findAll(@PathVariable projectId: ObjectId) = projectRepository.findOne(projectId).issues

    @JsonView(Views.Summary::class)
    @PostMapping("")
    fun createIssue(@PathVariable projectId: ObjectId, @RequestBody issueData: CreateIssueData): Issue {
        val project = projectRepository.findOne(projectId)

        val index = project.issues.size
        val issue = with(issueData) {
            Issue(index, true, true, currentUser, type, shortDescription, fullDescription,
                    assignees = assignees,
                    attachments = attachments
            )
        }

        val createdIssue = issueRepository.save(issue)
        project.issues += createdIssue

        projectRepository.save(project)

        return createdIssue
    }
}