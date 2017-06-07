package bugtracktor.ext

import bugtracktor.models.User
import org.springframework.security.core.context.SecurityContextHolder

val Any.currentUser: User
    get() = SecurityContextHolder.getContext().authentication.principal as User