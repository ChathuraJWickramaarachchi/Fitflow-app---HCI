package com.example.hcilab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.hcilab6.ui.FitFlowApp
import com.example.hcilab6.ui.theme.FitFlowTheme

class MainActivity : ComponentActivity() {
    private val viewModel: FitFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitFlowTheme { FitFlowApp(viewModel) }
        }
    }
}