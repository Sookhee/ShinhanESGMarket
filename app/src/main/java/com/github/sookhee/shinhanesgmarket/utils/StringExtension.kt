package com.github.sookhee.shinhanesgmarket.utils

import android.text.Html
import android.text.Spanned
import java.text.NumberFormat

fun Int.withComma(): String {
    return NumberFormat.getInstance().format(this)
}

fun String.fromHtml(): Spanned {
    return if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this)
    } else {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }
}