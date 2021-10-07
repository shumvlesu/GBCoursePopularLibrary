package com.shumikhin.gbcoursepopularlibrary

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visible(visible: () -> Boolean): View {
    visibility = if (visible()) {
        View.VISIBLE
    } else {
        View.GONE
    }
    return this
}

//Показать SnackBar c передачей текста в виде String
fun View.showSnakeBar(text: String, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, text, length).show()
}