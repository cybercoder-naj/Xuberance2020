package com.sxcs.xuberance2020.data

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.sxcs.xuberance2020.BaseApplication
import com.sxcs.xuberance2020.data.Constants.PREF_FIRST_TIME
import com.sxcs.xuberance2020.data.Constants.PREF_HAS_EVER_LOGGED_IN
import com.sxcs.xuberance2020.data.Constants.PREF_LOGGED_FIRST_TIME
import com.sxcs.xuberance2020.data.Constants.PREF_USERNAME

object Preferences {

    private val context = BaseApplication.getAppContext()
    private val prefs = context.getSharedPreferences("sharedPrefs", MODE_PRIVATE)

    var firstTime: Boolean
        set(value) =
            prefs.edit {
                putBoolean(PREF_LOGGED_FIRST_TIME, value)
            }
        get() =
            prefs.getBoolean(PREF_LOGGED_FIRST_TIME, true)

    var username: String
        set(value) =
            prefs.edit {
                putString(PREF_USERNAME, value)
            }
        get() =
            prefs.getString(PREF_USERNAME, "") ?: ""

    var hasEverLoggedIn: Boolean
        set(value) =
            prefs.edit {
                putBoolean(PREF_HAS_EVER_LOGGED_IN, value)
            }
        get() =
            prefs.getBoolean(PREF_HAS_EVER_LOGGED_IN, false)
}