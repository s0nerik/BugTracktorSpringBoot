package bugtracktor.repositories

import bugtracktor.models.Project
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectRepository : MongoRepository<Project, ObjectId>