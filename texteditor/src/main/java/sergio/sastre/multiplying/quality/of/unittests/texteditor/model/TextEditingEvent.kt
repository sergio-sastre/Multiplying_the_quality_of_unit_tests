package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

interface TextEditingEvent {

    fun textStateChange(newText: String, cursorPosition: Int)

    fun undo()

    fun redo()
}
