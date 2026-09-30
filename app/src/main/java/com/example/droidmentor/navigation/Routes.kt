package com.example.droidmentor.navigation

/**
 * Navigation routes (see Figure 1 of the assignment).
 *
 * Active Chat receives an optional conversation id. [NEW_CHAT_ID] means "start a new conversation".
 */
object Routes {
    const val TITLE = "title"
    const val CHAT_HISTORY = "chat_history"
    const val ABOUT = "about"
    const val SETTINGS = "settings"

    const val ACTIVE_CHAT = "active_chat"
    const val ARG_CHAT_ID = "chatId"
    const val NEW_CHAT_ID = -1L

    /** Route pattern registered in the NavHost. */
    const val ACTIVE_CHAT_PATTERN = "$ACTIVE_CHAT?$ARG_CHAT_ID={$ARG_CHAT_ID}"

    /** Builds the concrete route to open a conversation (or a new one when no id is given). */
    fun activeChat(chatId: Long = NEW_CHAT_ID): String = "$ACTIVE_CHAT?$ARG_CHAT_ID=$chatId"
}
