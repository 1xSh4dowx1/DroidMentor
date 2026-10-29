package com.example.core

private const val MASK_CHAR = '•'
private const val VISIBLE_SUFFIX = 4
private const val MIN_LENGTH_TO_SHOW_SUFFIX = 12

/**
 * Cleans what the user typed or pasted into the API key field. Gemini keys never contain
 * whitespace, but pasting often brings a trailing newline or a space, so every whitespace
 * character is dropped. Returns null when nothing usable is left.
 */
fun normalizeApiKey(raw: String): String? =
    raw.filterNot { it.isWhitespace() }.ifEmpty { null }

/**
 * Display-safe version of [apiKey]: only the last few characters are shown, and only when the
 * key is long enough for that to reveal very little. Never log or display the full key.
 */
fun maskApiKey(apiKey: String): String =
    if (apiKey.length < MIN_LENGTH_TO_SHOW_SUFFIX) {
        MASK_CHAR.toString().repeat(apiKey.length)
    } else {
        MASK_CHAR.toString().repeat(apiKey.length - VISIBLE_SUFFIX) + apiKey.takeLast(VISIBLE_SUFFIX)
    }
