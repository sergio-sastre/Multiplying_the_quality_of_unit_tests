package sergio.sastre.multiplying.quality.of.unittests.initial

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Test
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
}