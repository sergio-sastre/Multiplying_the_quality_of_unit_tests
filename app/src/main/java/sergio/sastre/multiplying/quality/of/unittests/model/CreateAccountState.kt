package sergio.sastre.multiplying.quality.of.unittests.model

data class CreateAccountState(
    val email: String = "learning.pbt@myemail.com",
    val password: String? = null,
    val error: String? = null,
)
