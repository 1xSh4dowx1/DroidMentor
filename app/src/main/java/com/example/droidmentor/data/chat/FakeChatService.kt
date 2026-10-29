package com.example.droidmentor.data.chat

import com.example.core.ChatMessage
import com.example.core.ChatService
import kotlinx.coroutines.delay

/**
 * In-memory [ChatService] used for Milestone 2. Returns a canned reply after a small delay so the
 * "sending" state is visible in the UI. Replaced by the real Ktor/Gemini client in Milestone 3.
 *
 * ponytail: canned echo + fixed delay, swap for the real client in M3.
 */
class FakeChatService(
    private val replyDelayMs: Long = 400L,
) : ChatService {

    override suspend fun send(contents: List<ChatMessage>): ChatMessage {
        delay(replyDelayMs)
        val lastUserPrompt = contents.lastOrNull { it.isUser }?.text ?: "(no prompt)"
        return ChatMessage(
            text = "(fake mentor) I would answer: \"$lastUserPrompt\". Payload size: ${contents.size}.",
            isUser = false,
        )
    }
}
