package com.tutorial.learnenglishnewera.audio

interface Audio {

    fun start()

    fun stop()

    fun pause()

    fun resume()

    fun release()

    fun isActive():Boolean
}