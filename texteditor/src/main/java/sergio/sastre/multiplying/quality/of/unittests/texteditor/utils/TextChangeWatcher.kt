package sergio.sastre.multiplying.quality.of.unittests.texteditor.utils

import android.text.Editable
import android.text.TextWatcher

class TextChangeWatcher(val onTextChanged: (String, Int) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // no-op
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s.toString(), start + count)
    }

    override fun afterTextChanged(s: Editable?) {
        // no-op
    }

}