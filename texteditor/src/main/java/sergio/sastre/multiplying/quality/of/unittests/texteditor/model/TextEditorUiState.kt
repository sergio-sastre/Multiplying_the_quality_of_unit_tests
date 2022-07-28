package sergio.sastre.multiplying.quality.of.unittests.texteditor.model

data class TextEditorUiState(
    val bodyText: String = "",
    val cursorPosition: Int = 0,
    val redoEnabled: Boolean = false,
    val undoEnabled: Boolean = false,
    val ignoreTextChanges: Boolean = false,
)
