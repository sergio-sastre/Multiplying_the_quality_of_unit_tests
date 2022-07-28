package sergio.sastre.multiplying.quality.of.unittests.texteditor.actions

import net.jqwik.api.stateful.Action
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.displayedTextEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.redoActionsSizeEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.undoActionsSizeIncreasedByOneUpToMax
import strikt.api.expectThat

class RedoAction : Action<TextEditor> {
    override fun precondition(state: TextEditor): Boolean =
        state.getModelState().redoTextFieldStates.isNotEmpty()

    override fun run(state: TextEditor): TextEditor {
        val previousRedoTexts = state.getModelState().copy().redoTextFieldStates
        val previousUndoTexts = state.getModelState().copy().undoTextFieldStates

        state.redo()

        expectThat(state.getModelState()) {
            displayedTextEquals(previousRedoTexts.last().displayedText)
            redoActionsSizeEquals(previousRedoTexts.size - 1)
            undoActionsSizeIncreasedByOneUpToMax(
                previousActionsSize = previousUndoTexts.size,
                maxUndoActionsSize = state.bufferSize,
            )
        }

        return state
    }

    override fun toString(): String {
        return "RedoAction"
    }
}