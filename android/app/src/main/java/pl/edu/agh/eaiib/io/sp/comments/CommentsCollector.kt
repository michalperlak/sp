package pl.edu.agh.eaiib.io.sp.comments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import pl.edu.agh.eaiib.io.sp.android.voice.VoiceControl
import pl.edu.agh.eaiib.io.sp.android.voice.VoiceRecognitionListener
import pl.edu.agh.eaiib.io.sp.common.model.Comment
import pl.edu.agh.eaiib.io.sp.common.model.Location


class CommentsCollector(private val context: Context,
                        private val locationManager: LocationManager,
                        private val commentsPublisher: CommentsPublisher) : VoiceControl {

    private var speechRecognizer: SpeechRecognizer? = null
    private var collectingEnabled = true

    init {
        VoiceRecognitionListener.initialize(this)
    }

    fun startListening() {
        collectingEnabled = true
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer?.setRecognitionListener(VoiceRecognitionListener)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        if (!intent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE)) {
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "pl.edu.agh")
        }

        speechRecognizer?.startListening(intent)
    }

    fun stopListening() {
        collectingEnabled = false

        speechRecognizer?.stopListening()
        speechRecognizer?.cancel()
        speechRecognizer?.destroy()
        speechRecognizer = null
    }

    @SuppressLint("MissingPermission")
    override fun processCommands(commands: Array<String>) {
        if (collectingEnabled) {
            if (commands.isEmpty()) {
                return
            }

            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val longitude = location?.longitude ?: 0.0
            val latitude = location?.latitude ?: 0.0
            commentsPublisher.addToQueue(Comment(Location(longitude, latitude), commands.first()))
        }
    }

    override fun restartListeningService() {
        stopListening()
        startListening()
    }
}