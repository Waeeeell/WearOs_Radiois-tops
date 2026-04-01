package com.example.proyectoreloj.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.example.proyectoreloj.presentation.viewmodel.MotivationalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    motivationalViewModel: MotivationalViewModel,
    onNavigateToActivity: () -> Unit,
    onNavigateToSos: () -> Unit,
    onNavigateToEcg: () -> Unit
) {
    val motivationalMessage by motivationalViewModel.currentHomeMessage.collectAsState()
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val currentDate = SimpleDateFormat("d 'de' MMMM", Locale("es", "ES")).format(Date())
    val listState = rememberScalingLazyListState()

    // ScalingLazyColumn es vital para Wear OS: centra y escala el contenido automáticamente
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Batería
        item {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(color = Color(0xFF2E7D32), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("⚡ 72%", style = MaterialTheme.typography.caption1.copy(fontSize = 10.sp, color = Color.White))
            }
        }

        // Fecha
        item {
            Text(currentDate, style = MaterialTheme.typography.body2.copy(fontSize = 12.sp, color = Color.LightGray))
        }

        // Hora Grande
        item {
            Text(
                currentTime,
                style = MaterialTheme.typography.display1.copy(
                    fontSize = 44.sp, 
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        // Mensaje Motivacional
        item {
            Text(
                text = motivationalMessage,
                style = MaterialTheme.typography.body1.copy(fontSize = 13.sp, color = Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { motivationalViewModel.nextHomeMessage() }
            )
        }

        // Salud (Corazón y O2)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pulso
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onNavigateToEcg() }.padding(horizontal = 10.dp)
                ) {
                    Text("❤️", fontSize = 18.sp)
                    Text("78", style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))
                    Text("bpm", style = MaterialTheme.typography.caption2.copy(fontSize = 8.sp))
                }

                Spacer(modifier = Modifier.width(1.dp).height(24.dp).background(Color.Gray))

                // Oxígeno
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.size(20.dp).clip(CircleShape).background(Color(0xFF26C6DA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("O₂", style = MaterialTheme.typography.caption1.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White))
                    }
                    Text("95%", style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
        
        // Espacio final para que no se corte abajo
        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}
