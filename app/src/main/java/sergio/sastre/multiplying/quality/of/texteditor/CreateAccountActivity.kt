package sergio.sastre.multiplying.quality.of.texteditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import sergio.sastre.multiplying.quality.of.texteditor.ui.CreateAccount

class CreateAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateAccount()
        }
    }
}
