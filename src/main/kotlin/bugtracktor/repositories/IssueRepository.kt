package bugtracktor.repositories

import bugtracktor.models.Issue
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface IssueRepository : MongoRepository<Issue, ObjectId>