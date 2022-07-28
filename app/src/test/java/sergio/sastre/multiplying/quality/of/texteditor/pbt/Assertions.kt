package sergio.sastre.multiplying.quality.of.texteditor.pbt

import strikt.api.Assertion

fun Assertion.Builder<String?>.isNullOrDoesNotContain(text: String) =
    assert("is null or does not contain") {
        when (it == null) {
            true -> pass()
            else -> if (!it.contains(text)) pass() else fail(description = "'$it' does contain '$text'")
        }
    }

fun Assertion.Builder<String>.doesNotContain(text: String) =
    assert("does not contain") {
        when (it.contains(text)) {
            true -> fail(description = "'$it' does contain '$text'")
            else -> pass()
        }
    }
