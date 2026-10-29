package com.example.droidmentor.container

import android.content.Context
import com.example.core.ChatService
import com.example.core.SettingsRepository
import com.example.droidmentor.DroidMentorApp
import com.example.droidmentor.data.chat.FakeChatService
import com.example.droidmentor.data.settings.InMemorySettingsRepository

/**
 * Service locator for the app-wide dependencies.
 *
 * Each feature owner adds its own dependency here (one property per feature):
 *  - A: ChatService (Milestone 2: fake, Milestone 3: Ktor/Gemini client)
 *  - B: ConversationRepository (Room)
 *  - C: SettingsRepository (Milestone 2: in-memory, Milestone 3: DataStore)
 */
interface AppContainer {
    val chatService: ChatService
    val settingsRepository: SettingsRepository
}

class DefaultAppContainer(
    @Suppress("unused") private val appContext: Context,
) : AppContainer {
    override val chatService: ChatService = FakeChatService()
    override val settingsRepository: SettingsRepository = InMemorySettingsRepository()
}

/** Shortcut to reach the container from any Context, e.g. inside a ViewModel factory. */
val Context.appContainer: AppContainer
    get() = (applicationContext as DroidMentorApp).container
