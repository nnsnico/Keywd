package net.nns.keywd.ui.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.nns.keywdui.core.R

private val defaultFontFamily = FontFamily(Font(R.font.shirokuma_regular))
private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = defaultFontFamily),
    bodyMedium = defaultTypography.bodyLarge.copy(fontFamily = defaultFontFamily),
    bodySmall = defaultTypography.bodyMedium.copy(fontFamily = defaultFontFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = defaultFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = defaultFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = defaultFontFamily),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
