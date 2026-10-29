package com.example.droidmentor.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.droidmentor.R
import com.example.droidmentor.container.appContainer
import com.example.droidmentor.ui.aboutScreen.AboutScreen
import com.example.droidmentor.ui.activeChatScreen.ActiveChatScreen
import com.example.droidmentor.ui.activeChatScreen.ActiveChatViewModel
import com.example.droidmentor.ui.common.MyCustomButton
import com.example.droidmentor.ui.common.PlaceholderScreen
import com.example.droidmentor.ui.settingsScreen.SettingsScreen
import com.example.droidmentor.ui.settingsScreen.SettingsViewModel
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
        ) {
            val service = LocalContext.current.appContainer.chatService
            val vm: ActiveChatViewModel = viewModel(factory = ActiveChatViewModel.Factory(service))
            ActiveChatScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.SETTINGS) {
            val repository = LocalContext.current.appContainer.settingsRepository
            val vm: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory(repository))
            SettingsScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
