package sergio.sastre.multiplying.quality.of.unittests.texteditor.utils

import android.os.Build
import android.text.InputType.*
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun EditText.setInputTypeWithoutWordSuggestions(inputType: Int) {
    setInputType(inputType or TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or
            TYPE_TEXT_FLAG_NO_SUGGESTIONS)
    privateImeOptions = "nm,com.google.android.inputmethod.latin.noMicrophoneKey"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
    }
}

fun EditText.disableTextWatcherWhile(textWatcher: TextWatcher, action: () -> Unit){
    removeTextChangedListener(textWatcher)
    action()
    addTextChangedListener(textWatcher)
}