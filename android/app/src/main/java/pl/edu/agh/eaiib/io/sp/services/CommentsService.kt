package pl.edu.agh.eaiib.io.sp.services

import android.content.Context
import android.location.LocationManager
import pl.edu.agh.eaiib.io.sp.android.AndroidNetworkHelper
import pl.edu.agh.eaiib.io.sp.comments.CommentsCollector
import pl.edu.agh.eaiib.io.sp.comments.CommentsPublisher
import pl.edu.agh.eaiib.io.sp.config.Configuration

class CommentsService(context: Context,
                      locationManager: LocationManager,
                      config: Configuration) {
    private val commentsCollector: CommentsCollector
    private val commentsPublisher: CommentsPublisher = CommentsPublisher(config)

    init {
        commentsCollector = CommentsCollector(context, locationManager, commentsPublisher)
        val networkHelper = ServicesUtil.getService(AndroidNetworkHelper::class.java)
        networkHelper.addNetworkStateListener(commentsPublisher)
    }

    fun startListening() = commentsCollector.startListening()
    fun stopListening() = commentsCollector.stopListening()
    fun startPublishing() = commentsPublisher.startPublishingData()
    fun stopPublishing() = commentsPublisher.stopPublishingData()
}
