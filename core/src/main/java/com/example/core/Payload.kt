package com.example.core

/** Default upper bound on how many prior messages travel with each request. */
const val DEFAULT_HISTORY_LIMIT = 20

/**
 * Builds the ordered list of messages that would be sent to the model: the most recent
 * [historyLimit] entries from [history] followed by the new user [prompt].
 *
 * Truncation keeps the tail of the conversation, which is the context the model needs most.
 * ponytail: naive sliding window; swap for token-aware compaction only if quality drops.
 */
fun buildPayload(
    history: List<ChatMessage>,
    prompt: String,
    historyLimit: Int = DEFAULT_HISTORY_LIMIT,
): List<ChatMessage> {
    require(historyLimit >= 0) { "historyLimit must be >= 0" }
    require(prompt.isNotBlank()) { "prompt must not be blank" }
    return truncateHistory(history, historyLimit) + ChatMessage(prompt, isUser = true)
}

/** Returns the last [limit] messages of [history], preserving order. */
fun truncateHistory(history: List<ChatMessage>, limit: Int): List<ChatMessage> {
    require(limit >= 0) { "limit must be >= 0" }
    return if (history.size <= limit) history else history.subList(history.size - limit, history.size)
}
