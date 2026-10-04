package com.example.hcilab6.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.hcilab6.FitFlowViewModel

@Composable
fun FitFlowApp(vm: FitFlowViewModel) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf(
        "Home" to Icons.Default.Home,
        "Workout" to Icons.Default.Menu,
        "Social" to Icons.Default.Person,
        "Nutrition" to Icons.Default.Star,
        "Settings" to Icons.Default.Settings
    )
    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { i, (label, icon) ->
                    NavigationBarItem(
                        selected = tab == i,
                        onClick = { tab = i },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (tab) {
                0 -> HomeScreen(vm)
                1 -> WorkoutScreen(vm)
                2 -> SocialScreen(vm)
                3 -> NutritionScreen(vm)
                else -> SettingsScreen(vm)
            }
        }
    }
}