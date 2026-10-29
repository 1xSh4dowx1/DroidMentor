package com.example.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ApiKeyTest {

    @Test
    fun `normalize keeps a clean key unchanged`() {
        assertEquals("AIzaSyExample123", normalizeApiKey("AIzaSyExample123"))
    }

    @Test
    fun `normalize trims spaces and newlines around a pasted key`() {
        assertEquals("AIzaSyExample123", normalizeApiKey("  AIzaSyExample123 \n"))
    }

    @Test
    fun `normalize drops whitespace inside a key that was wrapped when copied`() {
        assertEquals("AIzaSyExample123", normalizeApiKey("AIzaSyExa\n mple123"))
    }

    @Test
    fun `normalize returns null for empty or blank input`() {
        assertNull(normalizeApiKey(""))
        assertNull(normalizeApiKey("   \t\n"))
    }

    @Test
    fun `mask hides everything except the last four characters of a long key`() {
        val masked = maskApiKey("AIzaSyExample123456")
        assertEquals("•••••••••••••••3456", masked)
        assertEquals("AIzaSyExample123456".length, masked.length)
    }

    @Test
    fun `mask hides a short key completely`() {
        assertEquals("•••••", maskApiKey("abcde"))
    }

    @Test
    fun `mask never contains the beginning of the key`() {
        val key = "AIzaSyExample123456"
        assertTrue(!maskApiKey(key).contains(key.take(8)))
    }
}
