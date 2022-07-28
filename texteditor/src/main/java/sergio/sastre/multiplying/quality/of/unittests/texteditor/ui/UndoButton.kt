package sergio.sastre.multiplying.quality.of.unittests.texteditor.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import sergio.sastre.multiplying.quality.of.unittests.texteditor.R

@Composable
fun UndoButton(
    modifier: Modifier = Modifier,
    isEnabledProvider: () -> Boolean,
    onUndo: () -> Unit,
) {
    val isEnabled by remember { derivedStateOf { isEnabledProvider() } }

    IconButton(
        modifier = modifier,
        onClick = {
            onUndo()
        },
        enabled = isEnabled,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_undo_24),
            contentDescription = stringResource(R.string.undo_action),
        )
    }
}