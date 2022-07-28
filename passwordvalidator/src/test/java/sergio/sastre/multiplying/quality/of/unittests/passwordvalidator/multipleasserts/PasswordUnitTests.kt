package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.multipleasserts

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.*
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.pbt.doesNotContain
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isNotNull
import strikt.assertions.isNull

/**
 * This class shows the problem of having several asserts.
 * The more password requirements we get, the more of asserts per test.
 * It also means, the test will fail fast:
 * - If several asserts fail, we'll only know about the first one.
 *
 * This is very inconvenient if having many assertions in one test
 */
class PasswordUnitTests {

    @Nested
    inner class InitialPasswordValidator {

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
                    .contains("must contain upper case letters")

                expectThat(passwordValidator.validate("1234A"))
                    .isNotNull()
                    .contains("must contain at least 6 chars")

                expectThat(passwordValidator.validate("ABCDEF"))
                    .isNotNull()
                    .contains("must contain digits")
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
    inner class FullStrongPasswordValidator {

        private val passwordValidator = StrongPasswordValidator()

        @Nested
        inner class PasswordValidatorShowsCorrectError {
            @Test
            fun when_WithMissingRequirement() {
                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .contains("must contain upper case letters")

                expectThat(passwordValidator.validate("1234A"))
                    .isNotNull()
                    .contains("must contain at least 6 chars")

                expectThat(passwordValidator.validate("ABCDEF"))
                    .isNotNull()
                    .contains("must contain digits")

                expectThat(passwordValidator.validate("HELLO"))
                    .isNotNull()
                    .contains("must contain lower case letters")

                expectThat(passwordValidator.validate("12 3 456"))
                    .isNotNull()
                    .contains("must not contain blanks")
            }
        }

        @Nested
        inner class PasswordValidatorValidatesTrue {
            @Test
            fun when_WithMissingRequirement() {
                expectThat(passwordValidator.validate("A23456"))
                    .isNotNull()
                    .doesNotContain("must contain upper case letters")

                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .doesNotContain("must contain at least 6 chars")

                expectThat(passwordValidator.validate("1BCDEF"))
                    .isNotNull()
                    .doesNotContain("must contain digits")

                expectThat(passwordValidator.validate("hELLO"))
                    .isNotNull()
                    .doesNotContain("must contain lower case letters")

                expectThat(passwordValidator.validate("123456"))
                    .isNotNull()
                    .doesNotContain("must not contain blanks")
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