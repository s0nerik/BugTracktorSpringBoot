package bugtracktor

import bugtracktor.models.*
import bugtracktor.repositories.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SampleDataInitializer(
        val userRepository: UserRepository,
        val projectRepository: ProjectRepository,
        val issueRepository: IssueRepository,
        val authorityRepository: AuthorityRepository,
        val issueTypeRepository: IssueTypeRepository
) : ApplicationListener<ApplicationReadyEvent> {

    @Value("\${POPULATE_DB}")
    val shouldPopulateDb: String? = null

    private fun removeAllData() {
        userRepository.deleteAll()
        projectRepository.deleteAll()
        issueRepository.deleteAll()
        authorityRepository.deleteAll()
        issueTypeRepository.deleteAll()
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
                creator = userRepository.findByEmail("admin@test.com")!!,
                fullDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        )

        projectRepository.save(projects)
    }

    private fun initIssueTypes() {
        issueTypeRepository.save(listOf(IssueType("Bug"), IssueType("Feature request")))
    }

    private fun initIssues() {
        val issues = listOf(
                Issue(
                        index = 0,
                        author = userRepository.findAll()[0],
                        shortDescription = "Test issue 1",
                        fullDescription = "Test issue 1 description",
                        type = issueTypeRepository.findAll()[0]
                ),
                Issue(
                        index = 1,
                        author = userRepository.findAll()[1],
                        shortDescription = "Test issue 2",
                        fullDescription = "Test issue 2 description",
                        type = issueTypeRepository.findAll()[1]
                )
        )
        issueRepository.save(issues)
    }

    fun init(shouldPopulate: Boolean) {
        if (shouldPopulate) {
            removeAllData()
            initAuthorities()
            initUsers()
            initProjects()
            initIssueTypes()
            initIssues()
        }
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        init(!shouldPopulateDb.isNullOrEmpty() && (userRepository.count() <= 0 || projectRepository.count() <= 0 || issueRepository.count() <= 0))
    }
}