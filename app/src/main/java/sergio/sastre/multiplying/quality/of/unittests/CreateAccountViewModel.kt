package sergio.sastre.multiplying.quality.of.unittests

import sergio.sastre.multiplying.quality.of.unittests.model.CreateAccountEvent
import sergio.sastre.multiplying.quality.of.unittests.model.CreateAccountState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import sergio.sastre.multiplying.quality.of.unittests.model.validators.*

class CreateAccountViewModel : ViewModel() {

    private val passwordValidator = PasswordValidator(
        ContainsUpperCaseLetterValidator(),
        MinCharsValidator(6),
        ContainsDigitValidator(),
        ContainsLowerCaseLetterValidator(),
        NoBlanksValidator(),
    )

    val uiState = MutableStateFlow(CreateAccountState())

    fun handleEvent(createAccountEvent: CreateAccountEvent) {
        when (createAccountEvent) {
            is CreateAccountEvent.PasswordChanged -> {
                updatePassword(createAccountEvent.password)
            }
            is CreateAccountEvent.CreateAccount -> onCreateAccountButtonClicked()
        }
    }

    private fun onCreateAccountButtonClicked() {
        uiState.value = uiState.value.copy(
            error = passwordValidator.validate(uiState.value.password),
        )
    }

    private fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(
            password = password,
        )
    }
}
