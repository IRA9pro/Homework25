package com.example.notes.ui

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.R.string.default_web_client_id
import com.example.notes.data.local.Pref
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

fun EditText.listener(onTextChanged: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
        }

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
            onTextChanged()
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}

fun Fragment.dialogDelete(onDelete: () -> Unit) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_delete, null)

    val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
    dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    dialog.show()

    dialogView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
        onDelete()
        dialog.dismiss()
    }
    dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
}

fun Fragment.userUX() {
    if (Firebase.auth.currentUser == null)
        findNavController().navigate(R.id.signInFragment)
    else if (!Pref(requireContext()).isIntroShown())
        findNavController().navigate(R.id.introductionFragment)
}

fun Fragment.resetUX() {
    Firebase.auth.signOut()

    val googleSignInClient = GoogleSignIn.getClient(
        requireContext(),
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(default_web_client_id)
        ).requestEmail().build()
    )

    googleSignInClient.signOut().addOnCompleteListener {
        googleSignInClient.revokeAccess().addOnCompleteListener {
            Pref(requireContext()).resetIntro()
            findNavController().navigate(R.id.signInFragment)
        }
    }
}