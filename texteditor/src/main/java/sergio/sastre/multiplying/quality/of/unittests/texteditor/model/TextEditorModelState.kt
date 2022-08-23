package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

import sergio.sastre.multiplying.quality.of.unittests.texteditor.datastructures.CircularBuffer
import java.util.Stack

data class TextEditorModelState(
    val bufferSize: Int,
    val textState: TextState = TextState(),
    val undoTextStates: Stack<TextState> = CircularBuffer(bufferSize),
    val redoTextStates: Stack<TextState> = CircularBuffer(bufferSize),
) {

    data class TextState(
        val displayedText: String = "",
        val cursorPosition: Int = 0,
    )

    // Ensure a deep copy of the Stack, and not a shallow copy with the reference which can be altered
    @Suppress("UNCHECKED_CAST")
    fun copy(): TextEditorModelState {
        return TextEditorModelState(
            bufferSize = bufferSize,
            textState = textState,
            undoTextStates = undoTextStates.clone() as CircularBuffer<TextState>,
            redoTextStates = redoTextStates.clone() as CircularBuffer<TextState>,
        )
    }

    fun toUiState(): TextEditorUiState =
        TextEditorUiState(
            bodyText = textState.displayedText,
            cursorPosition = textState.cursorPosition,
            redoEnabled = redoTextStates.isNotEmpty(),
            undoEnabled = undoTextStates.isNotEmpty(),
        )
}