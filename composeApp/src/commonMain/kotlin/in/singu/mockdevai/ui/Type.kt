package `in`.singu.mockdevai.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val nunitoFontFamily = FontFamily(
//    Font(R.font.nunito_black, FontWeight.Black),
//    Font(R.font.nunito_bold, FontWeight.Bold),
//    Font(R.font.nunito_light, FontWeight.Light),
//    Font(R.font.nunito_medium, FontWeight.Medium),
//    Font(R.font.nunito_regular, FontWeight.Normal),
//    Font(R.font.nunito_extralight, FontWeight.Thin),
//    Font(R.font.nunito_extralight, FontWeight.ExtraLight),
//    Font(R.font.nunito_semibold, FontWeight.SemiBold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Thin,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)