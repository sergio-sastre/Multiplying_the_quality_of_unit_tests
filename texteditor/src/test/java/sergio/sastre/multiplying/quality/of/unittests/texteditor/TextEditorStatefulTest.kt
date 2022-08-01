package sergio.sastre.multiplying.quality.of.unittests.texteditor

import net.jqwik.api.*
import net.jqwik.api.stateful.ActionSequence
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.RedoAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.TextChangeAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.UndoAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.*
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import strikt.api.expectThat

/**
 * Property-based test to showcase how to generate a sequence of random actions for
 * stateful testing with Jqwik
 */
typealias TextState = Pair<String, Int>

class TextEditorStatefulTest {

    @Provide
    private fun arbitraryTextState(): Arbitrary<TextState> {
        val maxTextLength = 20
        val arbText = Arbitraries.strings().ofMaxLength(maxTextLength)
        val arbCursorPosition = Arbitraries.integers().between(0, maxTextLength)

        return Combinators.combine(arbText, arbCursorPosition)
            .`as` { text, cursorPosition ->
                TextState(text, cursorPosition)
            }
    }

    private fun arbitraryTextChangeAction(): Arbitrary<TextChangeAction> =
        arbitraryTextState().map { TextChangeAction(it.first, it.second) }


    @Provide
    fun arbitraryTextEditorActionSequence() =
        Arbitraries.sequences(
            Arbitraries.oneOf(
                arbitraryTextChangeAction(),
                Arbitraries.of(RedoAction()),
                Arbitraries.of(UndoAction()),
            )
        )

    @Property
    fun executeRandomSequenceOfActions_textEditorModelStateIsCorrect(
        @ForAll("arbitraryTextEditorActionSequence") actionSequence: ActionSequence<TextEditor>
    ) {
        // "peek" accesses the internal state after each successful execution of an action’s run(..)
        actionSequence.peek { textEditor ->
            collectStatsUndo(textEditor.getModelState()) // "undo at max" or "undo in between"
            collectStatsRedo(textEditor.getModelState()) // "redo at max" or "redo in between"
        }.run(TextEditor());

        verifyRedoActionsCountWasNotAlwayZero()
        verifyUndoActionsCountWasNotAlwaysZero()
    }

    @Group
    inner class ForceBufferAtMax {

        // given
        private val textEditor = TextEditor()

        @Provide
        private fun arbitraryTextChangeActionsAmount() =
            // amount over buffer size to force the buffer being at its max
            Arbitraries.integers().between(textEditor.bufferSize + 1, textEditor.bufferSize + 20)

        @Property
        fun sequenceOfTextActionsOverUndoMaxBuffer_keepsUndoMaxBufferAtMax(
            @ForAll("arbitraryTextState") textState: TextState,
            @ForAll("arbitraryTextChangeActionsAmount") textChangeActionsAmount: Int
        ) {
            // when
            repeat(textChangeActionsAmount) {
                textEditor.textStateChange(textState.first, textState.second)
            }

            // then
            expectThat(textEditor.getModelState()) {
                verifyUndoActionsCountIsAtMax()
                undoActionsSizeEquals(textEditor.bufferSize)
                redoActionIsEnabled(false)
            }
        }

        @Property
        fun sequenceOfUndoActionsAtMaxBuffer_undoIsDisabled(
            @ForAll("arbitraryTextState") textState: TextState,
            @ForAll("arbitraryTextChangeActionsAmount") textChangeActionsAmount: Int
        ) {
            // when
            repeat(textChangeActionsAmount) {
                textEditor.textStateChange(textState.first, textState.second)
            }

            val undoActionsAmount = textEditor.getModelState().copy().undoTextFieldStates.size
            repeat(undoActionsAmount) {
                textEditor.undo()
            }

            // then
            expectThat(textEditor.getModelState()) {
                verifyRedoActionsCountIsAtMax()
                undoActionIsEnabled(false)
                redoActionIsEnabled(true)
                redoActionsSizeEquals(textEditor.bufferSize)
            }
        }

        @Property
        fun sequenceOfRedoActionsAtMaxBuffer_redoIsDisabled(
            @ForAll("arbitraryTextState") textState: TextState,
            @ForAll("arbitraryTextChangeActionsAmount") textChangeActionsAmount: Int
        ) {
            // when
            repeat(textChangeActionsAmount) {
                textEditor.textStateChange(textState.first, textState.second)
            }

            val undoActionsAmount = textEditor.getModelState().copy().undoTextFieldStates.size
            repeat(undoActionsAmount) {
                textEditor.undo()
            }

            val redoActionsAmount = textEditor.getModelState().copy().redoTextFieldStates.size
            repeat(redoActionsAmount) {
                textEditor.redo()
            }

            // then
            expectThat(textEditor.getModelState()) {
                undoActionIsEnabled(true)
                redoActionIsEnabled(false)
                undoActionsSizeEquals(textEditor.bufferSize)
            }
        }
    }
}