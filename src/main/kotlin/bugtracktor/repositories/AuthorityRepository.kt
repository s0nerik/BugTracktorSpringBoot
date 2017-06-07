package bugtracktor.repositories

import bugtracktor.models.Authority
import org.springframework.data.mongodb.repository.MongoRepository

interface AuthorityRepository : MongoRepository<Authority, String>