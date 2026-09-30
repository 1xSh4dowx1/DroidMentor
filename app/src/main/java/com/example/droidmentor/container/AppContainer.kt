package com.example.droidmentor.container

import android.content.Context
import com.example.droidmentor.DroidMentorApp

/**
 * Service locator for the app-wide dependencies.
 *
 * Each feature owner adds its own dependency here (one property per feature):
 *  - A: ChatService (Ktor client)
 *  - B: ConversationRepository (Room)
 *  - C: SettingsRepository (DataStore)
 *
 * Until the real implementations exist, expose fake/in-memory ones behind the same interface.
 */
interface AppContainer

class DefaultAppContainer(
    @Suppress("unused") private val appContext: Context,
) : AppContainer

/** Shortcut to reach the container from any Context, e.g. inside a ViewModel factory. */
val Context.appContainer: AppContainer
    get() = (applicationContext as DroidMentorApp).container
