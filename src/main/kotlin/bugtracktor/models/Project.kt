package bugtracktor.models

import com.fasterxml.jackson.annotation.JsonView
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Project @PersistenceConstructor constructor(
        @JsonView(Views.Summary::class)
        val name: String,
        @JsonView(Views.Summary::class)
        val shortDescription: String,
        @JsonView(Views.Summary::class)
        @DBRef
        val creator: User,
        @DBRef
        val members: MutableList<ProjectMember> = mutableListOf(),
        @DBRef
        val issues: MutableList<Issue> = mutableListOf(),
        @DBRef
        val issueTypes: MutableList<IssueType> = mutableListOf(),
        @DBRef
        val roles: MutableList<Role> = mutableListOf(),

        val fullDescription: String? = null,

        @JsonView(Views.Summary::class)
        @Id val id: ObjectId? = null
)

data class ProjectMember @PersistenceConstructor constructor(
        val user: User,
        val project: Project,
        val joinDate: Date = Date(),
        val roles: List<Role> = emptyList(),
        val exitDate: Date? = null
)

data class Role @PersistenceConstructor constructor(
        val name: String,
        val description: String,
        val permissions: List<Permission> = emptyList()
)

data class Permission @PersistenceConstructor constructor(
        val name: String,
        val description: String
)