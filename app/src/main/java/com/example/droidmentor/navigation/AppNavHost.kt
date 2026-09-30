package com.example.droidmentor.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.droidmentor.R
import com.example.droidmentor.ui.aboutScreen.AboutScreen
import com.example.droidmentor.ui.common.MyCustomButton
import com.example.droidmentor.ui.common.PlaceholderScreen
import com.example.droidmentor.ui.settingsScreen.SettingsScreen
import com.example.droidmentor.ui.titleScreen.TitleScreen

/**
 * App navigation graph. Shared file: keep changes small and frequent.
 *
 * TODO(C, next milestone): decide [startDestination] from the "last active chat" (needs the
 *  DataStore owner to be agreed with B). When it exists, the back stack must still be
 *  Title -> Chat History -> Active Chat so that "back" behaves as in Figure 1.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.TITLE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Routes.TITLE) {
            TitleScreen(
                onChatHistory = { navController.navigate(Routes.CHAT_HISTORY) },
                onSettings = { navController.navigate(Routes.SETTINGS) },
                onAbout = { navController.navigate(Routes.ABOUT) },
            )
        }

        composable(Routes.CHAT_HISTORY) {
            // TODO(B): replace this placeholder with the real ChatHistoryScreen.
            PlaceholderScreen(
                title = stringResource(R.string.chat_history_title),
                onBack = { navController.popBackStack() },
            ) {
                MyCustomButton(
                    text = stringResource(R.string.chat_history_new_chat),
                    icon = Icons.Default.Add,
                    onClick = { navController.navigate(Routes.activeChat()) },
                )
                MyCustomButton(
                    text = stringResource(R.string.chat_history_open_demo),
                    icon = Icons.Default.ChatBubble,
                    onClick = { navController.navigate(Routes.activeChat(chatId = 1L)) },
                )
            }
        }

        composable(
            route = Routes.ACTIVE_CHAT_PATTERN,
            arguments = listOf(
                navArgument(Routes.ARG_CHAT_ID) {
                    type = NavType.LongType
                    defaultValue = Routes.NEW_CHAT_ID
                },
            ),
        ) { entry ->
            val chatId = entry.arguments?.getLong(Routes.ARG_CHAT_ID) ?: Routes.NEW_CHAT_ID
            // TODO(A): replace this placeholder with the real ActiveChatScreen.
            PlaceholderScreen(
                title = stringResource(R.string.active_chat_title),
                onBack = { navController.popBackStack() },
            ) {
                Text(
                    text = if (chatId == Routes.NEW_CHAT_ID) {
                        stringResource(R.string.active_chat_new)
                    } else {
                        stringResource(R.string.active_chat_existing, chatId)
                    },
                )
            }
        }

        composable(Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.SETTINGS) {
            // Plain remember (not rememberSaveable) so the key is never written to the saved-state Bundle.
            // TODO(C, milestone 2): replace with SettingsViewModel + SettingsRepository.
            var apiKey by remember { mutableStateOf("") }
            SettingsScreen(
                apiKey = apiKey,
                onApiKeyChange = { apiKey = it },
                onSave = { /* TODO(C): persist through SettingsRepository */ },
                onBack = { navController.popBackStack() },
            )
        }
    }
}
