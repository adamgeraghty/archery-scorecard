package com.adamgeraghty.scorecard.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
@Preview
fun SteppedSlider() {
    var sliderPosition by remember { mutableFloatStateOf(1f) }

    Column {
        Text(text = "Arrows per end: %s".format(sliderPosition.roundToInt().toString()))
        Slider(
            modifier = Modifier.semantics { contentDescription = "Select arrow amount" },
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 1f..24f,
            onValueChangeFinished = {
            },
            // Would be nice to have a custom slider here eventually
//            thumb = {
//                Label(
//                    label = {
//                        PlainTooltip(modifier = Modifier.sizeIn(45.dp, 25.dp).wrapContentWidth()) {
//                            Text("%.2f".format(sliderPosition))
//                        }
//                    },
//                    interactionSource = interactionSource
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowDropDown,
//                        contentDescription = null,
//                        modifier = Modifier.size(ButtonDefaults.IconSize),
//                        tint = Color.Red
//                    )
//                }
//            },
            steps = 23,
        )
    }
}
