package net.nns.keywd.ui.core.annotation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark theme")
annotation class MultiThemePreviews
