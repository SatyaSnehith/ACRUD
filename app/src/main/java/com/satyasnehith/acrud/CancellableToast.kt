package com.satyasnehith.acrud

import android.content.Context
import android.widget.Toast

object CancellableToast {
    private var toast: Toast? = null

    fun show(
        context: Context,
        text: String,
        length: Int = Toast.LENGTH_SHORT
    ) {
        toast?.cancel()
        toast = Toast.makeText(context, text, length)
        toast?.show()
    }
}