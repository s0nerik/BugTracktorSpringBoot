package bugtracktor.repositories

import bugtracktor.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, Long>