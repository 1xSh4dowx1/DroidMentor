package com.example.droidmentor

import android.app.Application
import com.example.droidmentor.container.AppContainer
import com.example.droidmentor.container.DefaultAppContainer

/**
 * Application class used as the service locator (Hilt/Dagger are not allowed in this project).
 * Access the container from anywhere with `context.appContainer`.
 */
class DroidMentorApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}
