package com.example.hcilab6.ui

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hcilab6.FitFlowViewModel
import com.example.hcilab6.data.DemoData
import com.example.hcilab6.data.Meal

@Composable
fun NutritionScreen(vm: FitFlowViewModel) {
    var photo by remember { mutableStateOf<Bitmap?>(null) }
    var guess by remember { mutableStateOf<Meal?>(null) }

    val camera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp ->
        if (bmp != null) { photo = bmp; guess = DemoData.foods.random() }
    }

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Nutrition Logger", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Button(
            onClick = { camera.launch(null) },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) { Text("Snap your meal") }

        photo?.let {
            Image(it.asImageBitmap(), contentDescription = "Photo of your meal",
                modifier = Modifier.fillMaxWidth().height(160.dp))
        }
        guess?.let { g ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("We think this is: ${g.name} (~${g.kcal} kcal)")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { vm.addMeal(g); guess = null; photo = null }) { Text("Confirm") }
                        OutlinedButton(onClick = { guess = DemoData.foods.random() }) { Text("Not right") }
                    }
                }
            }
        }
        Text("Today: ${vm.totalKcal()} kcal", fontWeight = FontWeight.SemiBold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(vm.meals) { m -> Text("- ${m.name}: ${m.kcal} kcal") }
        }
    }
}