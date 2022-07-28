package sergio.sastre.multiplying.quality.of.texteditor.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import sergio.sastre.multiplying.quality.of.texteditor.model.validators.ContainsUpperCaseLetterValidator

@Composable
fun PasswordRequirementComposedFailures(
    modifier: Modifier = Modifier,
    csvError: String?,
) {
    Column(modifier = modifier.animateContentSize()) {
        csvError?.splitToSequence(',', ignoreCase = true, limit = 0)?.forEach {
            PasswordRequirementSingleFailure(message = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PasswordRequirementComposedFailures() {
    MaterialTheme {
        PasswordRequirementComposedFailures(
            modifier = Modifier.fillMaxWidth(),
            csvError = ContainsUpperCaseLetterValidator().keywordOnError
        )
    }
}
