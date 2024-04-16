package com.ebmobile.spannabletext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ebmobile.spannabletext.data.SpannableTextData
import com.ebmobile.spannabletext.ui.SpannableText
import com.ebmobile.spannabletext.ui.theme.SpannableTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpannableTextTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Samples()
                }
            }
        }
    }
}

@Composable
fun Samples() {

    Column(modifier = Modifier.fillMaxSize()) {
        Sample1()
        Spacer(modifier = Modifier.size(10.dp))
        Sample2()
        Spacer(modifier = Modifier.size(10.dp))
        Sample3()
    }

}

@Composable
fun Sample1() {
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
                    spannableTextStyle = MaterialTheme.typography.labelLarge,
                    onClick = {
                        Log.e("SpannableTextData", "it=$it")

                    }
                )
            )
        )
    }
}

@Composable
fun Sample2() {
    val spannableText1 = "acık rıza"
    val spannableText2 = "kişisel verilerimin"

    val visibilityDescription =
        "Tarafıma avantajlı tekliflerin sunulabilmesi amacıyla kişisel verilerimin işlenmesine ve paylaşılmasına acık rıza veriyorum"

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

@Composable
fun Sample3() {
    val context = LocalContext.current
    val spannableText1 = "enesbaskaya"
    val spannableText2 = "enesb08"
    val spannableText3 = "enesbaskaya08"


    val visibilityDescription =
        "Sosyal medya hesaplarımdan bana ulasabilirsiniz" +
                "\nlinkedin:$spannableText1" +
                "\ngithub:$spannableText2" +
                "\nmedium:$spannableText3"

    val list = listOf(
        SpannableTextData(
            text = spannableText1,
            spannableTextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Magenta),
            isHaveUnderLine = true,
            tag = "linkedin",
            annotation = "https://www.linkedin.com/in/enesbaskaya/",

        ),
        SpannableTextData(
            text = spannableText2,
            spannableTextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Red),
            isHaveUnderLine = true,
            tag = "github",
            annotation = "https://github.com/enesb08",


        ),
        SpannableTextData(
            text = spannableText3,
            spannableTextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue),
            tag = "medium",
            annotation = "https://enesbaskaya08.medium.com/",


        )
    )

    list.forEach{
       it.onClick = {
            Log.e("openLink", "link=" + it.item)
            Log.e("openLink", "tag=" + it.tag)

            context.openLink(it.item)
        }
    }


    Box(modifier = Modifier.background(Color.White)) {
        SpannableText(
            fullText = visibilityDescription,
            defTextStyle = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Left
            ),
            data =list
        )
    }
}

fun Context.openLink(link: String) {
    var url = link
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://" + url
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpannableTextTheme {
        Samples()
    }
}