package bugtracktor

import bugtracktor.models.Project
import bugtracktor.models.User
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Before
    fun before() {
        userRepository.deleteAll()
        projectRepository.deleteAll()
    }

    @Test
    fun `Changing creator should affect the project creator too`() {
        userRepository.save(User("test@test.com", "0", "test", "", ""))
        projectRepository.save(Project("Test", "Test", userRepository.findAll()[0]))
        val changedUser = userRepository.findAll()[0].copy(email = "")
        userRepository.save(changedUser)
        Assert.assertEquals("", projectRepository.findAll()[0].creator.email)
    }

}