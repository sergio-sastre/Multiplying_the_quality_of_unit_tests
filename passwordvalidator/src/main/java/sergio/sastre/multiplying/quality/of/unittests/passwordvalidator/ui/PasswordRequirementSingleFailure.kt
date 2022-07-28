package sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sergio.sastre.multiplying.quality.of.unittests.passwordvalidator.R

@Composable
fun PasswordRequirementSingleFailure(
    modifier: Modifier = Modifier,
    singleErrorProvider: () -> String,
) {
    Row(
        modifier = modifier.padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(8.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            tint = colorResource(id = R.color.design_default_color_error),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.clearAndSetSemantics { },
            fontSize = 12.sp,
            text = singleErrorProvider(),
            color = colorResource(id = R.color.design_default_color_error),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PasswordRequirementSingleFailure() {
    MaterialTheme {
        PasswordRequirementSingleFailure(
            modifier = Modifier.fillMaxWidth(),
            singleErrorProvider = { "must contain this requirement" },
        )
    }
}
