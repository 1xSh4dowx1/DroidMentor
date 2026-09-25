package com.example.droidmentor.ui.common

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidmentor.ui.theme.AppTheme
import com.example.droidmentor.ui.theme.DroidMentorTheme

@Composable
fun MyCustomButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = buttonShape(),
        contentPadding = ButtonContentPadding,
        colors = buttonColors(),
    ) {
        if (icon != null) {
            MyIcon(icon = icon)
            Spacer(modifier = Modifier.width(10.dp))
        }
        MyLabel(text = text)
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyCustomButtonPreview() {
    DroidMentorTheme{
        Surface {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                MyCustomButton(
                    text = "New Chat",
                    icon = Icons.Default.Message,
                    onClick = {},
                )
                MyCustomButton(
                    text = "Chat History",
                    icon = Icons.Default.History,
                    onClick = {},
                )
                MyCustomButton(
                    text = "Settings",
                    icon = Icons.Default.Settings,
                    onClick = {},
                )
                MyCustomButton(
                    text = "Disabled",
                    onClick = {},
                    enabled = false,
                )
            }
        }
    }
}
