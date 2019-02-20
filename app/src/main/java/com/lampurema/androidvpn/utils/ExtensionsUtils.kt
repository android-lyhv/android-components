import android.text.Editable
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner

/**
 * Created by Ly Ho V. on April 19, 2018
 */
fun EditText.onTextChanged(onTextChanged: (text: String) -> Unit) {
    this.addTextChangedListener(object : android.text.TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged(p0.toString())
        }

    })
}

fun Spinner.onItemSelected(onItemSelect: (position: Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
            onItemSelect(position)
        }

        override fun onNothingSelected(parentView: AdapterView<*>) {
            // No-op
        }
    }
}

fun String.isEmailValidate(): Boolean {
    return Patterns.EMAIL_ADDRESS
        .matcher(this).matches()
}

fun String.isPasswordValidate(): Boolean {
    return this.isNotBlank() && this.length >= 8
}

fun String.removeSpace(): String {
    return this.trim().replace("\\s+", "\\s")
}
