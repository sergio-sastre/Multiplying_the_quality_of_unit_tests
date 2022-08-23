package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountEvent
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountModelState
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.CreateAccountUiState
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.StrongPasswordValidator

class CreateAccountViewModel : ViewModel(), CreateAccountEvent {

    private val passwordValidator = StrongPasswordValidator()

    private val modelState = MutableStateFlow(CreateAccountModelState())
    private val uiState = MutableStateFlow(CreateAccountUiState())

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
