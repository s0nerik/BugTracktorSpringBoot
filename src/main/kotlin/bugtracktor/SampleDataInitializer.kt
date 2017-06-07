package bugtracktor

import bugtracktor.models.Authority
import bugtracktor.models.SystemRole
import bugtracktor.models.User
import bugtracktor.repositories.AuthorityRepository
import bugtracktor.repositories.IssueRepository
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SampleDataInitializer(
        val userRepository: UserRepository,
        val projectRepository: ProjectRepository,
        val issueRepository: IssueRepository,
        val authorityRepository: AuthorityRepository
) : ApplicationListener<ApplicationReadyEvent> {
    private fun init() {
        userRepository.deleteAll()
        projectRepository.deleteAll()
        issueRepository.deleteAll()
        authorityRepository.deleteAll()

        authorityRepository.save(listOf(Authority(SystemRole.USER), Authority(SystemRole.PROJECT_CREATOR), Authority(SystemRole.ADMIN)))

        userRepository.save(User("project_creator@test.com", "0", "sick", "Project creator", "", authorityRepository.findAll()))
        userRepository.save(User("user@test.com", "0", "sick", "User", "", listOf(Authority(SystemRole.USER))))
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) = init()
}