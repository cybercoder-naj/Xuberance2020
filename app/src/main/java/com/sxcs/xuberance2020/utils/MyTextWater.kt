package com.sxcs.xuberance2020.utils

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MyTextWater(
    private val editText: EditText,
    private val deductCoins: () -> Unit
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        deductCoins()
        if (s?.toString()?.isNotBlank() == true) {
            if (s.toString().toInt() !in 0..250)
                editText.setTextColor(Color.RED)
            else
                editText.setTextColor(Color.BLACK)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s?.toString()?.isNotBlank() == true && s.toString().toInt() !in 0..250)
            editText.apply {
                error = "You cannot invest more than 250 X - Points"
                requestFocus()
            }
    }
}