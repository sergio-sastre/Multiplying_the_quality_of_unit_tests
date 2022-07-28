package sergio.sastre.multiplying.quality.of.texteditor.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CreateAccountForm(
    modifier: Modifier = Modifier,
    email: String,
    password: String?,
    csvError: String?,
    enableAuthentication: Boolean,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        CreateAccountHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val passwordFocusRequester = FocusRequester()
            EmailText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                email = email,
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
                isError = csvError != null,
                password = password,
                onPasswordChanged = onPasswordChanged,
                onDoneClicked = onAuthenticate
            )
            Spacer(modifier = Modifier.height(12.dp))
            AnimatedVisibility(visible = csvError != null) {
                PasswordRequirementComposedFailures(
                    modifier = Modifier.fillMaxWidth(),
                    csvError = csvError,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                CreateAccountButton(
                    modifier = Modifier.animateContentSize(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = onAuthenticate,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_CreateAccountForm() {
    MaterialTheme {
        CreateAccountForm(
            modifier = Modifier.fillMaxWidth(),
            email = "learning.pbt@myemail.com",
            password = "12345678",
            csvError = null,
            enableAuthentication = true,
            onPasswordChanged = { },
            onAuthenticate = { },
        )
    }
}
