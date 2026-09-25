package com.example.droidmentor.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.droidmentor.ui.theme.DroidMentorTheme


@Composable
fun MyIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier.size(size),
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyIconPreview() {
    DroidMentorTheme{
        Surface {
            MyIcon(
                icon = Icons.Default.Settings,
                modifier = Modifier.padding(16.dp),
                size = 24.dp,
            )
        }
    }
}
