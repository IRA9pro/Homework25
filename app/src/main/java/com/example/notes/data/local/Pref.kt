package com.example.notes.data.local

import android.content.Context

class Pref(context: Context) {

    val pref = context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun setIntroShown() {
        pref.edit().putBoolean("isIntroShown", true).apply()
    }

    fun isIntroShown(): Boolean = pref.getBoolean("isIntroShown", false)
}