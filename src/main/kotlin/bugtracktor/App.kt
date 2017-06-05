package bugtracktor

import bugtracktor.models.User
import bugtracktor.repositories.IssueRepository
import bugtracktor.repositories.ProjectRepository
import bugtracktor.repositories.UserRepository
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

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
    fun parameterNamesModule(): Module {
        return SimpleModule("ObjectIdModule").addSerializer(ObjectId::class.java, ToStringSerializer())
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}