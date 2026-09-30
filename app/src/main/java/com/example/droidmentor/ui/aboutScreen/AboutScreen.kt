package com.example.droidmentor.ui.aboutScreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidmentor.R
import com.example.droidmentor.ui.common.AppScaffold
import com.example.droidmentor.ui.theme.DroidMentorTheme

private data class Author(val name: String, val number: String)

private val authors = listOf(
    Author("Duarte Rodrigues", "52599"),
    Author("Isadora Mendes", "52559"),
    Author("Daniel Viegas", "52885"),
)

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        title = stringResource(R.string.about_title),
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
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(R.string.about_description),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(R.string.about_authors),
                style = MaterialTheme.typography.titleMedium,
            )
            authors.forEach { author ->
                Text(
                    text = "${author.name} · ${author.number}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text(
                text = stringResource(R.string.about_course),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AboutScreenPreview() {
    DroidMentorTheme {
        AboutScreen(onBack = {})
    }
}
