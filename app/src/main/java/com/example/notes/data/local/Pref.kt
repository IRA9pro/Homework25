package com.example.notes.data.local

import android.content.Context
import androidx.core.content.edit

class Pref(context: Context) {

    val pref = context.getSharedPreferences("Note", Context.MODE_PRIVATE)

    fun setIntroShown() {
        pref.edit { putBoolean("isIntroShown", true) }
    }

    fun resetIntro() {
        pref.edit { putBoolean("isIntroShown", false) }
    }

    fun isIntroShown(): Boolean = pref.getBoolean("isIntroShown", false)
}