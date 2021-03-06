package bugtracktor.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority

@Document
data class Authority @PersistenceConstructor constructor(
        @Id val name: String
) : GrantedAuthority {
    override fun getAuthority() = name
    override fun toString() = name
}