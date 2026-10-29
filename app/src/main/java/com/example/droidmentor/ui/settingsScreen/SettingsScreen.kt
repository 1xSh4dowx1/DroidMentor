package com.example.droidmentor.ui.settingsScreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.maskApiKey
import com.example.droidmentor.R
import com.example.droidmentor.ui.common.AppScaffold
import com.example.droidmentor.ui.common.MyCustomButton
import com.example.droidmentor.ui.theme.DroidMentorTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsScreenContent(
        state = viewModel.state,
        onApiKeyChange = viewModel::onApiKeyChange,
        onSave = viewModel::save,
        onClear = viewModel::clear,
        onBack = onBack,
        modifier = modifier,
    )
}

@Composable
private fun SettingsScreenContent(
    state: SettingsState,
    onApiKeyChange: (String) -> Unit,
    onSave: () -> Unit,
    onClear: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Only the visibility toggle is saved across rotation, never the key itself.
    var keyVisible by rememberSaveable { mutableStateOf(false) }

    AppScaffold(
        title = stringResource(R.string.settings_title),
        onBack = onBack,
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = state.apiKey,
                onValueChange = onApiKeyChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.settings_api_key_label)) },
                supportingText = { Text(stringResource(R.string.settings_api_key_hint)) },
                singleLine = true,
                visualTransformation = if (keyVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrectEnabled = false,
                ),
                trailingIcon = {
                    IconButton(onClick = { keyVisible = !keyVisible }) {
                        Icon(
                            imageVector = if (keyVisible) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = stringResource(
                                if (keyVisible) R.string.settings_hide_key else R.string.settings_show_key,
                            ),
                        )
                    }
                },
            )
            MyCustomButton(
                text = stringResource(R.string.settings_save),
                onClick = onSave,
                enabled = state.canSave,
            )
            if (state.savedApiKey != null) {
                Text(
                    text = if (state.justSaved) {
                        stringResource(R.string.settings_saved)
                    } else {
                        stringResource(R.string.settings_saved_key, maskApiKey(state.savedApiKey))
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TextButton(onClick = onClear) {
                    Text(stringResource(R.string.settings_remove_key))
                }
            }
        }
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
private fun SettingsScreenEmptyPreview() {
    DroidMentorTheme {
        SettingsScreenContent(
            state = SettingsState(),
            onApiKeyChange = {},
            onSave = {},
            onClear = {},
            onBack = {},
        )
    }
}

@Preview(name = "Saved - Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenSavedPreview() {
    val key = "AIzaExampleKey1234567890abcdef"
    DroidMentorTheme {
        SettingsScreenContent(
            state = SettingsState(apiKey = key, savedApiKey = key),
            onApiKeyChange = {},
            onSave = {},
            onClear = {},
            onBack = {},
        )
    }
}

@Preview(name = "Landscape", showBackground = true, widthDp = 640, heightDp = 360)
@Composable
private fun SettingsScreenLandscapePreview() {
    DroidMentorTheme {
        SettingsScreenContent(
            state = SettingsState(apiKey = "AIzaExampleKey1234567890abcdef"),
            onApiKeyChange = {},
            onSave = {},
            onClear = {},
            onBack = {},
        )
    }
}
