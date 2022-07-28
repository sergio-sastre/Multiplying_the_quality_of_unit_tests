package sergio.sastre.multiplying.quality.of.texteditor.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sergio.sastre.multiplying.quality.of.unittests.R

@Composable
fun CreateAccountHeader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Start,
        text = stringResource(id = R.string.header_create_account),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_CreateAccountHeader() {
    MaterialTheme {
        CreateAccountHeader(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
        )
    }
}
