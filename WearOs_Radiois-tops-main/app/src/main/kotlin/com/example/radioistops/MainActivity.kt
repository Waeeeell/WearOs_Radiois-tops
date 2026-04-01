package com.example.radioistops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.material.MaterialTheme
import com.example.radioistops.presentation.navigation.WearNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.e("DEBUG_WEAR", "¡MainActivity HA ARRANCADO CORRETAMENTE!")
        
        setContent {
            MaterialTheme {
                // REQUIRED ON WEAR OS: Si la app no tiene un componente deslizable nativo
                // el sistema puede bloquear los frames visuales de Compose creyendo que no tiene root válido.
                val state = androidx.wear.compose.material.rememberSwipeToDismissBoxState()
                androidx.wear.compose.material.SwipeToDismissBox(
                    state = state,
                    onDismissed = { finish() }
                ) { isBackground ->
                    if (!isBackground) {
                        androidx.wear.compose.material.Scaffold(
                            timeText = { androidx.wear.compose.material.TimeText() }
                        ) {
                            WearNavGraph()
                        }
                    }
                }
            }
        }
    }
}
