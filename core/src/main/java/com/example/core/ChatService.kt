package com.example.core

/**
 * Contract between the Active Chat feature and whatever actually talks to the model.
 * Milestone 2: fake, in-memory implementation. Milestone 3: real Ktor/Gemini client.
 *
 * [contents] mirrors Gemini's request shape: the ordered conversation turns, with the latest
 * user prompt as the last entry. Build it with [buildPayload].
 */
interface ChatService {
    /** Returns the mentor's reply. Implementations may throw to signal network/API errors. */
    suspend fun send(contents: List<ChatMessage>): ChatMessage
}
