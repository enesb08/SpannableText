package com.ebmobile.spannabletext.ext

fun String.findWordIndices(
    targetWord: String,
): List<Pair<Int, Int>> {
    val regex = Regex("\\b$targetWord\\b")
    val result = mutableListOf<Pair<Int, Int>>()

    regex.findAll(this).forEach { match ->
        result.add(Pair(match.range.first, match.range.last + 1))
    }

    return result
}