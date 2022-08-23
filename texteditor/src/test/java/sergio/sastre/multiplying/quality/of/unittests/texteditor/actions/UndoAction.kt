package sergio.sastre.multiplying.quality.of.unittests.texteditor.actions

import net.jqwik.api.stateful.Action
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.displayedTextEquals
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.redoActionsSizeIncreasedByOneUpToMax
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.undoActionsSizeEquals
import strikt.api.expectThat

class UndoAction : Action<TextEditor> {
    override fun precondition(state: TextEditor): Boolean =
        state.getModelState().undoTextStates.isNotEmpty()

    override fun run(state: TextEditor): TextEditor {
        // 1. save previous state
        val previousRedoTexts = state.getModelState().copy().redoTextStates
        val previousUndoTexts = state.getModelState().copy().undoTextStates

        // 2. perform action
        state.undo()

        // 3. assert new state
        expectThat(state.getModelState()) {
            displayedTextEquals(previousUndoTexts.peek().displayedText)
            undoActionsSizeEquals(previousUndoTexts.size - 1)
            redoActionsSizeIncreasedByOneUpToMax(
                previousActionsSize = previousRedoTexts.size,
                maxRedoActionsSize = state.bufferSize,
            )
        }

        return state
    }

    // override for more readable logs in the Jqwik report
    override fun toString(): String {
        return "UndoAction"
    }
}