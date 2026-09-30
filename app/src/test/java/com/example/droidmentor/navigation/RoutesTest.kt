package com.example.droidmentor.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class RoutesTest {

    @Test
    fun activeChat_withoutId_usesNewChatSentinel() {
        assertEquals("active_chat?chatId=-1", Routes.activeChat())
    }

    @Test
    fun activeChat_withId_embedsTheId() {
        assertEquals("active_chat?chatId=42", Routes.activeChat(42L))
    }

    @Test
    fun activeChatPattern_declaresTheOptionalArgument() {
        assertEquals("active_chat?chatId={chatId}", Routes.ACTIVE_CHAT_PATTERN)
    }
}
