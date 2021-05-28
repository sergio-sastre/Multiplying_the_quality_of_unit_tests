package sergio.sastre.multiplying.quality.of.unittests.multipleasserts

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import sergio.sastre.multiplying.quality.of.unittests.*


class PasswordUnitTests {

    @Nested
    inner class Initial {

        val passwordValidator =
            PasswordValidator(
                ContainsUpperCaseLetterValidator(),
                MinCharsValidator(
                    6
                ),
                ContainsDigitValidator()
            )

        @Nested
        inner class PasswordValidatorShowsCorrectError {
            @Test
            fun when_WithMissingRequirement() {
                assertThat(
                    passwordValidator.validate("123456")
                ).contains("no upper case letters")

                assertThat(
                    passwordValidator.validate("1234A")
                ).contains("contains less than 6 chars")

                assertThat(
                    passwordValidator.validate("ABCDEF")
                ).contains("no digits")
            }
        }

        @Nested
        inner class PasswordValidatorValidatesTrue {
            @Test
            fun when_WithMissingRequirement() {
                assertThat(
                    passwordValidator.validate("A2345")
                ).doesNotContain("no upper case letters")

                assertThat(
                    passwordValidator.validate("123456")
                ).doesNotContain("contains less than 6 chars")

                assertThat(
                    passwordValidator.validate("1BCDE")
                ).doesNotContain("no digits")
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

    @Nested
    inner class Advanced {

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
            fun when_WithMissingRequirement() {
                assertThat(
                    passwordValidator.validate("123456")
                ).contains("no upper case letters")

                assertThat(
                    passwordValidator.validate("1234A")
                ).contains("contains less than 6 chars")

                assertThat(
                    passwordValidator.validate("ABCDEF")
                ).contains("no digits")

                assertThat(
                    passwordValidator.validate("HELLO")
                ).contains("no lower case letters")

                assertThat(
                    passwordValidator.validate("12 3 456")
                ).contains("contains blanks")
            }
        }

        @Nested
        inner class PasswordValidatorValidatesTrue {
            @Test
            fun when_WithMissingRequirement() {
                assertThat(
                    passwordValidator.validate("A23456")
                ).doesNotContain("no upper case letters")

                assertThat(
                    passwordValidator.validate("123456")
                ).doesNotContain("contains less than 6 chars")

                assertThat(
                    passwordValidator.validate("1BCDEF")
                ).doesNotContain("no digits")

                assertThat(
                    passwordValidator.validate("hELLO")
                ).doesNotContain("no lower case letters")

                assertThat(
                    passwordValidator.validate("123456")
                ).doesNotContain("contains blanks")
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
}