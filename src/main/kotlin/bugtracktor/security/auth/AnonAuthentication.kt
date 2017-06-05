package bugtracktor.security.auth

import org.springframework.security.authentication.AbstractAuthenticationToken

class AnonAuthentication : AbstractAuthenticationToken(null) {
    override fun getCredentials() = null
    override fun getPrincipal() = null
    override fun isAuthenticated() = true
}