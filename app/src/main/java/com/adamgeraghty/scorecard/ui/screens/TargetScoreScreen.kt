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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.drawscope.Stroke
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

    data object Undo : ArcheryAction()
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
        is ArcheryAction.Undo -> {
            if (state.shots.isNotEmpty()) {
                val newScore = state.score -
                    calculateScore(state.shots.last(), state.targetCenterOffset, state.targetSize)
                var newShotCount = state.shotCount
                newShotCount -= 1
                state.copy(
                    score = newScore,
                    shotCount = newShotCount,
                    shots = state.shots.dropLast(1),
                )
            } else {
                state // Not happy with this, revisit
            }
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetScoreScreen(navController: NavController) {
    val (state, dispatch) = rememberReducer(
        initialState = ArcheryState(),
        reducer = ::archeryReducer,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Archery Score") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
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
                                // Target colors
                                drawCircle(
                                    color = Color.White,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_WHITE_1,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_WHITE_2,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLACK_3,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLACK_4,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Blue,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLUE_5,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Blue,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLUE_6,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Red,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_RED_7,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Red,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_RED_8,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Yellow,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_GOLD_9,
                                    center = size.center,
                                )
                                drawCircle(
                                    color = Color.Yellow,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_GOLD_10,
                                    center = size.center,
                                )

                                // Border rings
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_WHITE_1,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_WHITE_2,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLACK_4,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLUE_5,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_BLUE_6,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_RED_7,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_RED_8,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_GOLD_9,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * TargetConstants.TARGET_SIZE_GOLD_10,
                                    center = size.center,
                                    style = Stroke(width = TargetConstants.TARGET_BORDER),
                                )

                                state.shots.forEach { shot ->
                                    drawCircle(Color.Green, TargetConstants.SHOT_RADIUS, shot)
                                }
                            }
                        }
                    }
                }

                Row {
                    Button(
                        onClick = { dispatch(ArcheryAction.Reset) },
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text("Reset")
                    }
                    Button(
                        onClick = { dispatch(ArcheryAction.Undo) },
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text("Undo")
                    }
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
        distance <= targetSize * TargetConstants.TARGET_SIZE_GOLD_10 -> TargetConstants.TARGET_SCORE_GOLD_10
        distance <= targetSize * TargetConstants.TARGET_SIZE_RED_7 -> TargetConstants.TARGET_SCORE_GOLD_9
        distance <= targetSize * TargetConstants.TARGET_SIZE_RED_8 -> TargetConstants.TARGET_SCORE_RED_8
        distance <= targetSize * TargetConstants.TARGET_SIZE_RED_7 -> TargetConstants.TARGET_SCORE_RED_7
        distance <= targetSize * TargetConstants.TARGET_SIZE_BLUE_6 -> TargetConstants.TARGET_SCORE_BLUE_6
        distance <= targetSize * TargetConstants.TARGET_SIZE_BLUE_5 -> TargetConstants.TARGET_SCORE_BLUE_5
        distance <= targetSize * TargetConstants.TARGET_SIZE_BLACK_4 -> TargetConstants.TARGET_SCORE_BLACK_4
        distance <= targetSize * TargetConstants.TARGET_SIZE_BLACK_3 -> TargetConstants.TARGET_SCORE_BLACK_3
        distance <= targetSize * TargetConstants.TARGET_SIZE_WHITE_2 -> TargetConstants.TARGET_SCORE_WHITE_2
        distance <= targetSize * TargetConstants.TARGET_SIZE_WHITE_1 -> TargetConstants.TARGET_SCORE_WHITE_1
        else -> 0
    }
}

object TargetConstants {
    const val TARGET_SIZE_GOLD_10 = 0.1f
    const val TARGET_SIZE_GOLD_9 = 0.2f
    const val TARGET_SIZE_RED_8 = 0.3f
    const val TARGET_SIZE_RED_7 = 0.4f
    const val TARGET_SIZE_BLUE_6 = 0.5f
    const val TARGET_SIZE_BLUE_5 = 0.6f
    const val TARGET_SIZE_BLACK_4 = 0.7f
    const val TARGET_SIZE_BLACK_3 = 0.8f
    const val TARGET_SIZE_WHITE_2 = 0.9f
    const val TARGET_SIZE_WHITE_1 = 1f
    const val TARGET_SCORE_GOLD_10 = 10
    const val TARGET_SCORE_GOLD_9 = 9
    const val TARGET_SCORE_RED_8 = 8
    const val TARGET_SCORE_RED_7 = 7
    const val TARGET_SCORE_BLUE_6 = 6
    const val TARGET_SCORE_BLUE_5 = 5
    const val TARGET_SCORE_BLACK_4 = 4
    const val TARGET_SCORE_BLACK_3 = 3
    const val TARGET_SCORE_WHITE_2 = 2
    const val TARGET_SCORE_WHITE_1 = 1
    const val SHOT_RADIUS = 10f
    const val TARGET_BORDER = 3f
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
