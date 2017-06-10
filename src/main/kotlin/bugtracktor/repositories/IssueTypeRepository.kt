package bugtracktor.repositories

import bugtracktor.models.IssueType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface IssueTypeRepository : MongoRepository<IssueType, ObjectId>