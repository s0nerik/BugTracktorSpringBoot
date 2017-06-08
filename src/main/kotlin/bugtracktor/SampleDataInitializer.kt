package bugtracktor

import bugtracktor.models.Authority
import bugtracktor.models.Project
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
    private fun removeAllData() {
        userRepository.deleteAll()
        projectRepository.deleteAll()
        issueRepository.deleteAll()
        authorityRepository.deleteAll()
    }

    private fun initAuthorities() {
        authorityRepository.save(listOf(Authority(SystemRole.USER), Authority(SystemRole.PROJECT_CREATOR), Authority(SystemRole.ADMIN)))
    }

    private fun initUsers() {
        val users = mutableListOf<User>()
        users += User("user1@test.com", "0", "projectCreator", "Project creator", "", listOf(Authority(SystemRole.USER), Authority(SystemRole.PROJECT_CREATOR)))
        users += User("user2@test.com", "0", "justUser", "User", "", listOf(Authority(SystemRole.USER)))
        users += User("admin@test.com", "0", "admin", "Admin", "", listOf(Authority(SystemRole.USER), Authority(SystemRole.PROJECT_CREATOR), Authority(SystemRole.ADMIN)))

        userRepository.save(users)
    }

    private fun initProjects() {
        val projects = mutableListOf<Project>()
        projects += Project(
                name = "Test project 1",
                shortDescription = "Test project 1 description",
                creator = userRepository.findByEmail("user1@test.com")!!,
                fullDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        )

        projects += Project(
                name = "Test project 2",
                shortDescription = "Test project 2 description",
                creator = userRepository.findByEmail("user2@test.com")!!,
                fullDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        )

        projectRepository.save(projects)
    }

    private fun init() {
        removeAllData()
        initAuthorities()
        initUsers()
        initProjects()
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) = init()
}