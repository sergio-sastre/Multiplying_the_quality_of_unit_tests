package sergio.sastre.multiplying.quality.of.unittests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import sergio.sastre.multiplying.quality.of.unittests.ui.CreateAccount

class CreateAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateAccount()
        }
    }
}
