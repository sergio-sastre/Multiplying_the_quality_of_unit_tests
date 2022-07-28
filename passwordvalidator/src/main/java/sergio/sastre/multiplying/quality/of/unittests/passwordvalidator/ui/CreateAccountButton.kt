package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.R

@Composable
fun CreateAccountButton(
    modifier: Modifier = Modifier,
    onAuthenticate: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onAuthenticate,
        shape = RoundedCornerShape(percent = 50),
    ) {
        Text(text = stringResource(id = R.string.button_create_account))
    }
}

@Preview
@Composable
fun Preview_CreateAccountButton() {
    MaterialTheme {
        CreateAccountButton(
            modifier = Modifier.fillMaxWidth(),
        ){}
    }
}
