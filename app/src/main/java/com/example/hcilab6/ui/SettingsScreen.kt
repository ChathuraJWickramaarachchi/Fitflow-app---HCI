package com.example.hcilab6.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.PackageInfoCompat
import com.example.hcilab6.FitFlowViewModel

private const val PRIVACY_URL = "https://fitflow.example/privacy" // oyage real URL eka damma

@Composable
fun SettingsScreen(vm: FitFlowViewModel) {
    val context = LocalContext.current
    var confirmDelete by remember { mutableStateOf(false) }

    val versionText = remember {
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            "Version ${info.versionName} (build ${PackageInfoCompat.getLongVersionCode(info)})"
        } catch (e: Exception) {
            "Version unknown"
        }
    }

    Column(
        Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Privacy", fontWeight = FontWeight.SemiBold)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("AI personalisation", Modifier.weight(1f))
            Switch(checked = vm.personalization, onCheckedChange = { vm.updatePersonalization(it) })
        }
        Text(
            "When off, FitFlow gives a standard plan and does not adapt to your energy level.",
            style = MaterialTheme.typography.bodySmall
        )

        OutlinedButton(
            onClick = {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_URL)))
                } catch (e: ActivityNotFoundException) { /* no browser installed */ }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Read our Privacy Policy") }

        Button(onClick = { confirmDelete = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Delete my data")
        }

        Text("About", fontWeight = FontWeight.SemiBold)
        Text(versionText)
        Text(
            "FitFlow gives general fitness information and is not medical advice. " +
                    "Consult a doctor before starting a new programme.",
            style = MaterialTheme.typography.bodySmall
        )
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete all data?") },
            text = { Text("This removes your workouts, meals and settings from this device. It cannot be undone.") },
            confirmButton = { TextButton(onClick = { vm.deleteAllData(); confirmDelete = false }) { Text("Delete") } },
            dismissButton = { TextButton(onClick = { confirmDelete = false }) { Text("Cancel") } }
        )
    }
}