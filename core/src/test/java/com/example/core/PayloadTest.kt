package com.example.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PayloadTest {

    @Test
    fun `truncate keeps the whole history when under the limit`() {
        val history = listOf(user("a"), model("b"), user("c"))
        assertEquals(history, truncateHistory(history, limit = 10))
    }

    @Test
    fun `truncate keeps the last N when over the limit`() {
        val history = listOf(user("a"), model("b"), user("c"), model("d"), user("e"))
        assertEquals(listOf(user("c"), model("d"), user("e")), truncateHistory(history, limit = 3))
    }

    @Test
    fun `truncate with limit zero returns empty`() {
        val history = listOf(user("a"), model("b"))
        assertEquals(emptyList<ChatMessage>(), truncateHistory(history, limit = 0))
    }

    @Test
    fun `truncate rejects negative limit`() {
        assertThrows(IllegalArgumentException::class.java) {
            truncateHistory(emptyList(), limit = -1)
        }
    }

    @Test
    fun `buildPayload appends the prompt as a user message`() {
        val history = listOf(user("hi"), model("hello"))
        val payload = buildPayload(history, prompt = "how do I use Compose?")
        assertEquals(history + user("how do I use Compose?"), payload)
    }

    @Test
    fun `buildPayload truncates history then appends prompt`() {
        val history = listOf(user("a"), model("b"), user("c"), model("d"))
        val payload = buildPayload(history, prompt = "next", historyLimit = 2)
        assertEquals(listOf(user("c"), model("d"), user("next")), payload)
    }

    @Test
    fun `buildPayload works with empty history`() {
        val payload = buildPayload(emptyList(), prompt = "first message")
        assertEquals(listOf(user("first message")), payload)
    }

    @Test
    fun `buildPayload rejects blank prompt`() {
        assertThrows(IllegalArgumentException::class.java) {
            buildPayload(emptyList(), prompt = "   ")
        }
    }

    @Test
    fun `buildPayload rejects negative historyLimit`() {
        assertThrows(IllegalArgumentException::class.java) {
            buildPayload(emptyList(), prompt = "ok", historyLimit = -1)
        }
    }

    private fun user(text: String) = ChatMessage(text, isUser = true)
    private fun model(text: String) = ChatMessage(text, isUser = false)
}
