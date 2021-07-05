package sergio.sastre.multiplying.quality.of.unittests.pbt

import net.jqwik.api.statistics.Statistics.*

fun statsUpperCase(password: String?) {
    val withUpperCase = password?.contains("[A-Z]".toRegex())
    label("Upper case")
        .collect(if (withUpperCase == true) "with upper case" else "without upper case")
}

fun statsLowerCase(password: String?) {
    val withLowerCase = password?.contains("[a-z]".toRegex())
    label("Lower case")
        .collect(if (withLowerCase == true) "with lower case" else "without lower case")
}

fun statsDigits(password: String?) {
    val withDigits = password?.contains("[0-9]".toRegex())
    label("Digits")
        .collect(if (withDigits == true) "with digits" else "without digits")
}

fun statsPasswordLength(password: String?) {
    val min6Chars = password?.length ?: 0 > 5
    label("Password Length")
        .collect(if (min6Chars) "min 6 chars" else "max 5 chars")
}

fun statsBlanks(password: String?) {
    val withBlanks = password?.contains("\\s".toRegex())
    label("Blanks")
        .collect(if (withBlanks == true) "with blanks" else "without blanks")
}

fun collectPasswordStats(password: String?) {
    statsUpperCase(password)
    statsLowerCase(password)
    statsDigits(password)
    statsPasswordLength(password)
    statsBlanks(password)
}