package bugtracktor

import bugtracktor.models.Authority
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@SpringBootApplication
open class App {
    @Bean
    fun jacksonBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .serializerByType(ObjectId::class.java, ToStringSerializer())
                .serializerByType(Authority::class.java, ToStringSerializer())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}