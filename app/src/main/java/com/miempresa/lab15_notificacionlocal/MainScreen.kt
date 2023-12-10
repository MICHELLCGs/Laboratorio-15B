package com.miempresa.lab15_notificacionlocal

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.random.Random

@Composable
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
fun MainScreen(){
    val contexto = LocalContext.current
    val notificationService = NotificationService(contexto)
    var texto1 by remember{ mutableStateOf("") }
    var texto2 by remember{ mutableStateOf("") }
    var numero by remember{ mutableStateOf(0) }
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                val permissionState = rememberPermissionState(
                    permission = Manifest.permission.POST_NOTIFICATIONS
                )
                if (!permissionState.status.isGranted){
                    OutlinedButton(onClick = {permissionState.launchPermissionRequest()}) {
                        Text(
                            text = "Permitir notificación",
                            fontSize = 22.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            TextField(value = texto1, onValueChange = {texto1=it})
            Spacer(modifier = Modifier.size(16.dp))
            TextField(value = texto2, onValueChange = {texto2=it})
            Spacer(modifier = Modifier.size(16.dp))
            Row(
               modifier= Modifier
                   .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Button(onClick = {
                    numero= Random.nextInt(1,10)
                }) {
                    Text(text = "Generar número")
                }
                Text(text = "El número generado es ${numero}")
            }
            Button(onClick = {
                if(numero%2==0){
                    notificationService.showNotification(texto1)
                }
                else{
                    notificationService.showNotification(texto2)
                }
            }) {
                Text(text = "Show Notification",
                    fontSize = 22.sp
                    )
            }
            
        }
    }
}
