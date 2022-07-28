package sergio.sastre.multiplying.quality.of.unittests.texteditor.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextEditorTextField(
    modifier: Modifier = Modifier,
    textProvider: () -> String,
    cursorPositionProvider: () -> Int,
    onTextStateChanged: (String, Int) -> Unit,
) {

    EditTextWithoutSuggestions(
        modifier = modifier.fillMaxWidth(),
        cursorPositionProvider = cursorPositionProvider,
        onTextStateChanged = onTextStateChanged,
        textProvider = textProvider,
    )
}