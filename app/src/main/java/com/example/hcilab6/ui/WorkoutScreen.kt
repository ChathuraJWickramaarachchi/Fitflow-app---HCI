package com.example.hcilab6.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hcilab6.FitFlowViewModel

@Composable
fun WorkoutScreen(vm: FitFlowViewModel) {
    var newItem by rememberSaveable { mutableStateOf("") }
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Workout Builder", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = newItem, onValueChange = { newItem = it },
                label = { Text("Add exercise") }, singleLine = true, modifier = Modifier.weight(1f)
            )
            Button(onClick = { vm.addExercise(newItem); newItem = "" }) { Text("Add") }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            itemsIndexed(vm.workout) { i, name ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("${i + 1}. $name", Modifier.weight(1f))
                        TextButton(onClick = { vm.moveUp(i) }) { Text("Up") }
                        TextButton(onClick = { vm.moveDown(i) }) { Text("Down") }
                        IconButton(onClick = { vm.removeExercise(i) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete $name")
                        }
                    }
                }
            }
        }
    }
}