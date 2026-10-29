package com.example.droidmentor.ui.activeChatScreen

import com.example.core.ChatMessage

/**
 * UI state for the Active Chat screen. Error messages are kept out of [messages] because
 * they must never travel back to the model in the next request (project ground rule).
 */
sealed class ActiveChatState {
    abstract val messages: List<ChatMessage>

    data class Idle(override val messages: List<ChatMessage>) : ActiveChatState()
    data class Sending(override val messages: List<ChatMessage>) : ActiveChatState()
    data class Error(override val messages: List<ChatMessage>, val reason: String) : ActiveChatState()
}
