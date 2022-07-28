package sergio.sastre.multiplying.quality.of.unittests.texteditor

import net.jqwik.api.statistics.Statistics
import sergio.sastre.multiplying.quality.of.unittests.texteditor.model.TextEditorModelState

const val UndoAtMax = "undo at max"
const val UndoInBetween = "undo in between"
const val RedoAtMax = "redo at max"
const val RedoInBetween = "redo in between"

fun collectStatsUndo(textEditorModelState: TextEditorModelState) {
    val bufferSize = textEditorModelState.bufferSize
    val undoStatesSize = textEditorModelState.undoTextFieldStates.size
    val reachedBufferSize = undoStatesSize == bufferSize
    val statistics = if (reachedBufferSize) UndoAtMax else UndoInBetween
    Statistics.collect(statistics)
}

fun collectStatsRedo(textEditorModelState: TextEditorModelState) {
    val bufferSize = textEditorModelState.bufferSize
    val undoStatesSize = textEditorModelState.redoTextFieldStates.size
    val reachedBufferSize = undoStatesSize == bufferSize
    val statistics = if (reachedBufferSize) RedoAtMax else RedoInBetween
    Statistics.collect(statistics)
}