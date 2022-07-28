package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateAccountContent(
    header: @Composable ColumnScope.() -> Unit,
    email: @Composable ColumnScope.() -> Unit,
    password: @Composable ColumnScope.() -> Unit,
    errorMessage: @Composable ColumnScope.() -> Unit,
    createAccountAction: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            header()
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                email()
                Spacer(modifier = Modifier.height(16.dp))
                password()
                Spacer(modifier = Modifier.height(12.dp))
                errorMessage()
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    createAccountAction()
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
