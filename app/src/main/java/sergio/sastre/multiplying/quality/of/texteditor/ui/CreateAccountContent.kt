package sergio.sastre.multiplying.quality.of.texteditor.ui

import sergio.sastre.multiplying.quality.of.texteditor.model.CreateAccountEvent
import sergio.sastre.multiplying.quality.of.texteditor.model.CreateAccountModelState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateAccountContent(
    state: CreateAccountModelState,
    handleEvent: (event: CreateAccountEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CreateAccountForm(
            modifier = Modifier.fillMaxSize(),
            email = state.email,
            password = state.password,
            csvError = state.error,
            enableAuthentication = true,
            onPasswordChanged = {
                handleEvent(CreateAccountEvent.PasswordChanged(it))
            },
            onAuthenticate = {
                handleEvent(CreateAccountEvent.CreateAccount)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_CreateAccountContent() {
    MaterialTheme {
        CreateAccountContent(state = CreateAccountModelState(), handleEvent = { })
    }
}
