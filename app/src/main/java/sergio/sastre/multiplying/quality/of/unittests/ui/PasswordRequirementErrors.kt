package sergio.sastre.multiplying.quality.of.unittests.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import sergio.sastre.multiplying.quality.of.unittests.model.validators.ContainsUpperCaseLetterValidator

@Composable
fun PasswordRequirementErrors(
    modifier: Modifier = Modifier,
    csvError: String?,
) {
    Column(modifier = modifier.animateContentSize()) {
        csvError?.splitToSequence(',', ignoreCase = true, limit = 0)?.forEach {
            PasswordRequirementError(message = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PasswordRequirementErrors() {
    MaterialTheme {
        PasswordRequirementErrors(
            modifier = Modifier.fillMaxWidth(),
            csvError = ContainsUpperCaseLetterValidator().keywordOnError.orEmpty()
        )
    }
}
