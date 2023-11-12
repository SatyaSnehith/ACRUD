package com.satyasnehith.acrud.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArticleTextField(
    placeholder: String = "Text",
    valueState: MutableState<String> = mutableStateOf(""),
    textStyle: TextStyle = TextStyle.Default,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
fun ArticleTextFieldPreview() {
    ArticleTextField()
}