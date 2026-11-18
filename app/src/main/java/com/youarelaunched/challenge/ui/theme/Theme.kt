package com.youarelaunched.challenge.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = TopBarColor,
    primaryVariant = TopBarColor,
    secondary = TopBarColor,
    background = VendorBackground,
    surface = VendorCardBackground,
    onPrimary = Color.White,
    onBackground = VendorTitleColor,
    onSurface = VendorTitleColor
)

@Composable
fun VendorAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = VendorTypography,
        content = content
    )
}
