package com.adamgeraghty.scorecard.ui.components

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
annotation class ArcheryPreview

@Preview(
    name = "Large font",
    fontScale = 1.5f,
)
@Preview(
    name = "Small font",
    fontScale = 0.5f,
)
annotation class FontPreviews
