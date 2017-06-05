package bugtracktor.security.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails

data class TokenBasedAuthentication(
        val token: String,
        val principle: UserDetails
) : AbstractAuthenticationToken(principle.authorities) {
    override fun getCredentials() = token
    override fun getPrincipal() = principle
    override fun isAuthenticated() = true
}