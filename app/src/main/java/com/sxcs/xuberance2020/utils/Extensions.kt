package com.sxcs.xuberance2020.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.widget.EditText
import android.widget.Toast
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Constants
import com.sxcs.xuberance2020.data.models.Registration

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun getDate(day: Int) = if (day == 1) Constants.DATE_DAY1 else Constants.DATE_DAY2

fun moveGradient(drawable: AnimationDrawable) {
    drawable.apply {
        setEnterFadeDuration(1500)
        setExitFadeDuration(2000)

        start()
    }
}

fun HashMap<String, Registration>.getList(): String {
    val sb = StringBuilder()
    for ((k, v) in this) {
        //language=HTML
        sb.append(
            "<b>$k</b>" +
                    "<br>" +
                    "<ul type=\"disc\">"
        )
        for (participant in v.participants) {
            //language=HTML
            sb.append("<li>${participant!!.name}, ${participant.phoneNumber}</li>")
        }
        //language=HTML
        sb.append("</ul>")
    }
    return sb.toString()
}

fun Activity.getColorFromCode(colorCode: Int) =
    when (colorCode) {
        0 -> getColor(R.color.color_clouds)
        1 -> getColor(R.color.color_sky)
        2 -> Color.YELLOW
        3 -> Color.RED
        4 -> Color.BLUE
        else -> 0
    }

fun validateXuberanceEmail(email: String) = Regex("\\w+@xuberance20.com") in email

fun validateEmail(email: String) = Regex("[\\w._\\-]+@\\w+.com") in email

fun validatePassword(password: String): String {
    if (password.length > 7)
        return "Password is too short!"

    if (password.count { it.isUpperCase() } == 0 ||
        password.count { it.isLowerCase() } == 0 ||
        password.count { it.isWhitespace() } > 0)
        return "Password should have at least 1 upper case, 1 lower case and no spaces"

    return ""
}

fun EditText.getInt(): Int {
    return if (text.isNotBlank()) text.toString().toInt() else 0
}