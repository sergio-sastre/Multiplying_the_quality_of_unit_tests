package sergio.sastre.multiplying.quality.of.unittests.texteditor

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditingViewModel
import sergio.sastre.multiplying.quality.of.unittests.texteditor.ui.*

class TextEditorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            TextEditorScreen()
        }
    }
}

@Composable
fun TextEditorScreen() {
    val viewModel: TextEditingViewModel = viewModel()
    val viewState by remember(viewModel) { viewModel.uiState }.collectAsState()

    MaterialTheme {
        TextEditorContent(
            textField = {
                TextEditorTextField(
                    modifier = Modifier
                        .weight(weight = 1f, true)
                        .fillMaxWidth(),
                    textProvider = { viewState.bodyText },
                    cursorPositionProvider = { viewState.cursorPosition },
                    onTextStateChanged = viewModel::textStateChange,
                )
            },
            textEditActionBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    UndoButton(
                        modifier = Modifier.weight(1f),
                        isEnabledProvider = { viewState.undoEnabled },
                        onUndo = viewModel::undo
                    )
                    RedoButton(
                        modifier = Modifier.weight(1f),
                        isEnabledProvider = { viewState.redoEnabled },
                        onRedo = viewModel::redo,
                    )
                }
            }
        )
    }
}
