package com.example.droidmentor.ui.settingsScreen

import com.example.core.normalizeApiKey

/**
 * UI state for the Settings screen. [apiKey] is the text currently in the field (the draft) and
 * [savedApiKey] is what the repository holds.
 */
data class SettingsState(
    val apiKey: String = "",
    val savedApiKey: String? = null,
    val justSaved: Boolean = false,
) {
    private val normalizedDraft: String? get() = normalizeApiKey(apiKey)

    /** Saving makes sense only for a usable key that differs from the one already saved. */
    val canSave: Boolean get() = normalizedDraft.let { it != null && it != savedApiKey }

    val hasSavedKey: Boolean get() = savedApiKey != null
}
