package pl.edu.agh.eaiib.io.sp.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.eaiib.io.sp.common.model.Comment
import pl.edu.agh.eaiib.io.sp.common.model.ProcessedData
import pl.edu.agh.eaiib.io.sp.common.model.Reading

@Repository
interface ReadingsRepository : MongoRepository<Reading, String>

@Repository
interface CommentsRepository : MongoRepository<Comment, String>

@Repository
interface ProcessedDataRepository : MongoRepository<ProcessedData, String>