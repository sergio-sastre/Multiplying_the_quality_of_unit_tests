package sergio.sastre.multiplying.quality.of.unittests

class PasswordValidator(private vararg val validators: RequirementValidator) :
    RequirementValidator {

    // Won't be used in validate()
    override val keywordOnError: String? = null

    override fun isValid(password: String?): Boolean =
        validators.all { it.isValid(password) }

    override fun validate(password: String?): String? =
        validators
            .mapNotNull { it.validate(password) }
            .joinToString(separator = ", ")
            .ifBlank { null }
}