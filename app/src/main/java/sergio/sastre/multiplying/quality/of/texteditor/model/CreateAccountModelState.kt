package sergio.sastre.multiplying.quality.of.texteditor.model

data class CreateAccountModelState(
    val email: String = "learning.pbt@myemail.com",
    val error: String? = null,
    val password: String? = null,
){
}
