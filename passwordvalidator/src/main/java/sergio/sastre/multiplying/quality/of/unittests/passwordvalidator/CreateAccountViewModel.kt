package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountEvent
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountEvent2
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountModelState
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountUiState
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.StrongPasswordValidator

class CreateAccountViewModel : ViewModel(), CreateAccountEvent {

    private val passwordValidator = StrongPasswordValidator()

    val modelState = MutableStateFlow(CreateAccountModelState())
    val uiState = MutableStateFlow(CreateAccountUiState())

    fun handleEvent(event: CreateAccountEvent2) {
        when (event) {
            CreateAccountEvent2.CreateAccountAction -> createAccountAction()
            is CreateAccountEvent2.PasswordChanged -> passwordChanged(event.password)
        }
    }

    override fun passwordChanged(password: String) {
        modelState.value = modelState.value.copy(password = password)
        uiState.value = modelState.value.toUiState()
    }

    override fun createAccountAction() {
        val error = passwordValidator.validate(uiState.value.password)
        modelState.value = modelState.value.copy(error = error)
        uiState.value = modelState.value.toUiState()
    }
}
