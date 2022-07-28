package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.LogCompositions
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.R

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordProvider: () -> String,
    containsErrorProvider: () -> Boolean,
    passwordLeadingIconColorProvider: () -> Int,
    onPasswordChanged: (password: String) -> Unit,
    onDoneClicked: () -> Unit
) {
    val passwordFocusRequester = FocusRequester()

    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(passwordFocusRequester),
        value = passwordProvider(),
        onValueChange = onPasswordChanged,
        isError = containsErrorProvider(),
        singleLine = true,
        label = {
            Text(text = stringResource(id = R.string.hint_password))
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = colorResource(id = passwordLeadingIconColorProvider())
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable(
                        onClickLabel = if (isPasswordHidden) {
                            stringResource(id = R.string.action_show_password)
                        } else
                            stringResource(id = R.string.action_hide_password)
                    ) {
                        isPasswordHidden = !isPasswordHidden
                    },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Visibility
                } else Icons.Default.VisibilityOff,
                contentDescription = null
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneClicked()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation =
        if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_PasswordInput() {
    MaterialTheme {
        PasswordInput(
            modifier = Modifier.fillMaxWidth(),
            passwordLeadingIconColorProvider = { android.R.color.black },
            passwordProvider = { "12345678" },
            containsErrorProvider = { false },
            onPasswordChanged = { },
            onDoneClicked = { }
        )
    }
}
