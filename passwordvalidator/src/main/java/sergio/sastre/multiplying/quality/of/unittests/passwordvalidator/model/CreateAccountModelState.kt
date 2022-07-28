package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model

import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.R

data class CreateAccountModelState(
    val error: String? = null,
    val password: String = "",
) {

    fun toUiState(): CreateAccountUiState =
        CreateAccountUiState(
            passwordLeadingIconColor = if (error == null) {
                android.R.color.black
            } else {
                R.color.design_default_color_error
            },
            password = password,
            error = error,
            errorVisible = error != null,
        )
}
