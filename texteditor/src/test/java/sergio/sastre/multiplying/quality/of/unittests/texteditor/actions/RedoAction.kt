package sergio.sastre.multiplying.quality.of.unittests.texteditor.actions

import net.jqwik.api.stateful.Action
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.displayedTextEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.redoActionsSizeEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.undoActionsSizeIncreasedByOneUpToMax
import strikt.api.expectThat

class RedoAction : Action<TextEditor> {
    override fun precondition(state: TextEditor): Boolean =
        state.getModelState().redoTextStates.isNotEmpty()

    override fun run(state: TextEditor): TextEditor {
        // 1. save previous state
        val previousRedoTexts = state.getModelState().copy().redoTextStates
        val previousUndoTexts = state.getModelState().copy().undoTextStates

        // 2. perform action
        state.redo()

        // 3. assert new state
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

    // override for more readable logs in the Jqwik report
    override fun toString(): String {
        return "RedoAction"
    }
}