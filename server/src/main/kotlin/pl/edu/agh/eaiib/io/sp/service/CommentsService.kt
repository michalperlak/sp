package pl.edu.agh.eaiib.io.sp.service

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.sp.common.model.Comment
import pl.edu.agh.eaiib.io.sp.repositories.CommentsRepository

@Service
class CommentsService(private val commentsRepository: CommentsRepository) {
    fun add(comment: Comment): Comment {
        return commentsRepository.save(comment)
    }

    fun getAll(): List<Comment> {
        return commentsRepository.findAll()
    }
}