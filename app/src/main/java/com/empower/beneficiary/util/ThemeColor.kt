package com.empower.beneficiary.util

import android.content.Context
import android.util.TypedValue

/**
 * Retrieves the primary color from the current theme.
 * Uses [TypedValue] to resolve the [android.R.attr.colorPrimary] attribute defined in the theme.
 *
 * @return The color value defined as the primary color in the current theme.
 */
val Context.getThemePrimaryColor
    get() = TypedValue()
        .apply { theme.resolveAttribute(android.R.attr.colorPrimary, this, true) }
        .data

/**
 * Retrieves the background color from the current theme.
 * Uses [TypedValue] to resolve the [android.R.attr.colorBackground] attribute defined in the theme.
 *
 * @return The color value defined as the background color in the current theme.
 */
val Context.getThemeBackgroundColor
    get() = TypedValue()
        .apply { theme.resolveAttribute(android.R.attr.colorBackground, this, true) }
        .data
