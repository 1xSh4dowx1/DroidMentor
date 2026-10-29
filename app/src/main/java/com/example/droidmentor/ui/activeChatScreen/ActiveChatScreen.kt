package com.example.droidmentor.ui.activeChatScreen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.ChatMessage
import com.example.droidmentor.R
import com.example.droidmentor.ui.common.AppScaffold
import com.example.droidmentor.ui.theme.DroidMentorTheme

@Composable
fun ActiveChatScreen(
    viewModel: ActiveChatViewModel,
    onBack: () -> Unit,
) {
    ActiveChatScreenContent(
        state = viewModel.state,
        onSend = viewModel::send,
        onDismissError = viewModel::dismissError,
        onBack = onBack,
    )
}

@Composable
private fun ActiveChatScreenContent(
    state: ActiveChatState,
    onSend: (String) -> Unit,
    onDismissError: () -> Unit,
    onBack: () -> Unit,
) {
    AppScaffold(
        title = stringResource(R.string.active_chat_title),
        onBack = onBack,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            MessageList(
                messages = state.messages,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            )
            if (state is ActiveChatState.Error) {
                ErrorBanner(reason = state.reason, onDismiss = onDismissError)
            }
            InputBar(
                enabled = state !is ActiveChatState.Sending,
                sending = state is ActiveChatState.Sending,
                onSend = onSend,
            )
        }
    }
}

@Composable
private fun MessageList(
    messages: List<ChatMessage>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }
    if (messages.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.active_chat_empty),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        return
    }
    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(messages) { MessageBubble(it) }
    }
}

@Composable
private fun InputBar(
    enabled: Boolean,
    sending: Boolean,
    onSend: (String) -> Unit,
) {
    var draft by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = draft,
            onValueChange = { draft = it },
            modifier = Modifier.weight(1f),
            enabled = enabled,
            placeholder = { Text(stringResource(R.string.active_chat_input_hint)) },
            singleLine = false,
            maxLines = 4,
        )
        if (sending) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        } else {
            IconButton(
                enabled = enabled && draft.isNotBlank(),
                onClick = {
                    val toSend = draft
                    draft = ""
                    onSend(toSend)
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = stringResource(R.string.active_chat_send),
                )
            }
        }
    }
}

@Composable
private fun ErrorBanner(reason: String, onDismiss: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.active_chat_error, reason),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(R.string.active_chat_dismiss),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .clickable(onClick = onDismiss)
                    .padding(8.dp),
            )
        }
    }
}

@Preview(name = "Idle · Light", showBackground = true)
@Preview(name = "Idle · Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ActiveChatIdlePreview() {
    DroidMentorTheme {
        ActiveChatScreenContent(
            state = ActiveChatState.Idle(
                listOf(
                    ChatMessage("Hello", isUser = true),
                    ChatMessage("Hi, I'm your Android mentor.", isUser = false),
                ),
            ),
            onSend = {},
            onDismissError = {},
            onBack = {},
        )
    }
}

@Preview(name = "Sending", showBackground = true)
@Composable
private fun ActiveChatSendingPreview() {
    DroidMentorTheme {
        ActiveChatScreenContent(
            state = ActiveChatState.Sending(
                listOf(ChatMessage("How do I use Jetpack Compose?", isUser = true)),
            ),
            onSend = {},
            onDismissError = {},
            onBack = {},
        )
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
private fun ActiveChatErrorPreview() {
    DroidMentorTheme {
        ActiveChatScreenContent(
            state = ActiveChatState.Error(
                messages = listOf(ChatMessage("Ping", isUser = true)),
                reason = "No network",
            ),
            onSend = {},
            onDismissError = {},
            onBack = {},
        )
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
private fun ActiveChatEmptyPreview() {
    DroidMentorTheme {
        ActiveChatScreenContent(
            state = ActiveChatState.Idle(emptyList()),
            onSend = {},
            onDismissError = {},
            onBack = {},
        )
    }
}
