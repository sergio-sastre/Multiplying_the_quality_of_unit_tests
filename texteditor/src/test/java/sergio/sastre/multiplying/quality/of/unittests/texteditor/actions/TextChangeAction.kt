package sergio.sastre.multiplying.quality.of.unittests.texteditor.actions

import net.jqwik.api.stateful.Action
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.displayedTextEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.redoActionsSizeEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.undoActionsSizeIncreasedByOneUpToMax
import strikt.api.expectThat

class TextChangeAction(
    private val newText: String,
    private val cursorPosition: Int,
) : Action<TextEditor> {
    override fun run(state: TextEditor): TextEditor {
        val previousUndoTexts = state.getModelState().copy().undoTextFieldStates

        state.textStateChange(newText, cursorPosition)

        expectThat(state.getModelState()) {
            displayedTextEquals(newText)
            undoActionsSizeIncreasedByOneUpToMax(
                previousActionsSize = previousUndoTexts.size,
                maxUndoActionsSize = state.bufferSize,
            )
            redoActionsSizeEquals(0)
        }

        return state
    }

    override fun toString(): String {
        return "TextChangedAction($newText)"
    }
}