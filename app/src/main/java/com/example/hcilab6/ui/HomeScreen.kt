package com.example.hcilab6.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hcilab6.FitFlowViewModel
import com.example.hcilab6.data.Energy
import com.example.hcilab6.ui.theme.Orange
import com.example.hcilab6.ui.theme.Teal

@Composable
fun HomeScreen(vm: FitFlowViewModel) {
    Column(
        Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Good day, Alex", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        Card(colors = CardDefaults.cardColors(containerColor = Teal), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Daily Flow", color = Color.White, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge)
                Text("How is your energy today?", color = Color.White)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Energy.values().forEach { level ->
                        val selected = vm.energy == level
                        Button(
                            onClick = { vm.updateEnergy(level) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selected) Orange else Color.White,
                                contentColor = if (selected) Color.White else Color(0xFF1B2A49)
                            )
                        ) { Text(level.label) }
                    }
                }
                Text("Recommended: ${vm.dailyPlan}", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(
                    "${vm.workout.size} exercises in your builder. You can change this plan anytime.",
                    color = Color.White, style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Weekly progress: ${vm.workoutsDone} / ${vm.weeklyGoal} workouts", fontWeight = FontWeight.SemiBold)
                LinearProgressIndicator(
                    progress = { vm.workoutsDone / vm.weeklyGoal.toFloat() },
                    modifier = Modifier.fillMaxWidth()
                )
                if (vm.workoutsDone >= vm.weeklyGoal) {
                    Text("Goal reached! Amazing work!", color = Orange, fontWeight = FontWeight.Bold)
                }
                Button(onClick = { vm.completeWorkout() }) { Text("Complete today's workout") }
            }
        }
    }
}