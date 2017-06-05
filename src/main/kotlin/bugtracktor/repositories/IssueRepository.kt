package bugtracktor.repositories

import bugtracktor.models.Issue
import org.springframework.data.mongodb.repository.MongoRepository

interface IssueRepository : MongoRepository<Issue, Long>