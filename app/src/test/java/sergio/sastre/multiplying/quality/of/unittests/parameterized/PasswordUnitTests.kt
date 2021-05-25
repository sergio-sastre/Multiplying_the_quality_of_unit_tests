package sergio.sastre.multiplying.quality.of.unittests.parameterized

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import sergio.sastre.multiplying.quality.of.unittests.PasswordValidator
import sergio.sastre.multiplying.quality.of.unittests.initial.*

class PasswordUnitTests {

    @DisplayName("PasswordValidator for invalid passwords")
    @ParameterizedTest(name = "When password is \"{0}\", the error contains \"{1}\"")
    @CsvSource(
        "123456, no upper case letters",
        "ABCDEF, no digits",
        "HELLO, no lower case letters",
        "1234A, contains less than 6 chars",
        "12 3 456, contains blanks"
    )
    fun testPasswordValidatorRight(password: String?, expectedError: String?) {
        val passwordValidator = PasswordValidator(
            ContainsUpperCaseLetterValidator(),
            MinCharsValidator(6),
            ContainsDigitValidator(),
            ContainsLowerCaseLetterValidator(),
            NoBlanksValidator()
        )

        val actualError = passwordValidator.validate(password)

        assertThat(actualError).contains(expectedError)
    }
}