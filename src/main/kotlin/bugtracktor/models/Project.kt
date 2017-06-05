package bugtracktor.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Project @PersistenceConstructor constructor(
        val name: String,
        val shortDescription: String,
        @DBRef
        val creator: User,
        @DBRef
        val members: List<ProjectMember> = emptyList(),
        @DBRef
        val issues: List<Issue> = emptyList(),
        @DBRef
        val issueTypes: List<IssueType> = emptyList(),
        @DBRef
        val roles: List<Role> = emptyList(),

        val fullDescription: String? = null,

        @Id val id: ObjectId? = null
)

data class ProjectMember(
        val user: User,
        val project: Project,
        val joinDate: Date = Date(),
        val roles: List<Role> = emptyList(),
        val exitDate: Date? = null
)

data class Role(val name: String, val description: String, val permissions: List<Permission> = emptyList())

data class Permission(val name: String, val description: String)