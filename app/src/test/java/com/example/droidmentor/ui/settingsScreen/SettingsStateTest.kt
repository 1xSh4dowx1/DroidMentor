package com.example.droidmentor.ui.settingsScreen

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsStateTest {

    @Test
    fun `cannot save an empty field`() {
        assertFalse(SettingsState().canSave)
    }

    @Test
    fun `cannot save a blank field`() {
        assertFalse(SettingsState(apiKey = "   ").canSave)
    }

    @Test
    fun `can save a new key`() {
        assertTrue(SettingsState(apiKey = "AIzaSyExample123").canSave)
    }

    @Test
    fun `cannot save the key that is already saved`() {
        assertFalse(SettingsState(apiKey = "AIzaSyExample123", savedApiKey = "AIzaSyExample123").canSave)
    }

    @Test
    fun `cannot save the saved key again just because of extra whitespace`() {
        assertFalse(SettingsState(apiKey = " AIzaSyExample123\n", savedApiKey = "AIzaSyExample123").canSave)
    }

    @Test
    fun `can save once the draft differs from the saved key`() {
        assertTrue(SettingsState(apiKey = "another-key", savedApiKey = "AIzaSyExample123").canSave)
    }

    @Test
    fun `hasSavedKey reflects the repository`() {
        assertFalse(SettingsState().hasSavedKey)
        assertTrue(SettingsState(savedApiKey = "k").hasSavedKey)
    }
}
