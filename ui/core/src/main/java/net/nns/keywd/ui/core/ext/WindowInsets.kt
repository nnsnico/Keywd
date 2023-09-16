package net.nns.keywd.ui.core.ext

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable

@Composable
fun WindowInsets.Companion.zero(): WindowInsets = WindowInsets(
    left = 0,
    top = 0,
    right = 0,
    bottom = 0,
)
