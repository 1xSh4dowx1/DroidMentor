package com.example.droidmentor.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class AppTheme {
    SYSTEM,
    DARK,
    OCEAN,
    FOREST,
    MIDNIGHT
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val OceanColorScheme = lightColorScheme(
    primary = OceanPrimary,
    onPrimary = OceanOnPrimary,
    primaryContainer = OceanPrimaryContainer,
    onPrimaryContainer = OceanOnPrimaryContainer,
    secondary = OceanSecondary,
    tertiary = OceanTertiary,
    background = OceanBackground,
    surface = OceanSurface,
)

private val MidnightColorScheme = darkColorScheme(
    primary = MidnightPrimary,
    onPrimary = MidnightOnPrimary,
    primaryContainer = MidnightPrimaryContainer,
    onPrimaryContainer = MidnightOnPrimaryContainer,
    secondary = MidnightSecondary,
    tertiary = MidnightTertiary,
    background = MidnightBackground,
    surface = MidnightSurface,
)

private val ForestColorScheme = lightColorScheme(
    primary = ForestPrimary,
    onPrimary = ForestOnPrimary,
    primaryContainer = ForestPrimaryContainer,
    onPrimaryContainer = ForestOnPrimaryContainer,
    secondary = ForestSecondary,
    tertiary = ForestTertiary,
    background = ForestBackground,
    surface = ForestSurface,
)

val themeColorSchemes: Map<AppTheme, ColorScheme> = mapOf(
    AppTheme.DARK to DarkColorScheme,
    AppTheme.OCEAN to OceanColorScheme,
    AppTheme.MIDNIGHT to MidnightColorScheme,
    AppTheme.FOREST to ForestColorScheme,
)

@Composable
fun DroidMentorTheme(
    theme: AppTheme = AppTheme.SYSTEM,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ (AppTheme.SYSTEM)
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when (theme) {
        AppTheme.SYSTEM -> when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> OceanColorScheme
        }
        AppTheme.DARK -> DarkColorScheme
        AppTheme.OCEAN -> OceanColorScheme
        AppTheme.MIDNIGHT -> MidnightColorScheme
        AppTheme.FOREST -> ForestColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ThemePreviews() {
    Column(modifier = Modifier.fillMaxSize()) {
        themeColorSchemes.keys.forEach { appTheme ->
            DroidMentorTheme(theme = appTheme) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = appTheme.name,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}