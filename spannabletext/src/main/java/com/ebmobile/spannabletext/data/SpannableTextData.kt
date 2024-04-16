package com.ebmobile.spannabletext.data

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

data class SpannableTextData(
    val text: String,
    val tag: String? = null,
    val annotation: String? = null,
    val isHaveUnderLine: Boolean = false,
    val spannableTextStyle: TextStyle? = null,
    var onClick: ((str: AnnotatedString.Range<String>) -> Unit)? = null,
)