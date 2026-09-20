package com.example.droidmentor.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val iconInfo     = "iconInfo" to Icons.Default.Info
private val iconHistory  = "iconHistory" to Icons.Default.History
private val iconNewChat  = "iconNewChat" to Icons.Default.ChatBubble
private val iconSettings = "iconSettings" to Icons.Default.Settings
private val iconAddChat  = "iconAddChat" to Icons.Default.Add
private val iconMenu     = "iconMenu" to Icons.Default.Menu

private val allIcons = listOf(
    iconInfo,
    iconHistory,
    iconNewChat,
    iconSettings,
    iconAddChat,
    iconMenu,
)

@Preview(showBackground = true)
@Composable
private fun IconPreviews() {
    DroidMentorTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                allIcons.forEach { (name, icon) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = icon, contentDescription = name)
                        Spacer(Modifier.width(16.dp))
                        Text(name)
                    }
                }
            }
        }
    }
}