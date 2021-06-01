package com.lyhv.component.common

import android.text.Editable
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment

/**
 * Created by Ly Ho V. on April 19, 2018
 */
/**
 * Text change
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

fun Spinner.onItemSelected(onItemSelect: (position: Int, selectedItemView: View?) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>,
            selectedItemView: View?,
            position: Int,
            id: Long
        ) {
            if (position >= 0) {
                onItemSelect(position, selectedItemView)
            }
        }

        override fun onNothingSelected(parentView: AdapterView<*>) {
            // No-op
        }
    }
}

fun String.isEmailFormat(): Boolean {
    return Patterns.EMAIL_ADDRESS
        .matcher(this).matches()
}

fun Fragment.getFullNameTag(): String {
    return this.javaClass.name
}