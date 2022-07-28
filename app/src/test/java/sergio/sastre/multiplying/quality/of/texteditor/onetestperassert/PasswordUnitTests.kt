package sergio.sastre.multiplying.quality.of.texteditor.onetestperassert

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import sergio.sastre.multiplying.quality.of.texteditor.model.validators.StrongPasswordValidator
import sergio.sastre.multiplying.quality.of.texteditor.pbt.doesNotContain
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull
import strikt.assertions.isNull

/**
 * This class shows the problem of having one assert per test.
 * Although it has improved over having multiple asserts per test,
 * all the tests have the same structure, and every time we add a new requirement
 * we are basically copy-pasting one of the previous tests and adjust the values.
 */
class PasswordUnitTests {
    private val passwordValidator = StrongPasswordValidator()

    @Nested
    inner class PasswordValidatorShowsCorrectError {

        @Test
        fun when_PasswordWithoutUpperCase() {
            val actualError = passwordValidator.validate("123456")
            expectThat(actualError)
                .isNotNull()
                .contains("must contain upper case letters")
        }

        @Test
        fun when_PasswordWithoutLowerCase() {
            val actualError = passwordValidator.validate("HELLO")
            expectThat(actualError)
                .isNotNull()
                .contains("must contain lower case letters")
        }

        @Test
        fun when_PasswordValidatorNoDigits() {
            val actualError = passwordValidator.validate("ABCDEF")
            expectThat(actualError)
                .isNotNull()
                .contains("must contain digits")
        }

        @Test
        fun when_PasswordValidatorHasBlanks() {
            val actualError = passwordValidator.validate("12 3 456")
            expectThat(actualError)
                .isNotNull()
                .contains("must not contain blanks")
        }

        @Test
        fun when_PasswordValidatorHasLessThan6Chars() {
            val actualError = passwordValidator.validate("1234A")
            expectThat(actualError)
                .isNotNull()
                .contains("must contain at least 6 chars")
        }
    }

    @Nested
    inner class PasswordValidatorValidatesTrue {
        @Test
        fun when_PasswordWithUpperCase() {
            val actualError = passwordValidator.validate("A12345")
            expectThat(actualError)
                .isNotNull()
                .doesNotContain("must contain upper case letters")
        }

        @Test
        fun when_PasswordWithLowerCase() {
            val actualError = passwordValidator.validate("hELLO")
            expectThat(actualError)
                .isNotNull()
                .doesNotContain("must contain lower case letters")
        }

        @Test
        fun when_PasswordValidatorHasDigits() {
            val actualError = passwordValidator.validate("1BCDEF")
            expectThat(actualError)
                .isNotNull()
                .doesNotContain("must contain digits")
        }

        @Test
        fun when_PasswordValidatorHasBlanks() {
            val actualError = passwordValidator.validate("123456")
            expectThat(actualError)
                .isNotNull()
                .doesNotContain("must not contain blanks")
        }

        @Test
        fun when_PasswordValidatorHasAtLeast6Chars() {
            val actualError = passwordValidator.validate("123456")
            expectThat(actualError)
                .isNotNull()
                .doesNotContain("must contain at least 6 chars")
        }
    }

    @Nested
    inner class PasswordValidatorFullyValidPassword {
        @Test
        fun when_PasswordMeetsAllRequirements_returnsNull() {
            val actualError = passwordValidator.validate("aA123456")
            expectThat(actualError).isNull()
        }
    }
}