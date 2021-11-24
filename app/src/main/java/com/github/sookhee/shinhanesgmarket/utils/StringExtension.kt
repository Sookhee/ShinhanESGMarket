package com.github.sookhee.shinhanesgmarket.utils

import android.annotation.SuppressLint
import android.text.Html
import android.text.Spanned
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

@SuppressLint("NewApi")
fun String.calcTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    val postDate = dateFormat.parse(this) as Date
    val diff = (Date().time - postDate.time) / 1000

    val temp = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    return when (diff) {
        in 0 until 10 -> "방금 전"
        in 10 until 60 -> "${diff}초 전"
        in 60 until 60 * 60 -> "${diff/60}분 전"
        in 60 * 60 until 60 * 60 * 24 -> "${diff/(60 * 60)}시간 전"
        in 60 * 60 * 24 until 60 * 60 * 48 -> "어제"
        in 60 * 60 * 48 until 60 * 60 * 24 * 7 -> "${diff/(60 * 60 * 24)}일 전"
        else -> "${temp.monthValue}월 ${temp.dayOfMonth}일"
    }
}

fun Int.parseCategory(): String {
    return when(this) {
        1 -> "디지털기기"
        2 -> "인기매물"
        3 -> "생활가전"
        4 -> "가구/인테리어"
        5 -> "유아동"
        6 -> "생활/가공식품"
        7 -> "유아도서"
        8 -> "스포츠"
        9 -> "여성잡화"
        10 -> "여성의류"
        11 -> "남성패션"
        12 -> "게임/취미"
        else -> "인기매물"
    }
}
