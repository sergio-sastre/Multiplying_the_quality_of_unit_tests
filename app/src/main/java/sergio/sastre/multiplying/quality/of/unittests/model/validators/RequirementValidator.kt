package sergio.sastre.multiplying.quality.of.unittests.model.validators

interface RequirementValidator {
    val keywordOnError: String?

    fun isValid(password: String?): Boolean

    fun validate(password: String?): String? =
        when (isValid(password)) {
            true -> null
            false -> keywordOnError
        }
}