package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

class TextEditor(
    val bufferSize: Int = BUFFER_SIZE
): TextEditorEvent {

    companion object {
        const val BUFFER_SIZE = 20
    }

    private var modelState = TextEditorModelState(bufferSize = bufferSize)

    override fun undo() {
        modelState =
            modelState.copy(
                redoTextFieldStates = modelState.redoTextFieldStates.apply { push(modelState.textFieldState) },
                textFieldState = modelState.undoTextFieldStates.peek(),
                undoTextFieldStates = modelState.undoTextFieldStates.apply { pop() },
            )
    }

    override fun redo() {
        modelState = modelState.copy(
            undoTextFieldStates = modelState.undoTextFieldStates.apply { push(modelState.textFieldState) },
            textFieldState = modelState.redoTextFieldStates.peek(),
            redoTextFieldStates = modelState.redoTextFieldStates.apply { pop() },
        )
    }

    override fun textStateChange(newText: String, cursorPosition: Int) {
        modelState = modelState.copy(
            undoTextFieldStates = modelState.undoTextFieldStates.apply { push(modelState.textFieldState) },
            textFieldState = modelState.textFieldState.copy(
                displayedText = newText,
                cursorPosition = cursorPosition,
            ),
            redoTextFieldStates = modelState.redoTextFieldStates.apply { clear() },
        )
    }

    fun getModelState(): TextEditorModelState = modelState.copy()
}