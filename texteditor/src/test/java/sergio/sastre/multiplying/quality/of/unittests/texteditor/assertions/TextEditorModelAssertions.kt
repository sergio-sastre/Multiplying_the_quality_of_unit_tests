package sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions

import net.jqwik.api.statistics.Statistics
import sergio.sastre.multiplying.quality.of.unittests.texteditor.RedoAtMax
import sergio.sastre.multiplying.quality.of.unittests.texteditor.UndoAtMax
import sergio.sastre.multiplying.quality.of.unittests.texteditor.collectStatsRedo
import sergio.sastre.multiplying.quality.of.unittests.texteditor.collectStatsUndo
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditorModelState
import strikt.api.Assertion
import java.lang.Integer.min
import java.util.function.Predicate

fun Assertion.Builder<TextEditorModelState>.undoActionsSizeIncreasedByOneUpToMax(
    previousActionsSize: Int,
    maxUndoActionsSize: Int
) =
    assert("undoActions size = old undoActions size + 1, but never more than $maxUndoActionsSize") {
        val currentUndoActionsSize = it.undoTextFieldStates.size
        if (currentUndoActionsSize == min(maxUndoActionsSize, previousActionsSize + 1)) {
            pass()
        } else if (currentUndoActionsSize != previousActionsSize + 1) {
            fail(
                description = """
                undoActions size mismatch -> 
                current = $currentUndoActionsSize,
                previous = $previousActionsSize,
                but current != previous + 1: $currentUndoActionsSize != $previousActionsSize + 1
            """.trimIndent()
            )
        } else if (currentUndoActionsSize > maxUndoActionsSize) {
            fail(
                description = """
                undoActions is greater than the maximum ->
                undoActions size = $currentUndoActionsSize, 
                max size = $maxUndoActionsSize
                but undoActions size > max size : $currentUndoActionsSize > $maxUndoActionsSize
            """.trimIndent()
            )
        } else {
            fail(description = "failed for an unknown reason")
        }
    }

fun Assertion.Builder<TextEditorModelState>.redoActionsSizeIncreasedByOneUpToMax(
    previousActionsSize: Int,
    maxRedoActionsSize: Int
) =
    assert("redoActions size = old redoActions size + 1, but never more than $maxRedoActionsSize") {
        val currentRedoActionsSize = it.redoTextFieldStates.size
        if (currentRedoActionsSize == min(maxRedoActionsSize, previousActionsSize + 1)) {
            pass()
        } else if (currentRedoActionsSize != previousActionsSize + 1) {
            fail(
                description = """
                redoActions size mismatch -> 
                current = $currentRedoActionsSize,
                previous = $previousActionsSize,
                but current != previous + 1: $currentRedoActionsSize != $previousActionsSize + 1
            """.trimIndent()
            )
        } else if (currentRedoActionsSize > maxRedoActionsSize) {
            fail(
                description = """
                redoActions is greater than the maximum ->
                redoActions size = $currentRedoActionsSize, 
                max size = $maxRedoActionsSize
                but redoActions size > max size : $currentRedoActionsSize > $maxRedoActionsSize
            """.trimIndent()
            )
        } else {
            fail(description = "failed for an unknown reason")
        }
    }

fun Assertion.Builder<TextEditorModelState>.redoActionsSizeEquals(size: Int) =
    assert("redoActions size equals $size") {
        val currentRedoActionsSize = it.redoTextFieldStates.size
        if (currentRedoActionsSize == size) {
            pass()
        } else {
            fail("redo actions size is $currentRedoActionsSize instead of $size")
        }
    }

fun Assertion.Builder<TextEditorModelState>.undoActionIsEnabled(enabled: Boolean) =
    assert("undo action is $enabled") {
        val undoEnabled = it.toUiState().undoEnabled
        if (undoEnabled == enabled) {
            pass()
        } else {
            fail("undo action is $undoEnabled instead of $enabled")
        }
    }

fun Assertion.Builder<TextEditorModelState>.redoActionIsEnabled(enabled: Boolean) =
    assert("redo action is $enabled") {
        val redoEnabled = it.toUiState().redoEnabled
        if (redoEnabled == enabled) {
            pass()
        } else {
            fail("redo action is $redoEnabled instead of $enabled")
        }
    }

fun Assertion.Builder<TextEditorModelState>.undoActionsSizeEquals(size: Int) =
    assert("undoActions size equals $size") {
        val currentUndoActionsSize = it.undoTextFieldStates.size
        if (currentUndoActionsSize == size) {
            pass()
        } else {
            fail("undo actions size is $currentUndoActionsSize instead of $size")
        }
    }

fun Assertion.Builder<TextEditorModelState>.verifyUndoActionsCountIsAtMax() = run {
    collectStatsUndo(subject)
    Statistics.coverage { checker ->
        checker.check(UndoAtMax).count(Predicate { c -> c > 0 })
    }
}

fun Assertion.Builder<TextEditorModelState>.verifyRedoActionsCountIsAtMax() = run {
    collectStatsRedo(subject)
    Statistics.coverage { checker ->
        checker.check(RedoAtMax).count(Predicate { c -> c > 0 })
    }
}


fun Assertion.Builder<TextEditorModelState>.displayedTextEquals(text: String?) =
    assert("currentText equals $text") {
        if (it.textFieldState.displayedText == text) {
            pass()
        } else {
            fail("displayed text != $text")
        }
    }
