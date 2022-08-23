package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model

interface CreateAccountEvent {

    fun passwordChanged(password: String)

    fun createAccountAction()
}

