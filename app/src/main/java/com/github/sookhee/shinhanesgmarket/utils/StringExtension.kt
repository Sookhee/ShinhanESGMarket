package com.github.sookhee.shinhanesgmarket.utils

import java.text.NumberFormat

fun Int.withComma(): String {
    return NumberFormat.getInstance().format(this)
}
