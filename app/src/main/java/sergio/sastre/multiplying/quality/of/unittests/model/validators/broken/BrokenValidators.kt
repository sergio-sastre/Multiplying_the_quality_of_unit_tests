package sergio.sastre.multiplying.quality.of.unittests.model.validators.broken

import sergio.sastre.multiplying.quality.of.unittests.model.validators.RequirementValidator

class ContainsUpperCaseLetterValidator :
    RequirementValidator {
    override val keywordOnError: String = "must contain upper case letters"

    override fun isValid(password: String?): Boolean =
        password?.contains("[a-z]".toRegex()) == true
}