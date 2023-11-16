package com.satyasnehith.acrud.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satyasnehith.acrud.ui.theme.ACRUDTheme

@Composable
fun ArticleTextField(
    placeholder: String = "Text",
    valueState: MutableState<String> = mutableStateOf(""),
    textStyle: TextStyle = TextStyle.Default,
    textCount: Int = 128,
    modifier: Modifier = Modifier
) {
    val textCountIndicator by remember {
        derivedStateOf { "${valueState.value.length} / $textCount" }
    }
    Box {
        Text(
            text = textCountIndicator,
            fontSize = 11.sp,
            color = colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 2.dp)
        )
        BasicTextField(
            value = valueState.value,
            onValueChange = { value ->
                if (value.length <= textCount)
                    valueState.value = value
            },
            modifier = Modifier
                .padding(16.dp, 16.dp)
                .padding(top = 4.dp)
                .fillMaxWidth()
                .then(modifier),
            textStyle = (
                    TextStyle(
                        color = colorScheme.onSurface
                    ).merge(textStyle)
                    ),
            cursorBrush = SolidColor(colorScheme.primary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (valueState.value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                color = colorScheme
                                    .onSurfaceVariant
                                    .copy(alpha = 0.7f),
                            ).merge(textStyle)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleTextFieldPreview() {
    ACRUDTheme {
        ArticleTextField()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ArticleTextFieldPreviewDark() {
    ACRUDTheme {
        ArticleTextField()
    }
}