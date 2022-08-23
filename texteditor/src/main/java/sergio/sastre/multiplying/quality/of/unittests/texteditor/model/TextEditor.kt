package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

class TextEditor(
    val bufferSize: Int = DEFAULT_BUFFER_SIZE
): TextEditingEvent {

    companion object {
        const val DEFAULT_BUFFER_SIZE = 20
    }

    private var modelState = TextEditorModelState(bufferSize = bufferSize)

    override fun undo() {
        modelState =
            modelState.copy(
                redoTextStates = modelState.redoTextStates.apply { push(modelState.textState) },
                textState = modelState.undoTextStates.peek(),
                undoTextStates = modelState.undoTextStates.apply { pop() },
            )
    }

    override fun redo() {
        modelState = modelState.copy(
            undoTextStates = modelState.undoTextStates.apply { push(modelState.textState) },
            textState = modelState.redoTextStates.peek(),
            redoTextStates = modelState.redoTextStates.apply { pop() },
        )
    }

    override fun textStateChange(newText: String, cursorPosition: Int) {
        modelState = modelState.copy(
            undoTextStates = modelState.undoTextStates.apply { push(modelState.textState) },
            textState = modelState.textState.copy(
                displayedText = newText,
                cursorPosition = cursorPosition,
            ),
            redoTextStates = modelState.redoTextStates.apply { clear() },
        )
    }

    fun getModelState(): TextEditorModelState = modelState.copy()
}