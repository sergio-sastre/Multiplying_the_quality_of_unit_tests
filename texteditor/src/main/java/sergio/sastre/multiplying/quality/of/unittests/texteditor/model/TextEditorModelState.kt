package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

import sergio.sastre.multiplying.quality.of.unittests.texteditor.datastructures.CircularStack
import java.util.Stack

data class TextEditorModelState(
    val bufferSize: Int,
    val textFieldState: TextFieldState = TextFieldState(),
    val undoTextFieldStates: Stack<TextFieldState> = CircularStack(bufferSize),
    val redoTextFieldStates: Stack<TextFieldState> = CircularStack(bufferSize),
) {

    data class TextFieldState(
        val displayedText: String = "",
        val cursorPosition: Int = 0,
    )

    // Ensure a deep copy of the Stack, and not a shallow copy with the reference which can be altered
    @Suppress("UNCHECKED_CAST")
    fun copy(): TextEditorModelState {
        return TextEditorModelState(
            bufferSize = bufferSize,
            textFieldState = textFieldState,
            undoTextFieldStates = undoTextFieldStates.clone() as CircularStack<TextFieldState>,
            redoTextFieldStates = redoTextFieldStates.clone() as CircularStack<TextFieldState>,
        )
    }

    fun toUiState(): TextEditorUiState =
        TextEditorUiState(
            bodyText = textFieldState.displayedText,
            cursorPosition = textFieldState.cursorPosition,
            redoEnabled = redoTextFieldStates.isNotEmpty(),
            undoEnabled = undoTextFieldStates.isNotEmpty(),
        )
}