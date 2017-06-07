package bugtracktor.security.auth

import bugtracktor.models.User
import bugtracktor.models.UserTokenState
import bugtracktor.security.TokenHelper
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Cookie

@Component
class AuthenticationSuccessHandler(
        val tokenHelper: TokenHelper,
        val objectMapper: ObjectMapper
) : SimpleUrlAuthenticationSuccessHandler() {
    @Value("\${jwt.expires_in}")
    private val EXPIRES_IN: Int = TimeUnit.DAYS.toSeconds(365).toInt()

    @Value("\${jwt.cookie}")
    private lateinit var TOKEN_COOKIE: String

    @Value("\${app.user_cookie}")
    private lateinit var USER_COOKIE: String

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        clearAuthenticationAttributes(request)
        val user = authentication.principal as User

        val jws = tokenHelper.generateToken(user.username)

// Don't use cookies for now.

//        // Create token auth Cookie
//        val authCookie = Cookie(TOKEN_COOKIE, jws)
//        authCookie.path = "/"
//        authCookie.isHttpOnly = true
//        authCookie.maxAge = EXPIRES_IN
//        // Create flag Cookie
//        val userCookie = Cookie(USER_COOKIE, user.email)
//        userCookie.path = "/"
//        userCookie.maxAge = EXPIRES_IN
//        // Add cookie to response
//        response.addCookie(authCookie)
//        response.addCookie(userCookie)

        // JWT is also in the response
        val userTokenState = UserTokenState(jws, EXPIRES_IN)
        val jwtResponse = objectMapper.writeValueAsString(userTokenState)
        response.contentType = "application/json"
        response.writer.write(jwtResponse)
    }
}