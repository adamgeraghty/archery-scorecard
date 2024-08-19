package com.adamgeraghty.scorecard.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

sealed class ArcheryAction {
    data class Shoot(val shot: Offset) : ArcheryAction()

    data class UpdateCenter(val center: Offset) : ArcheryAction()

    data object Reset : ArcheryAction()
}

data class ArcheryState(
    val score: Int = 0,
    val targetSize: Float = 500f,
    val shotCount: Int = 0,
    val shots: List<Offset> = emptyList(),
    val targetCenterOffset: Offset = Offset(0f, 0f),
    val invalidations: Int = 0,
)

fun archeryReducer(
    state: ArcheryState,
    action: ArcheryAction,
): ArcheryState {
    return when (action) {
        is ArcheryAction.Shoot -> {
            val newScore = state.score + calculateScore(action.shot, state.targetCenterOffset, state.targetSize)
            state.copy(
                score = newScore,
                shotCount = state.shotCount + 1,
                shots = state.shots + action.shot,
            )
        }
        is ArcheryAction.UpdateCenter -> {
            state.copy(
                targetCenterOffset = action.center,
            )
        }
        ArcheryAction.Reset -> {
            state.copy(
                score = 0,
                shotCount = 0,
                shots = emptyList(),
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MagicNumber")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetScoreScreen(navController: NavController) {
    val (state, dispatch) = rememberReducer(
        initialState = ArcheryState(),
        reducer = ::archeryReducer,
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Archery Score") }) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text("Score: ${state.score}", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Target Size: ${state.targetSize.toInt()} dp", fontSize = 18.sp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(state.targetSize.dp)
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    dispatch(ArcheryAction.Shoot(offset))
                                }
                            },
                    ) {
                        Canvas(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            // Save the center of the target. kinda stinks as its saved every recomposition, also wont
                            // work when supporting multiple targets
                            dispatch(ArcheryAction.UpdateCenter(size.center))

                            // Note: Required as recomposition was stopping after a few seconds, thus stopping canvas
                            // draw updates. Using this I can trigger recomposition again only when new shots are added
                            state.shotCount.let {
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_GOLD,
                                    center = size.center,
                                )

                                drawCircle(
                                    color = Color.Blue,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_RED,
                                    center = size.center,
                                )

                                drawCircle(
                                    color = Color.Red,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLUE,
                                    center = size.center,
                                )

                                drawCircle(
                                    color = Color.Yellow,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLACK,
                                    center = size.center,
                                )

                                state.shots.forEach { shot ->
                                    drawCircle(Color.Green, TargetConstants.SHOT_RADIUS, shot)
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { dispatch(ArcheryAction.Reset) },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text("Reset")
                }
            }
        },
    )
}

// TODO eventually use a list of offsets, so app can support targets with multiple faces like Vegas
fun calculateScore(
    shot: Offset,
    center: Offset,
    targetSize: Float,
): Int {
    val centerX = center.x
    val centerY = center.y

    val distance = Math.sqrt(
        ((shot.x - centerX) * (shot.x - centerX) + (shot.y - centerY) * (shot.y - centerY)).toDouble(),
    )

    return when {
        distance <= targetSize * TargetConstants.TARGET_SIZE_GOLD -> TargetConstants.TARGET_SCORE_GOLD
        distance <= targetSize * TargetConstants.TARGET_SIZE_RED -> TargetConstants.TARGET_SCORE_RED
        distance <= targetSize * TargetConstants.TARGET_SIZE_BLUE -> TargetConstants.TARGET_SCORE_BLUE
        distance <= targetSize * TargetConstants.TARGET_SIZE_BLACK -> TargetConstants.TARGET_SCORE_BLACK
        else -> 0
    }
}

object TargetConstants {
    const val TARGET_SIZE_GOLD = 0.2f
    const val TARGET_SIZE_RED = 0.4f
    const val TARGET_SIZE_BLUE = 0.6f
    const val TARGET_SIZE_BLACK = 0.8f
    const val TARGET_SCORE_GOLD = 10
    const val TARGET_SCORE_RED = 8
    const val TARGET_SCORE_BLUE = 6
    const val TARGET_SCORE_BLACK = 4
    const val SHOT_RADIUS = 10f
}

// Helper function to create a Redux-like store
@Composable
fun <S, A> rememberReducer(
    initialState: S,
    reducer: (S, A) -> S,
): Pair<S, (A) -> Unit> {
    var state by remember { mutableStateOf(initialState) }
    return state to { action: A ->
        state = reducer(state, action)
    }
}

@Preview(showBackground = true)
@Composable
fun TargetScoreScreenPreview() {
    TargetScoreScreen(navController = rememberNavController())
}