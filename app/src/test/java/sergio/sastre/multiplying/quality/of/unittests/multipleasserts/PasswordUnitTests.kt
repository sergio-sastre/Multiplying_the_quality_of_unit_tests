package sergio.sastre.multiplying.quality.of.unittests.multipleasserts

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import sergio.sastre.multiplying.quality.of.unittests.*
import sergio.sastre.multiplying.quality.of.unittests.pbt.doesNotContain
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull
import strikt.assertions.isNull


class PasswordUnitTests {

    @Nested
    inner class Initial {

        val passwordValidator =
            PasswordValidator(
                ContainsUpperCaseLetterValidator(),
                MinCharsValidator(6),
                ContainsDigitValidator()
            )

        @Nested
        inner class PasswordValidatorShowsCorrectError {
            @Test
            fun when_WithMissingRequirement() {
                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .contains("no upper case letters")

                expectThat(passwordValidator.validate("1234A"))
                    .isNotNull()
                    .contains("contains less than 6 chars")

                expectThat(passwordValidator.validate("ABCDEF"))
                    .isNotNull()
                    .contains("no digits")
            }
        }

        @Nested
        inner class PasswordValidatorValidatesTrue {
            @Test
            fun when_WithMissingRequirement() {
                expectThat(passwordValidator.validate("A2345"))
                    .isNotNull()
                    .doesNotContain("no upper case letters")

                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .doesNotContain("contains less than 6 chars")

                expectThat(passwordValidator.validate("1BCDE"))
                    .isNotNull()
                    .doesNotContain("no digits")
            }
        }

        @Nested
        inner class PasswordValidatorFullyValidPassword {
            @Test
            fun when_PasswordMeetsAllRequirements_returnsNull() {
                expectThat(passwordValidator.validate("aA123456"))
                    .isNull()
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
                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .contains("no upper case letters")

                expectThat(passwordValidator.validate("1234A"))
                    .isNotNull()
                    .contains("contains less than 6 chars")

                expectThat(passwordValidator.validate("ABCDEF"))
                    .isNotNull()
                    .contains("no digits")

                expectThat(passwordValidator.validate("HELLO"))
                    .isNotNull()
                    .contains("no lower case letters")

                expectThat(passwordValidator.validate("12 3 456"))
                    .isNotNull()
                    .contains("contains blanks")
            }
        }

        @Nested
        inner class PasswordValidatorValidatesTrue {
            @Test
            fun when_WithMissingRequirement() {
                expectThat(passwordValidator.validate("A23456"))
                    .isNotNull()
                    .doesNotContain("no upper case letters")

                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .doesNotContain("contains less than 6 chars")

                expectThat(passwordValidator.validate("1BCDEF"))
                    .isNotNull()
                    .doesNotContain("no digits")

                expectThat(passwordValidator.validate("hELLO"))
                    .isNotNull()
                    .doesNotContain("no lower case letters")

                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .doesNotContain("contains blanks")
            }
        }

        @Nested
        inner class PasswordValidatorFullyValidPassword {
            @Test
            fun when_PasswordMeetsAllRequirements_returnsNull() {
                expectThat(passwordValidator.validate("aA123456"))
                    .isNull()
            }
        }
    }
}