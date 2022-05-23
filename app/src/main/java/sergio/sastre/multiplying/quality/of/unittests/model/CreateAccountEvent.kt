package sergio.sastre.multiplying.quality.of.unittests.model

sealed class CreateAccountEvent {

    class PasswordChanged(val password: String): CreateAccountEvent()

    object CreateAccount: CreateAccountEvent()
}
