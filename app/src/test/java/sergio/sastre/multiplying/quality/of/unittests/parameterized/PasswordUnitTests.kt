package sergio.sastre.multiplying.quality.of.unittests.parameterized

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import sergio.sastre.multiplying.quality.of.unittests.pbt.doesNotContain
import sergio.sastre.multiplying.quality.of.unittests.model.validators.*
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull
import strikt.assertions.isNull

/**
 * This class shows the best solution for example-based tests, when applicable: parameterized tests
 * - We avoid tests failing fasts, like with multiple asserts per test
 * - We avoid code duplication, like with one assert per test
 */
class PasswordUnitTests {

    private val passwordValidator = PasswordValidator(
        ContainsUpperCaseLetterValidator(),
        MinCharsValidator(6),
        ContainsDigitValidator(),
        ContainsLowerCaseLetterValidator(),
        NoBlanksValidator()
    )

    @Nested
    inner class PasswordValidatorShowsCorrectError {
        @DisplayName("PasswordValidator for invalid passwords")
        @ParameterizedTest(name = "When password is \"{0}\", the error contains \"{1}\"")
        @CsvSource(
            "123456, must contain upper case letters",
            "ABCDEF, must contain digits",
            "HELLO, must contain lower case letters",
            "1234A, must contain at least 6 chars",
            "12 3 456, must not contain blanks"
        )
        fun testPasswordValidatorRight(password: String, expectedError: String) {
            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .contains(expectedError)
        }
    }

    @Nested
    inner class PasswordValidatorValidatesTrue {
        @DisplayName("PasswordValidator for partially invalid passwords")
        @ParameterizedTest(name = "When password is \"{0}\", the error does not contain \"{1}\"")
        @CsvSource(
            "A23456, must contain upper case letters",
            "1BCDEF, must contain digits",
            "hELLO, must contain lower case letters",
            "123456, must contain at least 6 chars",
            "123456, must not contain blanks"
        )
        fun testPasswordValidatorRight(password: String, expectedError: String) {
            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .doesNotContain(expectedError)
        }
    }

    @Nested
    inner class PasswordValidatorFullyValidPassword {
        @DisplayName("PasswordValidator for fully valid passwords")
        @ParameterizedTest(name = "When password is \"{0}\", the error is null")
        @CsvSource("aA123456")
        fun when_PasswordMeetsAllRequirements_returnsNull(password: String?) {
            val actualError = passwordValidator.validate(password)
            expectThat(actualError).isNull()
        }
    }
}