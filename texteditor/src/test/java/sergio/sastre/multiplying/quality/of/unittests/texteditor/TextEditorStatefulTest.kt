package sergio.sastre.multiplying.quality.of.unittests.texteditor

import net.jqwik.api.*
import net.jqwik.api.stateful.ActionSequence
import net.jqwik.kotlin.api.ofLength
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.RedoAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.TextChangeAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.UndoAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.actions.edgecases.TextChangeSequenceOverBufferSizeAction
import sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions.*
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditor
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditorModelState.TextState
import strikt.api.expectThat

/**
 * Property-based test to showcase how to generate a sequence of random actions for
 * stateful testing with Jqwik
 */

class TextEditorStatefulTest {

    @Provide
    private fun arbitraryTextState(): Arbitrary<TextState> {
        // low end of range to reduce the generation time
        val textLengthRange = IntRange(1, 20)
        val arbText =
            Arbitraries.strings().ofLength(textLengthRange)
        val arbCursorPosition =
            arbText.flatMap { text -> Arbitraries.integers().between(0, text.length) }
        return Combinators.combine(arbText, arbCursorPosition)
            .`as` { text, cursorPosition ->
                TextState(text, cursorPosition)
            }
    }

    private fun arbitraryTextChangeAction(): Arbitrary<TextChangeAction> =
        arbitraryTextState().map { TextChangeAction(it.displayedText, it.cursorPosition) }

    @Provide
    fun arbitraryTextEditorActionSequence() =
        Arbitraries.sequences(
            Arbitraries.oneOf(
                arbitraryTextChangeAction(),
                Arbitraries.of(RedoAction()),
                Arbitraries.of(UndoAction()),
            )
        )

    @Property(tries = 1000)
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

        @Group
        inner class InExtraPropertyTest {

            @Provide
            private fun arbitraryTextChangeActionsAmount() =
                // amount over buffer size to force the buffer being at its max
                Arbitraries.integers()
                    .between(textEditor.bufferSize + 1, textEditor.bufferSize + 20)

            @Property
            fun sequenceOfTextActionsOverUndoMaxBuffer_keepsUndoMaxBufferAtMax(
                @ForAll("arbitraryTextState") textState: TextState,
                @ForAll("arbitraryTextChangeActionsAmount") textChangeActionsAmount: Int
            ) {
                // when
                repeat(textChangeActionsAmount) {
                    textEditor.textStateChange(textState.displayedText, textState.cursorPosition)
                }

                // then
                expectThat(textEditor.getModelState()) {
                    verifyUndoActionsCountIsAtMax()
                    undoActionsSizeEquals(textEditor.bufferSize)
                    redoActionIsEnabled(false)
                }
            }
        }

        @Group
        inner class WithTextChangeSequenceOverBufferSizeAction {

            private fun arbitraryTextFieldState(): Arbitrary<TextState> =
                arbitraryTextState().map { textState ->
                    TextState(textState.displayedText, textState.cursorPosition)
                }

            private fun arbitraryTextChangeSequenceOverBufferSize()
                    : Arbitrary<TextChangeSequenceOverBufferSizeAction> =
                arbitraryTextFieldState()
                    .list()
                    .ofMinSize(textEditor.bufferSize + 1)
                    .ofMaxSize(textEditor.bufferSize + 20) // reduce generation time
                    .map { TextChangeSequenceOverBufferSizeAction(it) }

            // We can also decide the frequency of each action with 'weight', like this
            @Provide
            fun arbitraryTextEditorActionSequence() =
                Arbitraries.sequences(
                    Arbitraries.frequencyOf(
                        Tuple.of(1, arbitraryTextChangeSequenceOverBufferSize()),
                        Tuple.of(19, arbitraryTextChangeAction()),
                        Tuple.of(20, Arbitraries.of(RedoAction())),
                        Tuple.of(20, Arbitraries.of(UndoAction())),
                    )
                )

            @Property
            fun sequenceOfTextActionsOverUndoMaxBuffer_keepsUndoMaxBufferAtMax(
                @ForAll("arbitraryTextEditorActionSequence") actionSequence: ActionSequence<TextEditor>
            ) {
                // "peek" accesses the internal state after each successful execution of an action’s run(..)
                actionSequence.peek { textEditor ->
                    collectStatsUndo(textEditor.getModelState()) // "undo at max" or "undo in between"
                    collectStatsRedo(textEditor.getModelState()) // "redo at max" or "redo in between"
                }.run(textEditor);

                verifyRedoActionsCountWasNotAlwayZero()
                verifyUndoActionsCountWasNotAlwaysZero()
                verifyUndoActionsCountIsAtMax()
            }
        }
    }
}