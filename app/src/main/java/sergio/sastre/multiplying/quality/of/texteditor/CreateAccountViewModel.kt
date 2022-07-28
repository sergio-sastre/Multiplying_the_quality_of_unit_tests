package sergio.sastre.multiplying.quality.of.texteditor

import sergio.sastre.multiplying.quality.of.texteditor.model.CreateAccountEvent
import sergio.sastre.multiplying.quality.of.texteditor.model.CreateAccountModelState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import sergio.sastre.multiplying.quality.of.texteditor.model.validators.StrongPasswordValidator
import sergio.sastre.multiplying.quality.of.unittests.model.validators.*

class CreateAccountViewModel : ViewModel() {

    private val passwordValidator = StrongPasswordValidator()

    val uiState = MutableStateFlow(CreateAccountModelState())

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
