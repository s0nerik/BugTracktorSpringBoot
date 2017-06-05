package bugtracktor.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

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
