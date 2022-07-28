package sergio.sastre.multiplying.quality.of.unittests.texteditor.assertions

import net.jqwik.api.statistics.Statistics
import sergio.sastre.multiplying.quality.of.unittests.texteditor.RedoAtMax
import sergio.sastre.multiplying.quality.of.unittests.texteditor.RedoInBetween
import sergio.sastre.multiplying.quality.of.unittests.texteditor.UndoAtMax
import sergio.sastre.multiplying.quality.of.unittests.texteditor.UndoInBetween
import java.util.function.Predicate

fun verifyRedoActionsCountIsAtMax() = run {
    Statistics.coverage { checker ->
        checker.check(RedoAtMax).count(Predicate { times -> times > 0 })
    }
}

fun verifyUndoActionsCountIsAtMax() = run {
    Statistics.coverage { checker ->
        checker.check(UndoAtMax).count(Predicate { times -> times > 0 })
    }
}

fun verifyUndoActionsCountWasNotAlwaysZero() = run {
    Statistics.coverage { checker ->
        checker.check(UndoInBetween).count(Predicate { times -> times > 0 })
    }
}

fun verifyRedoActionsCountWasNotAlwayZero() = run {
    Statistics.coverage { checker ->
        checker.check(RedoInBetween).count(Predicate { times -> times > 0 })
    }
}
