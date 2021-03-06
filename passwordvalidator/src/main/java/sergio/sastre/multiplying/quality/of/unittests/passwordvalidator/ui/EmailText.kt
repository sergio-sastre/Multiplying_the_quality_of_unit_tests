package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmailText(
    modifier: Modifier = Modifier,
    emailText: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        emailText()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_EmailText() {
    MaterialTheme {
        EmailText(
            modifier = Modifier.fillMaxWidth(),
            emailText = {
                Text("learning.pbt@myemail.com")
            }
        )
    }
}
