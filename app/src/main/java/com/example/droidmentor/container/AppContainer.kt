package com.example.droidmentor.container

import android.content.Context
import com.example.core.ChatService
import com.example.droidmentor.DroidMentorApp
import com.example.droidmentor.data.chat.FakeChatService

/**
 * Service locator for the app-wide dependencies.
 *
 * Each feature owner adds its own dependency here (one property per feature):
 *  - A: ChatService (Milestone 2: fake, Milestone 3: Ktor/Gemini client)
 *  - B: ConversationRepository (Room)
 *  - C: SettingsRepository (DataStore)
 */
interface AppContainer {
    val chatService: ChatService
}

class DefaultAppContainer(
    @Suppress("unused") private val appContext: Context,
) : AppContainer {
    override val chatService: ChatService = FakeChatService()
}

/** Shortcut to reach the container from any Context, e.g. inside a ViewModel factory. */
val Context.appContainer: AppContainer
    get() = (applicationContext as DroidMentorApp).container
