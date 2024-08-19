package com.adamgeraghty.scorecard.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                            // Save the center of the target. kinda stinks as its saved every recomposition, also wont work when supporting
                            // multiple targets
                            dispatch(ArcheryAction.UpdateCenter(size.center))

                            // Note: Required as recomposition was stopping after a few seconds, thus stopping canvas draw updates.
                            // Using this I can trigger recomposition again only when new shots are added
                            state.shotCount.let {
                                drawCircle(
                                    color = Color.Black,
                                    radius = state.targetSize * 0.8f,
                                    center = size.center,
                                )

                                drawCircle(
                                    color = Color.Blue,
                                    radius = state.targetSize * 0.6f,
                                    center = size.center,
                                )

                                drawCircle(
                                    color = Color.Red,
                                    radius = state.targetSize * 0.4f,
                                    center = size.center,
                                )

                                drawCircle(
                                    color = Color.Yellow,
                                    radius = state.targetSize * 0.2f,
                                    center = size.center,
                                )

                                state.shots.forEach { shot ->
                                    drawCircle(Color.Green, 10f, shot)
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

    val distance = Math.sqrt(((shot.x - centerX) * (shot.x - centerX) + (shot.y - centerY) * (shot.y - centerY)).toDouble())

    return when {
        distance <= targetSize * 0.2f -> 10
        distance <= targetSize * 0.4f -> 8
        distance <= targetSize * 0.6f -> 6
        distance <= targetSize * 0.8f -> 4
        else -> 0
    }
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
