package bugtracktor.models

import com.fasterxml.jackson.annotation.JsonView
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Issue @PersistenceConstructor constructor(
        @JsonView(Views.Summary::class)
        val index: Int,
        @JsonView(Views.Summary::class)
        val isOpened: Boolean = true,
        @JsonView(Views.Summary::class)
        val isActive: Boolean = true,
        @JsonView(Views.Summary::class)
        @DBRef
        val author: User,

        @JsonView(Views.Summary::class)
        @DBRef
        val type: IssueType,
        @JsonView(Views.Summary::class)
        val shortDescription: String,
        @JsonView(Views.Summary::class)
        val fullDescription: String? = null,
        @JsonView(Views.Summary::class)
        val creationDate: Date = Date(),
        @JsonView(Views.Summary::class)
        @DBRef
        val assignees: List<User> = emptyList(),

        @DBRef
        val attachments: List<IssueAttachment> = emptyList(),
        @DBRef
        val history: List<IssueChange> = emptyList(),

        @JsonView(Views.Summary::class)
        @Id val id: ObjectId? = null
)

data class IssueType @PersistenceConstructor constructor(
        @Indexed(unique = true)
        @JsonView(Views.Summary::class)
        val name: String,
        val description: String = "",

        @JsonView(Views.Summary::class)
        @Id val id: ObjectId? = null
)

data class IssueAttachment @PersistenceConstructor constructor(
        val url: String
)

data class IssueChange @PersistenceConstructor constructor(
        val change: String,
        val author: User,
        val date: Date = Date()
)
