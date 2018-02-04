package pl.edu.agh.eaiib.io.sp.android.voice


interface VoiceControl {
    fun processCommands(commands: Array<String>)
    fun restartListeningService()
}