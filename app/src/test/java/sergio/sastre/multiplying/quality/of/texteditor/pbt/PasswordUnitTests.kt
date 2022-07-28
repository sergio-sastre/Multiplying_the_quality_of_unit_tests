package sergio.sastre.multiplying.quality.of.texteditor.pbt

import net.jqwik.api.*
import net.jqwik.api.arbitraries.ListArbitrary
import sergio.sastre.multiplying.quality.of.texteditor.model.validators.PasswordValidator
import sergio.sastre.multiplying.quality.of.texteditor.model.validators.RequirementValidator
import sergio.sastre.multiplying.quality.of.texteditor.BrokenStrongPasswordValidator
import strikt.api.expectThat
import strikt.assertions.*

/**
 * This class shows how Property based tests solve the problems of example based tests:
 * - We do not pick examples, they (1000 per test) are semi-randomly generated for us
 *
 * In doing so, one of the we find out that the problem with the upperCase validator.
 */
@Group
class PasswordUnitTests {

    private val passwordValidator = BrokenStrongPasswordValidator()

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
                    "the error message contains 'must contain upper case letters'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("noUpperCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .contains("must contain upper case letters")
        }

        @Label(
            "If password with upper case chars, " +
                    "then the error message does not contain 'must contain upper case letters'"
        )
        @Property(seed = "-7293214509268013126")
        fun testPasswordValidatorWrong(@ForAll("withUpperCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError).isNullOrDoesNotContain("must contain upper case letters")
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
            "If password without lower case chars, " +
                    "then the error message contains 'must contain lower case letters'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("noLowerCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)

            expectThat(actualError)
                .isNotNull()
                .contains("must contain lower case letters")
        }

        @Label(
            "If password with lower case chars, " +
                    "then the error message does not contain 'must contain lower case letters'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("withLowerCase") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError).isNullOrDoesNotContain("must contain lower case letters")
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
                    "then the error message contains 'must contain digits'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("noDigits") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .contains("must contain digits")
        }

        @Label(
            "If password with digits, " +
                    "then the error message does not contain 'must contain digits'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("withDigits") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError).isNullOrDoesNotContain("must contain digits")
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
                    "then the error message does not contain 'must contain at least 6 chars'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("max5Chars") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .contains("must contain at least 6 chars")
        }

        @Label(
            "If password with at least 6 chars, " +
                    "then the error message does not contain 'must contain at least 6 chars'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("min6Chars") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError).isNullOrDoesNotContain("must contain at least 6 chars")
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
                    "then the error message contains 'must not contain blanks'"
        )
        @Property
        fun testPasswordValidatorRight(@ForAll("hasBlanks") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError)
                .isNotNull()
                .contains("must not contain blanks")
        }

        @Label(
            "If password without blanks, " +
                    "then the error message does not contain 'must not contain blanks'"
        )
        @Property
        fun testPasswordValidatorWrong(@ForAll("noBlanks") password: String?) {
            collectPasswordStats(password)

            val actualError = passwordValidator.validate(password)
            expectThat(actualError).isNullOrDoesNotContain("contains blanks")
        }
    }

    @Group
    inner class PasswordValidatorTest {

        private fun String?.amountOf(char: Char): Int = this?.count { it == char } ?: 0

        inner class FailingValidator : RequirementValidator {
            override val keywordOnError: String = "I always fail"
            override fun isValid(password: String?): Boolean = false
        }

        @Provide
        fun failingValidators(): ListArbitrary<FailingValidator> =
            Arbitraries.integers().map { FailingValidator() }.list()

        @Label(
            "When PasswordValidator fails then" +
                    "the error message contains at least as many commas as single validators - 1,"
        )
        @Property
        fun testPasswordValidatorWrong(
            @ForAll("failingValidators") failingValidators: List<FailingValidator>,
            @ForAll failingPassword: String
        ) {
            val passwordValidator = PasswordValidator(*failingValidators.toTypedArray())
            val errorMessage = passwordValidator.validate(failingPassword)

            expectThat(errorMessage.amountOf(','))
                .isGreaterThanOrEqualTo((failingValidators.size - 1))
        }
    }
}
