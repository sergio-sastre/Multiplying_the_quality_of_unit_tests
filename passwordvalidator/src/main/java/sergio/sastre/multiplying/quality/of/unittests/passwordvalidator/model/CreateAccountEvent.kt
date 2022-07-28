package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model

interface CreateAccountEvent {

    fun passwordChanged(password: String)

    fun createAccountAction()
}

sealed class CreateAccountEvent2 {

    class PasswordChanged(val password: String): CreateAccountEvent2()

    object CreateAccountAction: CreateAccountEvent2()
}
