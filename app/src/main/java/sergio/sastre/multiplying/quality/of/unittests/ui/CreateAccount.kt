package sergio.sastre.multiplying.quality.of.unittests.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import sergio.sastre.multiplying.quality.of.unittests.CreateAccountViewModel

@Composable
fun CreateAccount() {
    val viewModel: CreateAccountViewModel = viewModel()
    MaterialTheme {
        CreateAccountContent(
            state = viewModel.uiState.collectAsState().value,
            handleEvent = viewModel::handleEvent
        )
    }
}
