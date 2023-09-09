package net.nns.keywd.ui.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import net.nns.keywdui.core.R

private val defaultFontFamily = FontFamily(Font(R.font.shirokuma_regular))
private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = defaultFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = defaultFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = defaultFontFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = defaultFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = defaultFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = defaultFontFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = defaultFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = defaultFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = defaultFontFamily),
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
