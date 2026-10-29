package com.example.core

/**
 * Contract between the Settings feature and wherever the BYOK Gemini API key lives.
 * Milestone 2: in-memory implementation. Milestone 3: DataStore (+ Keystore) implementation.
 *
 * The key is read on demand ([getApiKey]) so that the chat client always uses the latest value
 * at the moment it builds a request, without having to observe anything.
 */
interface SettingsRepository {
    /** The saved API key, or null when the user has not configured one yet. */
    suspend fun getApiKey(): String?

    /** Saves [apiKey] after [normalizeApiKey]; throws [IllegalArgumentException] if it is blank. */
    suspend fun saveApiKey(apiKey: String)

    /** Forgets the saved API key. */
    suspend fun clearApiKey()
}
