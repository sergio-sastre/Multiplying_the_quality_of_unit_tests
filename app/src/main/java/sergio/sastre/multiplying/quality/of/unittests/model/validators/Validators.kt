package sergio.sastre.multiplying.quality.of.unittests.model.validators

class ContainsUpperCaseLetterValidator :
    RequirementValidator {
    override val keywordOnError: String = "must contain upper case letters"

    override fun isValid(password: String?): Boolean =
        password?.contains("[A-Z]".toRegex()) == true
}

class ContainsLowerCaseLetterValidator :
    RequirementValidator {
    override val keywordOnError: String = "must contain lower case letters"

    override fun isValid(password: String?): Boolean =
        password?.contains("[a-z]".toRegex()) == true
}

class ContainsDigitValidator :
    RequirementValidator {
    override val keywordOnError: String = "must contain digits"

    override fun isValid(password: String?): Boolean =
        password?.contains("[0-9]".toRegex()) == true
}

class MinCharsValidator(private val minCharsCount: Int) :
    RequirementValidator {
    override val keywordOnError: String = "must contain at least $minCharsCount chars"

    override fun isValid(password: String?): Boolean =
        password?.length ?: 0 >= minCharsCount
}

class NoBlanksValidator :
    RequirementValidator {
    override val keywordOnError: String = "must not contain blanks"

    override fun isValid(password: String?): Boolean =
        password?.contains("\\s".toRegex()) == false
}