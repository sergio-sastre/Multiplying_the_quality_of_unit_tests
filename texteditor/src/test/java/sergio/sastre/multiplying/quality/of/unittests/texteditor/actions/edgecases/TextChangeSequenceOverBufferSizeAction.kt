package sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.edgecases

import net.jqwik.api.stateful.Action
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.undoActionsSizeEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditorModelState.TextState
import strikt.api.expectThat
import strikt.assertions.isLessThanOrEqualTo

class TextChangeSequenceOverBufferSizeAction(
    private val newTextStates: List<TextState>,
) : Action<TextEditor> {
    override fun run(state: TextEditor): TextEditor {
        expectThat(state.bufferSize).isLessThanOrEqualTo(newTextStates.size)

        newTextStates.forEach { fieldState ->
            state.textStateChange(fieldState.displayedText, fieldState.cursorPosition)
        }

        expectThat(state.getModelState()) {
            undoActionsSizeEquals(state.bufferSize)
        }

        return state
    }

    override fun toString(): String =
        "TextChange${newTextStates.size}Times:\n ${newTextStates.toJqwikReportString()})"

    private fun List<TextState>.toJqwikReportString(): String =
        this.map { fieldState ->
            "TextChangeAction(${fieldState.displayedText},${fieldState.cursorPosition})"
        }
            .toList()
            .joinToString("\n")
}