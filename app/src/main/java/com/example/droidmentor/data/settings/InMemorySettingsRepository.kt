package com.example.droidmentor.data.settings

import com.example.core.SettingsRepository
import com.example.core.normalizeApiKey

/**
 * In-memory [SettingsRepository] used for Milestone 2: the key survives rotation and navigation
 * (the container lives as long as the process) but not a process restart. Replaced by the
 * DataStore-backed implementation in Milestone 3.
 */
class InMemorySettingsRepository(
    initialApiKey: String? = null,
) : SettingsRepository {

    @Volatile
    private var apiKey: String? = initialApiKey?.let(::normalizeApiKey)

    override suspend fun getApiKey(): String? = apiKey

    override suspend fun saveApiKey(apiKey: String) {
        this.apiKey = requireNotNull(normalizeApiKey(apiKey)) { "API key must not be blank" }
    }

    override suspend fun clearApiKey() {
        apiKey = null
    }
}
