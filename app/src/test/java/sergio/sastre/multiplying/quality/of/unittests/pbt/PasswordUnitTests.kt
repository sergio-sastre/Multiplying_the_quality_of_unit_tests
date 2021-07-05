package sergio.sastre.multiplying.quality.of.unittests.pbt

import com.google.common.truth.Truth.assertThat
import net.jqwik.api.*
import net.jqwik.api.arbitraries.ListArbitrary
import net.jqwik.engine.properties.arbitraries.DefaultListArbitrary
import sergio.sastre.multiplying.quality.of.unittests.*
import sergio.sastre.multiplying.quality.of.unittests.broken.ContainsUpperCaseLetterValidator

@Group
class PasswordUnitTests {

    private val passwordValidator =
        PasswordValidator(
            ContainsUpperCaseLetterValidator(),
            MinCharsValidator(6),
            ContainsDigitValidator(),
            ContainsLowerCaseLetterValidator(),
            NoBlanksValidator()
        )

    @Group
    inner class UpperCase {

        @Provide
        fun noUpperCase(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.matches("[^A-Z]".toRegex()) }

        @Provide
        fun withUpperCase(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.contains("[A-Z]".toRegex()) }

        @Label(
            "If password without upper case chars," +
                    "the error message contains 'no upper case letters'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("noUpperCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            assertThat(actualError).contains("no upper case letters")
        }

        @Label(
            "If password with upper case chars, " +
                    "then the error message does not contain 'no upper case letters'"
        )
        @Property(seed = "-7293214509268013126")
        fun testPasswordValidatorWrong(@ForAll("withUpperCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            actualError.assertNullOrDoesNotContain("no upper case letters")
        }
    }

    @Group
    inner class LowerCase {

        @Provide
        fun noLowerCase(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.matches("[^a-z]".toRegex()) }

        @Provide
        fun withLowerCase(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.contains("[a-z]".toRegex()) }

        @Label(
            "WIf password without lower case chars, " +
                    "then the error message contains 'no lower case letters'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("noLowerCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            assertThat(actualError).contains("no lower case letters")
        }

        @Label(
            "If password with lower case chars, " +
                    "then the error message does not contain 'no lower case letters'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("withLowerCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            actualError.assertNullOrDoesNotContain("no lower case letters")
        }
    }

    @Group
    inner class Digits {

        @Provide
        fun noDigits(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.matches("[^0-9]".toRegex()) }

        @Provide
        fun withDigits(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.contains("[0-9]".toRegex()) }

        @Label(
            "If password without digits, " +
                    "then the error message contains 'no digits'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("noDigits") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            assertThat(actualError).contains("no digits")
        }

        @Label(
            "If password with digits, " +
                    "then the error message does not contain 'no digits'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("withDigits") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            actualError.assertNullOrDoesNotContain("no digits")
        }
    }

    @Group
    inner class MinChars {

        @Provide
        fun max5Chars(): Arbitrary<String> =
            Arbitraries.strings().ascii().ofMaxLength(5)

        @Provide
        fun min6Chars(): Arbitrary<String> =
            Arbitraries.strings().ascii().ofMinLength(6)


        @Label(
            "If password with less than 6 chars, " +
                    "then the error message does not contain 'contains less than 6 chars'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("max5Chars") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            assertThat(actualError).contains("contains less than 6 chars")
        }

        @Label(
            "If password with at least 6 chars, " +
                    "then the error message does not contain 'contains less than 6 chars'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("min6Chars") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            actualError.assertNullOrDoesNotContain("contains less than 6 chars")
        }
    }

    @Group
    inner class NoBlanks {

        @Provide
        fun hasBlanks(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.contains("\\s".toRegex()) }

        @Provide
        fun noBlanks(): Arbitrary<String> =
            Arbitraries.strings().ascii().filter { it.matches("\\S".toRegex()) }

        @Label(
            "If password with blanks, " +
                    "then the error message contains 'contains blanks'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("hasBlanks") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            assertThat(actualError).contains("contains blanks")
        }

        @Label(
            "If password without blanks, " +
                    "then the error message does not contain 'contains blanks'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("noBlanks") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            actualError.assertNullOrDoesNotContain("contains blanks")
        }
    }

    @Group
    inner class PasswordValidatorTest {

        private fun String?.amountOf(char: Char): Int = this?.count { it == char } ?: 0

        inner class FailingValidator : RequirementValidator {
            override val keywordOnError: String? = "I always fail"
            override fun isValid(password: String?): Boolean = false
        }

        @Provide
        fun failingValidators(): ListArbitrary<FailingValidator> =
            DefaultListArbitrary<FailingValidator>(
                Arbitraries.integers().map { FailingValidator() }
            )

        @Label(
            "When PasswordValidator fails " +
                    "then the error message contains at least as many commas as single validators - 1,"
        )
        @Property
        fun testPasswordValidatorWrong(
            @ForAll("failingValidators") failingValidators: List<FailingValidator>,
            @ForAll failingPassword: String
        ) {
            val passwordValidator = PasswordValidator(*failingValidators.toTypedArray())
            val errorMessage = passwordValidator.validate(failingPassword)

            assertThat(errorMessage.amountOf(','))
                .isAtLeast((failingValidators.size - 1))
        }
    }
}
