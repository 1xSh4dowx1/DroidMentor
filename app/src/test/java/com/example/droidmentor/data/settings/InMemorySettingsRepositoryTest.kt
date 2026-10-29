package com.example.droidmentor.data.settings

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class InMemorySettingsRepositoryTest {

    @Test
    fun `starts without a key`() = runBlocking {
        assertNull(InMemorySettingsRepository().getApiKey())
    }

    @Test
    fun `returns the key that was saved`() = runBlocking {
        val repository = InMemorySettingsRepository()
        repository.saveApiKey("AIzaSyExample123")
        assertEquals("AIzaSyExample123", repository.getApiKey())
    }

    @Test
    fun `saving normalizes the key`() = runBlocking {
        val repository = InMemorySettingsRepository()
        repository.saveApiKey("  AIzaSyExample123\n")
        assertEquals("AIzaSyExample123", repository.getApiKey())
    }

    @Test
    fun `saving a new key replaces the old one`() = runBlocking {
        val repository = InMemorySettingsRepository()
        repository.saveApiKey("first-key")
        repository.saveApiKey("second-key")
        assertEquals("second-key", repository.getApiKey())
    }

    @Test
    fun `saving a blank key is rejected and keeps the previous key`() {
        val repository = InMemorySettingsRepository(initialApiKey = "keep-me")
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { repository.saveApiKey("   ") }
        }
        assertEquals("keep-me", runBlocking { repository.getApiKey() })
    }

    @Test
    fun `clear forgets the key`() = runBlocking {
        val repository = InMemorySettingsRepository(initialApiKey = "some-key")
        repository.clearApiKey()
        assertNull(repository.getApiKey())
    }
}
