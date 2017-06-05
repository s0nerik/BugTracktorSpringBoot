package bugtracktor.security.auth

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler()