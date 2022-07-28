package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui.*

class CreateAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateAccountScreen()
        }
    }
}

@Composable
fun CreateAccountScreen() {
    val viewModel: CreateAccountViewModel = viewModel()
    val viewState by remember(viewModel) { viewModel.uiState }.collectAsState()

    MaterialTheme {
        CreateAccountContent(
            header = {
                LogCompositions("JetpackCompose.app", "Header")
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Start,
                    text = stringResource(id = R.string.header_create_account),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            email = {
                LogCompositions("JetpackCompose.app", "Email")
                EmailText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    emailText = { Text(stringResource(R.string.email)) }
                )
            },
            password = {
                LogCompositions("JetpackCompose.app", "Password")
                PasswordInput(
                    containsErrorProvider = { viewState.errorVisible },
                    passwordProvider = { viewState.password },
                    passwordLeadingIconColorProvider = { viewState.passwordLeadingIconColor },
                    onPasswordChanged = viewModel::passwordChanged,
                    onDoneClicked = { viewModel.createAccountAction() }
                )
            },
            errorMessage = {
                LogCompositions("JetpackCompose.app", "Error message")
                PasswordRequirementComposedFailures(
                    modifier = Modifier.fillMaxWidth(),
                    isVisibleProvider = { viewState.errorVisible },
                    csvErrorProvider = { viewState.error },
                )
            },
            createAccountAction = {
                LogCompositions("JetpackCompose.app", "Create account button")
                CreateAccountButton(
                    modifier = Modifier.animateContentSize(),
                    onAuthenticate = { viewModel.createAccountAction() },
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_CreateAccountScreen() {
    MaterialTheme {
        CreateAccountScreen()
    }
}

class Ref(var value: Int)

// Note the inline function below which ensures that this function is essentially
// copied at the call site to ensure that its logging only recompositions from the
// original call site.
@Composable
inline fun LogCompositions(tag: String, msg: String) {
    if (BuildConfig.DEBUG) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        Log.d(tag, "Compositions: $msg ${ref.value}")
    }
}

