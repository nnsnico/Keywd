package net.nns.keywd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import net.nns.keywd.ui.core.theme.KeywdTheme
import net.nns.keywd.ui.home.Home

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            KeywdTheme {
                Home()
            }
        }
    }
}
