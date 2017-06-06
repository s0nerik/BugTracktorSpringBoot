package bugtracktor.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
data class User @PersistenceConstructor constructor(
        @Indexed(unique = true)
        val email: String,
        private val password: String,
        val nickname: String,
        val realName: String,
        val avatarUrl: String,

        @DBRef
        val authorities: List<Authority> = listOf(Authority("ROLE_USER")),

        @Id val id: ObjectId? = null
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getUsername() = email
    override fun getPassword() = password

    @JsonIgnore override fun isEnabled() = true
    @JsonIgnore override fun isCredentialsNonExpired() = true
    @JsonIgnore override fun isAccountNonExpired() = true
    @JsonIgnore override fun isAccountNonLocked() = true
}
