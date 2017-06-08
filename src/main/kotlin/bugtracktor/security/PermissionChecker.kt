package bugtracktor.security

import bugtracktor.ext.currentUser
import bugtracktor.models.Project
import bugtracktor.models.SystemRole
import bugtracktor.repositories.ProjectRepository
import org.bson.types.ObjectId
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class PermissionChecker(
        val projectRepository: ProjectRepository
) : PermissionEvaluator {
    override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any) = false

    override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any): Boolean {
        return when (targetType) {
            Project::class.qualifiedName -> {
                projectRepository.findOne(targetId as ObjectId)?.creator == currentUser || currentUser.systemRoles.contains(SystemRole.ADMIN)
            }
            else -> false
        }
    }
}