package pl.edu.agh.eaiib.io.sp.comments

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.sp.common.model.Comment
import pl.edu.agh.eaiib.io.sp.config.Configuration
import pl.edu.agh.eaiib.io.sp.rest.AbstractPublisher
import pl.edu.agh.eaiib.io.sp.rest.Api

class CommentsPublisher(config: Configuration) : AbstractPublisher<Comment>(config) {
    override fun publish(api: Api, data: Comment): Flowable<Any> = api.addComment(data)
}