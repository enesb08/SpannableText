package com.ebmobile.spannabletext.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ebmobile.spannabletext.data.SpannableTextData
import com.ebmobile.spannabletext.ext.findWordIndices

@Composable
fun SpannableText(
    modifier: Modifier = Modifier,
    fullText: String,
    defTextStyle: TextStyle,
    data: List<SpannableTextData>,
) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        withStyle(defTextStyle.toSpanStyle()) {
            append(fullText)
        }

        data.forEachIndexed { idex, dataItem ->
            AddSpannableText(this, fullText, dataItem, defTextStyle)
        }
    }

    Column(modifier = modifier) {
        ClickableText(
            modifier = Modifier.animateContentSize(),
            text = annotatedLinkString,
            style = defTextStyle,
            onClick = { offset ->
                data.forEach { annotatedStringData ->
                    if (annotatedStringData.tag != null && annotatedStringData.annotation != null) {
                        annotatedLinkString.getStringAnnotations(
                            tag = annotatedStringData.tag,
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            annotatedStringData.onClick?.invoke(it)
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun AddSpannableText(
    builder: AnnotatedString.Builder,
    fullText: String,
    dataItem: SpannableTextData,
    defTextStyle: TextStyle,
) {
    val newList = fullText.findWordIndices(dataItem.text)

    newList.forEach { startIndex ->
        val spanStyle = (dataItem.spannableTextStyle ?: defTextStyle).copy(
            textDecoration = if (dataItem.isHaveUnderLine) {
                TextDecoration.Underline
            } else TextDecoration.None
        ).toSpanStyle()

        builder.addStyle(
            style = spanStyle,
            start = startIndex.first,
            end = startIndex.second
        )
        builder.addStringAnnotation(
            tag = dataItem.tag ?: "",
            annotation = dataItem.annotation ?: "",
            start = startIndex.first,
            end = startIndex.second
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SpannableTextPreview() {
    val spannableText1 = "acık rıza"
    val spannableText2 = "kişisel verilerimin"

    val visibilityDescription ="Tarafıma avantajlı tekliflerin sunulabilmesi amacıyla kişisel verilerimin işlenmesine ve paylaşılmasına acık rıza veriyorum"

    Box(modifier = Modifier.background(Color.White)) {
        SpannableText(
            fullText = visibilityDescription,
            defTextStyle = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center
            ),
            data = listOf(
                SpannableTextData(
                    text = spannableText1,
                    spannableTextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Red)
                ),
                SpannableTextData(
                    text = spannableText2,
                    isHaveUnderLine = true,
                    spannableTextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue)
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpannableTextWithUnderLinePreview() {
    val spannableText1 = "aydınlatma metnini "

    val visibilityDescription =
        "Kişisel verilerin işlenmesine yönelik aydınlatma metnini kabul ediyorum"

    Box(modifier = Modifier.background(Color.White)) {
        SpannableText(
            fullText = visibilityDescription,
            defTextStyle = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center
            ),
            data = listOf(
                SpannableTextData(
                    text = spannableText1,
                    tag = "tag",
                    isHaveUnderLine = true,
                    annotation = "tag_url",
                    spannableTextStyle = MaterialTheme.typography.bodyMedium,
                            onClick = {
                        Log.e("SpannableTextData", "it=$it")

                    }
                )
            )
        )
    }
}

