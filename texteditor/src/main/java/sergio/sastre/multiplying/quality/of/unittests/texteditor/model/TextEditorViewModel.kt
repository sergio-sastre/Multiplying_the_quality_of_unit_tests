package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TextEditorViewModel : ViewModel(), TextEditorEvent {

    val textEditor = TextEditor(20)
    val uiState = MutableStateFlow(TextEditorUiState())

    override fun textStateChange(newText: String, cursorPosition: Int) {
        textEditor.textStateChange(newText, cursorPosition)
        uiState.value = textEditor.getModelState().toUiState()
    }

    override fun undo() {
        textEditor.undo()
        uiState.value = textEditor.getModelState().toUiState()
    }

    override fun redo() {
        textEditor.redo()
        uiState.value = textEditor.getModelState().toUiState()
    }
}

