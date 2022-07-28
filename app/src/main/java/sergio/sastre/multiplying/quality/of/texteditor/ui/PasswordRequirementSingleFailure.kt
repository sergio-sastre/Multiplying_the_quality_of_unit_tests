package sergio.sastre.multiplying.quality.of.texteditor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordRequirementSingleFailure(
    modifier: Modifier = Modifier,
    message: String,
    errorColor: Color = MaterialTheme.colors.error,
) {
    Row(
        modifier = modifier.padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(8.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            tint = errorColor,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.clearAndSetSemantics {  },
            fontSize = 12.sp,
            text = message,
            color = errorColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PasswordRequirementSingleFailure() {
    MaterialTheme {
        PasswordRequirementSingleFailure(
            modifier = Modifier.fillMaxWidth(),
            message = "must contain this requirement",
        )
    }
}
