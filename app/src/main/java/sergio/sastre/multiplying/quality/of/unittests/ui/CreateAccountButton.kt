package sergio.sastre.multiplying.quality.of.unittests.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import sergio.sastre.multiplying.quality.of.unittests.R

@Composable
fun CreateAccountButton(
    modifier: Modifier = Modifier,
    enableAuthentication: Boolean,
    onAuthenticate: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enableAuthentication,
        onClick = onAuthenticate,
        shape = RoundedCornerShape(percent = 50),
    ) {
        Text(
            text = stringResource(id = R.string.button_create_account)
        )
    }
}

@Preview
@Composable
fun Preview_CreateAccountButton() {
    MaterialTheme {
        CreateAccountButton(
            modifier = Modifier.fillMaxWidth(),
            enableAuthentication = true
        ){}
    }
}
