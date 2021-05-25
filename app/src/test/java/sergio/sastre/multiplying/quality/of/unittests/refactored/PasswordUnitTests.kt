package sergio.sastre.multiplying.quality.of.unittests.refactored

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import sergio.sastre.multiplying.quality.of.unittests.PasswordValidator


/**
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PasswordUnitTests {
    @Test
    fun passwordValidatorTest() {
        val minCharsCount = 6
        val passwordValidator =
            PasswordValidator(
                ContainsUpperCaseLetterValidator(),
                MinCharsValidator(minCharsCount),
                ContainsDigitValidator()
            )

        assertThat(
            passwordValidator.validate("123456")
        ).contains("no upper case letters")

        assertThat(
            passwordValidator.validate("1234A")
        ).contains("contains less than $minCharsCount chars")

        assertThat(
            passwordValidator.validate("ABCDEF")
        ).contains("no digits")
    }

    @Test
    fun passwordValidatorSoftAssertionTest() {
        val minCharsCount = 6
        val passwordValidator = PasswordValidator(
            ContainsUpperCaseLetterValidator(),
            MinCharsValidator(minCharsCount),
            ContainsDigitValidator()
        )

        assertAll(
            {
                assertThat(
                    passwordValidator.validate("123456")
                ).contains("no upper case letters")
            },

            {
                assertThat(
                    passwordValidator.validate("1234A")
                ).contains("contains less than $minCharsCount chars")
            },

            {
                assertThat(
                    passwordValidator.validate("ABCDEF")
                ).contains("no digits")
            }
        )
    }

    @Test
    fun advancedPasswordValidatorSoftAssertionTest() {
        val minCharsCount = 6
        val passwordValidator =
            PasswordValidator(
                ContainsUpperCaseLetterValidator(),
                MinCharsValidator(minCharsCount),
                ContainsDigitValidator(),
                ContainsLowerCaseLetterValidator(),
                NoBlanksValidator()
            )

        assertAll(
            {
                assertThat(
                    passwordValidator.validate("123456")
                ).contains("no upper case letters")
            },

            {
                assertThat(
                    passwordValidator.validate("1234A")
                ).contains("contains less than $minCharsCount chars")
            },

            {
                assertThat(
                    passwordValidator.validate("ABCDEF")
                ).contains("no digits")
            },

            {
                assertThat(
                    passwordValidator.validate("HELLO")
                ).contains("no lower case letters")
            },

            {
                assertThat(
                    passwordValidator.validate("12 3 456")
                ).contains("contains blanks")
            }
        )
    }
}