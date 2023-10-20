package com.example.bullseye

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.random.Random

@Composable
fun BullsEyeGame() {
    var playerScore by remember { mutableStateOf(0) }
    var roundsPlayed by remember { mutableStateOf(0) }
    var inProgress by remember { mutableStateOf(false) }

    if (!inProgress) {
        Button(
            onClick = {
                playerScore = 0
                roundsPlayed = 0
                inProgress = true
                newRound()
            }
        ) {
            Text(text = "Start New Game")
        }
    } else {
        BullsEyeGameScreen(playerScore, roundsPlayed, inProgress) { score ->
            playerScore += score
            roundsPlayed++
            if (roundsPlayed < MAX_ROUNDS) {
                newRound()
            } else {
                inProgress = false
            }
        }
    }
}

@Composable
fun BullsEyeGameScreen(
    playerScore: Int,
    roundsPlayed: Int,
    inProgress: Boolean,
    onRoundEnd: (Int) -> Unit
) {
    var targetValue by remember { mutableStateOf(generateRandomTargetValue()) }
    var sliderValue by remember { mutableStateOf(50f) }
    var userFeedback by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Target Value: ${targetValue}",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..100f
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val difference = (targetValue - sliderValue).absoluteValue
                val score = when {
                    difference <= 3 -> 5
                    difference <= 8 -> 1
                    else -> 0
                }
                userFeedback = "You scored $score points!"
                onRoundEnd(score)
            }
        ) {
            Text(text = "Hit Me!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your Score: $playerScore",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = userFeedback,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Rounds Played: $roundsPlayed / $MAX_ROUNDS",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        if (!inProgress) {
            Text(
                text = "Game Over",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Red
            )
        }
    }
}

fun generateRandomTargetValue(): Int {
    return Random.nextInt(0, 101)
}

fun newRound() {

}

private const val MAX_ROUNDS = 5 // Change this to your desired number of rounds




