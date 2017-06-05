package bugtracktor.repositories

import bugtracktor.models.Project
import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectRepository : MongoRepository<Project, Long>