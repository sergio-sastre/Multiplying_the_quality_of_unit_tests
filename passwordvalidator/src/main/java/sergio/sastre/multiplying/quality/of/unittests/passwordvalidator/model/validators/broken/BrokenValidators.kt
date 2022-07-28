package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.broken

import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.RequirementValidator


class ContainsUpperCaseLetterValidator :
    RequirementValidator {
    override val keywordOnError: String = "must contain upper case letters"

    // BROKEN! validates lower case instead of upper case
    override fun isValid(password: String?): Boolean =
        password?.contains("[a-z]".toRegex()) == true
}