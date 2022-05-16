package sergio.sastre.multiplying.quality.of.unittests.parameterizedbroken

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import sergio.sastre.multiplying.quality.of.unittests.*
import sergio.sastre.multiplying.quality.of.unittests.broken.ContainsUpperCaseLetterValidator
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull

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
            "123456, no upper case letters",
            "ABCDEF, no digits",
            "HELLO, no lower case letters",
            "1234A, contains less than 6 chars",
            "12 3 456, contains blanks"
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
            "a23456, no upper case letters", //extra sample that uncovers the error
            "123456, no upper case letters",
            "ABCDEF, no digits",
            "HELLO, no lower case letters",
            "1234A, contains less than 6 chars",
            "12 3 456, contains blanks"
        )
        fun testPasswordValidatorRight(password: String, expectedError: String) {
            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .contains(expectedError)
        }
    }
}