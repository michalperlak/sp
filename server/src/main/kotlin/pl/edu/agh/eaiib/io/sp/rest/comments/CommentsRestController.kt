package pl.edu.agh.eaiib.io.sp.rest.comments

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.agh.eaiib.io.sp.common.model.Comment
import pl.edu.agh.eaiib.io.sp.service.CommentsService

@RestController
@RequestMapping("/api/comments")
class CommentsRestController(private val commentsService: CommentsService) {

    @PostMapping
    fun addComments(@RequestBody comments: List<Comment>): ResponseEntity<List<Comment>> {
        val added = comments.asSequence()
                .map { commentsService.add(it) }
                .toList()

        return ResponseEntity(added, HttpStatus.OK)
    }

    @GetMapping
    fun getAllReadings(): List<Comment> {
        return commentsService.getAll()
    }
}