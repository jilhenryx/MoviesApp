package com.example.moviesapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviesapp.R


private val light = Font(R.font.poppins_light, FontWeight.W300)
private val regular = Font(R.font.poppins_regular, FontWeight.W400)
private val medium = Font(R.font.poppins_medium, FontWeight.W500)
private val semi_bold = Font(R.font.poppins_semi_bold, FontWeight.W600)

val fontFamily = FontFamily(light, regular, medium, semi_bold)

// Set of Material typography styles to start with

fun themedTypography(textColor: Color) = Typography(
    defaultFontFamily = fontFamily,
    h3 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 48.sp,
        letterSpacing = 0.sp,
        color = textColor,
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        letterSpacing = 0.sp,
        color = textColor,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        letterSpacing = 0.sp,
        color = textColor,
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
        color = textColor,
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
    )
)

