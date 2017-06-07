package bugtracktor

import bugtracktor.models.Authority
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class App {
    @Bean
    fun jacksonConfig() = Jackson2ObjectMapperBuilderCustomizer {
        it.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .serializerByType(ObjectId::class.java, ToStringSerializer())
                .serializerByType(Authority::class.java, ToStringSerializer())
                .deserializerByType(Authority::class.java, object: JsonDeserializer<Authority>() {
                    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?) = Authority(p!!.valueAsString)
                })
                .serializationInclusion(JsonInclude.Include.NON_NULL)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}