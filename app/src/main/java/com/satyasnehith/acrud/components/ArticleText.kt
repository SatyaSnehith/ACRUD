package com.satyasnehith.acrud.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.satyasnehith.acrud.ui.theme.ACRUDTheme

@Composable
fun ArticleText(
    text: String = "",
    textStyle: TextStyle = TextStyle.Default,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .fillMaxWidth()
            .then(modifier),
        style = (
            TextStyle(
                color = colorScheme.onSurface
            ).merge(textStyle)
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleTextPreview() {
    ACRUDTheme {
        ArticleText()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ArticleTextPreviewDark() {
    ACRUDTheme {
        ArticleText()
    }
}