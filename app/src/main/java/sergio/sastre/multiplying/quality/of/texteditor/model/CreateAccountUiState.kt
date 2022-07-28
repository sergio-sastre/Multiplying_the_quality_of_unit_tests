package sergio.sastre.multiplying.quality.of.texteditor.model

data class CreateAccountUiState(
    val passwordColor: Int,
    val passwordUnderlineColor: Int,
    val passwordStartIconColor: Int,
    val passwordEndIconColor: Int,
    val password: String?,

    val error: String?,
    val email: String,
) {
}