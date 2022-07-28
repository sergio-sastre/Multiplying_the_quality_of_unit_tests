package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

interface TextEditorEvent {

    fun textStateChange(newText: String, cursorPosition: Int)

    fun undo()

    fun redo()
}
