package com.example.droidmentor.ui.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.SettingsRepository
import com.example.core.normalizeApiKey
import kotlinx.coroutines.launch

/**
 * Holds the Settings screen state across rotation. The key lives only here and in the
 * repository: it is never put in a saved-state Bundle, a log or a navigation argument.
 *
 * TODO(C, milestone 3): the DataStore implementation can throw IOException, so [save] and
 *  [clear] will need to surface a failure state.
 */
class SettingsViewModel(
    private val repository: SettingsRepository,
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    init {
        viewModelScope.launch {
            val saved = repository.getApiKey()
            state = state.copy(
                // Do not overwrite something the user already started typing.
                apiKey = state.apiKey.ifEmpty { saved.orEmpty() },
                savedApiKey = saved,
            )
        }
    }

    fun onApiKeyChange(text: String) {
        state = state.copy(apiKey = text, justSaved = false)
    }

    fun save() {
        val key = normalizeApiKey(state.apiKey) ?: return
        viewModelScope.launch {
            repository.saveApiKey(key)
            state = state.copy(apiKey = key, savedApiKey = key, justSaved = true)
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.clearApiKey()
            state = SettingsState()
        }
    }

    class Factory(private val repository: SettingsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SettingsViewModel(repository) as T
    }
}
