package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model

import androidx.annotation.ColorRes
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color

data class CreateAccountUiState(
    /*
    val passwordColor: Int,
    val passwordUnderlineColor: Int,
    val passwordStartIconColor: Int,
    val passwordEndIconColor: Int,

     */
    @ColorRes
    val passwordLeadingIconColor: Int = android.R.color.black,
    val password: String = "",
    val error: String? = null,
    val errorVisible: Boolean = false,
)