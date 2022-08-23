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
        // 1. save previous state
        val previousUndoTexts = state.getModelState().copy().undoTextStates

        // 2. perform action
        state.textStateChange(newText, cursorPosition)

        // 3. assert new state
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

    // override for more readable logs in the Jqwik report
    override fun toString(): String {
        return "TextChangeAction($newText, $cursorPosition)"
    }
}