package bugtracktor

import bugtracktor.models.User
import bugtracktor.repositories.IssueRepository
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.module.SimpleModule
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
            issueRepository: IssueRepository) = CommandLineRunner {
        userRepository.deleteAll()
        userRepository.save(User("test@test.com", "0", "sick", "Vasya Pupkin", ""))

        projectRepository.deleteAll()
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