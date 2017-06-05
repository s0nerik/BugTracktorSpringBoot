package bugtracktor.security.auth

import bugtracktor.security.TokenHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder

class TokenAuthenticationFilter : OncePerRequestFilter() {
    @Value("\${jwt.header}")
    private lateinit var AUTH_HEADER: String

    @Value("\${jwt.cookie}")
    private lateinit var AUTH_COOKIE: String

    @Autowired
    lateinit var tokenHelper: TokenHelper

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    private fun getToken(request: HttpServletRequest): String? {
        /**
         * Getting the token from Cookie store
         */
        val authCookie = getCookieValueByName(request, AUTH_COOKIE)
        if (authCookie != null) return authCookie.value

        /**
         * Getting the token from Authentication header
         * e.g Bearer some_token
         */
        val authHeader = request.getHeader(AUTH_HEADER)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        }

        return null
    }

    private fun getCookieValueByName(request: HttpServletRequest, name: String): Cookie? {
        if (request.cookies == null) return null

        return (0..request.cookies.size - 1)
                .firstOrNull { request.cookies[it].name == name }
                ?.let { request.cookies[it] }
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authToken = getToken(request)
        if (authToken != null) {
            // get username from token
            val username = tokenHelper.getUsernameFromToken(authToken)
            if (username != null) {
                // get user
                val userDetails = userDetailsService.loadUserByUsername(username)
                // create authentication
                val authentication = TokenBasedAuthentication(authToken, userDetails)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } else {
            SecurityContextHolder.getContext().authentication = AnonAuthentication()
        }

        filterChain.doFilter(request, response)
    }
}