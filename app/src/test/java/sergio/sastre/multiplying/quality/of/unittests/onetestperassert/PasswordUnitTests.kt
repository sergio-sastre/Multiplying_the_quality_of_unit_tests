package sergio.sastre.multiplying.quality.of.unittests.onetestperassert

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import sergio.sastre.multiplying.quality.of.unittests.PasswordValidator
import sergio.sastre.multiplying.quality.of.unittests.ContainsDigitValidator
import sergio.sastre.multiplying.quality.of.unittests.ContainsLowerCaseLetterValidator
import sergio.sastre.multiplying.quality.of.unittests.ContainsUpperCaseLetterValidator
import sergio.sastre.multiplying.quality.of.unittests.MinCharsValidator
import sergio.sastre.multiplying.quality.of.unittests.NoBlanksValidator


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

        @Test
        fun when_PasswordWithoutUpperCase() {
            assertThat(
                passwordValidator.validate("123456")
            ).contains("no upper case letters")
        }

        @Test
        fun when_PasswordWithoutLowerCase() {
            assertThat(
                passwordValidator.validate("HELLO")
            ).contains("no lower case letters")
        }

        @Test
        fun when_PasswordValidatorNoDigits() {
            assertThat(
                passwordValidator.validate("ABCDEF")
            ).contains("no digits")
        }

        @Test
        fun when_PasswordValidatorHasBlanks() {
            assertThat(
                passwordValidator.validate("12 3 456")
            ).contains("contains blanks")
        }

        @Test
        fun when_PasswordValidatorHasLessThan6Chars() {
            assertThat(
                passwordValidator.validate("1234A")
            ).contains("contains less than 6 chars")

        }
    }

    @Nested
    inner class PasswordValidatorValidatesTrue {
        @Test
        fun when_PasswordWithUpperCase() {
            assertThat(
                passwordValidator.validate("A12345")
            ).doesNotContain("no upper case letters")
        }

        @Test
        fun when_PasswordWithLowerCase() {
            assertThat(
                passwordValidator.validate("hELLO")
            ).doesNotContain("no lower case letters")
        }

        @Test
        fun when_PasswordValidatorHasDigits() {
            assertThat(
                passwordValidator.validate("1BCDEF")
            ).doesNotContain("no digits")
        }

        @Test
        fun when_PasswordValidatorHasBlanks() {
            assertThat(
                passwordValidator.validate("123456")
            ).doesNotContain("contains blanks")
        }

        @Test
        fun when_PasswordValidatorHasAtLeast6Chars() {
            assertThat(
                passwordValidator.validate("123456")
            ).doesNotContain("contains less than 6 chars")
        }
    }

    @Nested
    inner class PasswordValidatorFullyValidPassword {
        @Test
        fun when_PasswordMeetsAllRequirements_returnsNull() {
            assertThat(
                passwordValidator.validate("aA123456")
            ).isNull()
        }
    }
}