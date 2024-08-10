package com.tutorial.learnenglishnewera.audio

import android.media.MediaPlayer
import java.io.File

class AudioPlayer:Audio {

    private var player: MediaPlayer? = null

    fun createPlayer(file: File) {
        if (player == null){
            player = MediaPlayer()
        } else {
            player?.reset()
        }

        player?.setDataSource(file.absolutePath)
        player?.prepare()
    }

    override fun start() {
        player?.start()
    }

    override fun stop() {
        player?.stop()
        player?.reset()
        player = null
    }

    override fun resume() {
        player?.start()
    }

    override fun pause() {
        player?.pause()
    }

    override fun release() {
        player?.release()
    }

    override fun isActive(): Boolean {
        return player != null
    }

    fun completeFun(func:()->Unit){
        player?.setOnCompletionListener { func() }
    }
}