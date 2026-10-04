package com.example.hcilab6.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hcilab6.FitFlowViewModel
import com.example.hcilab6.data.DemoData
import com.example.hcilab6.ui.theme.Orange

@Composable
fun SocialScreen(vm: FitFlowViewModel) {
    LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Community", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Share only with my private circle", Modifier.weight(1f))
                Switch(checked = vm.privateCircle, onCheckedChange = { vm.updatePrivateCircle(it) })
            }
            Text(
                if (vm.privateCircle) "Your activity is visible to your circle only."
                else "Your activity is visible to all FitFlow users.",
                style = MaterialTheme.typography.bodySmall
            )
        }
        item { Text("Challenges", fontWeight = FontWeight.SemiBold) }
        items(DemoData.challenges) { c ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(c, Modifier.weight(1f))
                    Button(onClick = { vm.toggleJoin(c) }) { Text(if (c in vm.joined) "Joined" else "Join") }
                }
            }
        }
        item { Text("Feed", fontWeight = FontWeight.SemiBold) }
        items(DemoData.posts) { p ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(p, Modifier.weight(1f))
                    IconButton(onClick = { vm.toggleLike(p) }) {
                        Icon(
                            Icons.Default.Favorite, contentDescription = "Like",
                            tint = if (p in vm.liked) Orange else Color.Gray
                        )
                    }
                }
            }
        }
    }
}