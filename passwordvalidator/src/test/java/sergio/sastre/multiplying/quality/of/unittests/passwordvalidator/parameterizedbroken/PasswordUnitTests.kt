package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.parameterizedbroken

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.BrokenStrongPasswordValidator
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull

/**
 * This class shows one of the problems of example-based tests:
 * - We are just testing the given examples.
 * That could lead to the following problem:
 * - The examples, although valid, are not sufficient to fully prove the requirement.
 */
class PasswordUnitTests {

    private val passwordValidator = BrokenStrongPasswordValidator()

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
    inner class PasswordValidatorWithFailingSample {

        @DisplayName("PasswordValidator for invalid passwords")
        @ParameterizedTest(name = "When password is \"{0}\", the error contains \"{1}\"")
        @CsvSource(
            "a23456, must contain upper case letters", //extra example that uncovers the error
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
}