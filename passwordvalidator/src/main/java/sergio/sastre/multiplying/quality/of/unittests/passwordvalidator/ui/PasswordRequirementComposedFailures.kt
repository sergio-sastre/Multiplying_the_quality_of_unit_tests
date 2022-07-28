package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.model.validators.ContainsUpperCaseLetterValidator

@Composable
fun PasswordRequirementComposedFailures(
    modifier: Modifier = Modifier,
    isVisibleProvider: () -> Boolean,
    csvErrorProvider: () -> String?,
) {
    val visible by remember { derivedStateOf { isVisibleProvider() } }
    val csvError by remember { derivedStateOf { csvErrorProvider() } }

    AnimatedVisibility(
        visible = visible,
    ) {
        Column(modifier = modifier.animateContentSize()) {
            csvError?.splitToSequence(',', ignoreCase = true, limit = 0)?.forEach {
                singleError ->
                PasswordRequirementSingleFailure(
                    singleErrorProvider = { singleError },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PasswordRequirementComposedFailures() {
    MaterialTheme {
        PasswordRequirementComposedFailures(
            modifier = Modifier.fillMaxWidth(),
            csvErrorProvider = { ContainsUpperCaseLetterValidator().keywordOnError },
            isVisibleProvider = { true }
        )
    }
}
