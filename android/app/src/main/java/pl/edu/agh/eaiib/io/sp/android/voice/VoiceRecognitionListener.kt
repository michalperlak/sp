package pl.edu.agh.eaiib.io.sp.android.voice

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

object VoiceRecognitionListener : RecognitionListenerAdapter() {
    private var voiceControl: VoiceControl? = null

    fun initialize(voiceControl: VoiceControl) {
        this.voiceControl = voiceControl
    }

    override fun onResults(results: Bundle?) {
        if (voiceControl == null) {
            return
        }

        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
        voiceControl?.processCommands(matches.toTypedArray())
    }

    override fun onError(error: Int) {
        if (voiceControl != null) {
            voiceControl?.restartListeningService()
        }
    }
}

open class RecognitionListenerAdapter : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) = Unit
    override fun onRmsChanged(rmsdB: Float) = Unit
    override fun onBufferReceived(buffer: ByteArray?) = Unit
    override fun onPartialResults(partialResults: Bundle?) = Unit
    override fun onEvent(eventType: Int, params: Bundle?) = Unit
    override fun onBeginningOfSpeech() = Unit
    override fun onEndOfSpeech() = Unit
    override fun onError(error: Int) = Unit
    override fun onResults(results: Bundle?) = Unit
}