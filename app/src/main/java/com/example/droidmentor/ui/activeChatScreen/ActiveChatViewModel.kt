package com.example.droidmentor.ui.activeChatScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.ChatMessage
import com.example.core.ChatService
import com.example.core.buildPayload
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActiveChatViewModel(
    private val service: ChatService,
) : ViewModel() {

    var state by mutableStateOf<ActiveChatState>(ActiveChatState.Idle(emptyList()))
        private set

    private var inFlight: Job? = null

    fun send(prompt: String) {
        val trimmed = prompt.trim()
        if (trimmed.isEmpty()) return
        if (state is ActiveChatState.Sending) return

        val previousMessages = state.messages
        val userMessage = ChatMessage(trimmed, isUser = true)
        val messagesWithUser = previousMessages + userMessage
        state = ActiveChatState.Sending(messagesWithUser)

        inFlight = viewModelScope.launch {
            try {
                val payload = buildPayload(previousMessages, trimmed)
                val reply = service.send(payload)
                state = ActiveChatState.Idle(messagesWithUser + reply)
            } catch (t: Throwable) {
                state = ActiveChatState.Error(
                    messages = messagesWithUser,
                    reason = t.message ?: "Unknown error",
                )
            }
        }
    }

    fun dismissError() {
        val current = state
        if (current is ActiveChatState.Error) {
            state = ActiveChatState.Idle(current.messages)
        }
    }

    class Factory(private val service: ChatService) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ActiveChatViewModel(service) as T
    }
}
