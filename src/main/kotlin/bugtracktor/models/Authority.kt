package bugtracktor.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority

@Document
data class Authority @PersistenceConstructor constructor(
        @Id val name: String
) : GrantedAuthority {
    constructor(role: SystemRole) : this(role.toString())

    override fun getAuthority() = name

    companion object {
        fun from(role: SystemRole) = Authority(role.toString())
    }
}