package com.example.radioistops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.material.MaterialTheme
import com.example.radioistops.presentation.navigation.WearNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MaterialTheme {
                androidx.wear.compose.material.Scaffold(
                    timeText = { androidx.wear.compose.material.TimeText() }
                ) {
                    WearNavGraph()
                }
            }
        }
    }
}
