package sergio.sastre.multiplying.quality.of.unittests.broken

import sergio.sastre.multiplying.quality.of.unittests.RequirementValidator

class ContainsUpperCaseLetterValidator :
    RequirementValidator {
    override val keywordOnError: String? = "no upper case letters"

    override fun isValid(password: String?): Boolean =
        password?.contains("[a-z]".toRegex()) == true
}