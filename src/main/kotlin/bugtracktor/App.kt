package bugtracktor

import bugtracktor.models.Authority
import bugtracktor.models.User
import bugtracktor.repositories.AuthorityRepository
import bugtracktor.repositories.IssueRepository
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@SpringBootApplication
open class App {
    @Bean
    open fun init(
            userRepository: UserRepository,
            projectRepository: ProjectRepository,
            issueRepository: IssueRepository,
            authorityRepository: AuthorityRepository
    ) = CommandLineRunner {
        userRepository.deleteAll()
        projectRepository.deleteAll()
        issueRepository.deleteAll()
        authorityRepository.deleteAll()

        authorityRepository.save(listOf(Authority("ROLE_USER"), Authority("ROLE_PROJECT_CREATOR")))

        userRepository.save(User("test@test.com", "0", "sick", "Vasya Pupkin", "", authorityRepository.findAll()))
    }

    @Bean
    fun jacksonBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .serializerByType(ObjectId::class.java, ToStringSerializer())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}