package sergio.sastre.multiplying.quality.of.unittests.texteditor.ui

import android.R.color
import android.text.InputType.*
import android.view.Gravity
import android.widget.EditText
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import sergio.sastre.multiplying.quality.of.unittests.texteditor.utils.TextChangeWatcher
import sergio.sastre.multiplying.quality.of.unittests.texteditor.utils.disableTextWatcherWhile
import sergio.sastre.multiplying.quality.of.unittests.texteditor.utils.setInputTypeWithoutWordSuggestions

/**
 * An EditText composable that reliably disables keyboard suggestions and requests focus on creation.
 *
 * This is necessary because Jetpack Compose 1.1.1 TextField does not support options like
 * [EditText.setPrivateImeOptions], which are needed to reliably disable the keyboard suggestions.
 *
 * Word suggestions might trigger TextField.onValueChanged multiple times on a single new input
 * due to CharSequence transformations (e.g. underline span) caused by the IME of the user.
 */
@Composable
fun EditTextWithoutSuggestions(
    modifier: Modifier = Modifier,
    onTextStateChanged: (String, Int) -> Unit,
    cursorPositionProvider: () -> Int,
    textProvider: () -> String,
) {

    val textWatcher = TextChangeWatcher(onTextStateChanged)

    AndroidView(
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
        factory = { ctx ->
            EditText(ctx).apply {
                gravity = Gravity.TOP
                setInputTypeWithoutWordSuggestions(
                    TYPE_TEXT_FLAG_MULTI_LINE or TYPE_CLASS_TEXT or TYPE_TEXT_FLAG_CAP_SENTENCES
                )
                setBackgroundResource(color.transparent)
                addTextChangedListener(textWatcher)
                requestFocus()
            }
        },
        update = { editText ->
            editText.disableTextWatcherWhile(textWatcher) {
                editText.setText(textProvider())
                editText.setSelection(cursorPositionProvider())
            }
        },
    )
}