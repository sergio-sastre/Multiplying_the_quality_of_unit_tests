package sergio.sastre.multiplying.quality.of.texteditor.model

sealed class CreateAccountEvent {

    class PasswordChanged(val password: String): CreateAccountEvent()

    object CreateAccount: CreateAccountEvent()
}
