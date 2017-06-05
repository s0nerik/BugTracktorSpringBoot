package bugtracktor.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class User @PersistenceConstructor constructor(
        val email: String,
        val password: String,
        val nickname: String,
        val realName: String,
        val avatarUrl: String,

        @Id val id: ObjectId? = null
)

@Document
data class Issue @PersistenceConstructor constructor(
        val issueIndex: Int,
        val isOpened: Boolean,
        val isActive: Boolean,
        @DBRef
        val author: User,
        @DBRef
        val type: IssueType,
        val shortDescription: String,
        val fullDescription: String? = null,
        val creationDate: Date = Date(),
        @DBRef
        val assignees: List<User> = emptyList(),
        @DBRef
        val attachments: List<IssueAttachment> = emptyList(),
        @DBRef
        val history: List<IssueChange> = emptyList(),

        @Id val id: ObjectId? = null
)

data class IssueType(val name: String, val description: String)
data class IssueAttachment(val url: String)
data class IssueChange(val change: String, val author: User, val date: Date = Date())

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

data class Permission(val name: String, val description: String)

data class Role(val name: String, val description: String, val permissions: List<Permission> = emptyList())

data class Token(val token: String)