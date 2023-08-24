package com.example.variablenotification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.variablenotification.ui.theme.VariableNotificationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var timerOperator: TimerOperator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(
                        this,
                        "通知：許可済",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "通知：未許可",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        setContent {
            VariableNotificationTheme {
                var seconds = remember {
                    mutableStateOf("")
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InputField(seconds, timerOperator, applicationContext)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(seconds: MutableState<String>, timerOperator: TimerOperator, context: Context, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row {
            Text(
                text = "秒数を\n入力してください",
                modifier = Modifier.weight(3f)
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = seconds.value,
                onValueChange = {seconds.value = it},
                modifier = Modifier.weight(3f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("通知")
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { startNotifyForegroundService(context) },
                modifier = Modifier.weight(2f)
            ) {
                Text("表示")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { stopNotifyForegroundService(context) },
                modifier = Modifier.weight(2f)
            ) {
                Text("削除")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("タイマー")
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    timerOperator.setTimer(seconds.value.toLongOrNull() ?: 0)
                    timerOperator.timerStart()
                },
                modifier = Modifier.weight(2f)
            ) {
                Text("開始")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { timerOperator.timerStop() },
                modifier = Modifier.weight(2f)
            ) {
                Text("停止")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// for preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(seconds: MutableState<String>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row {
            Text(
                text = "秒数を\n入力してください",
                modifier = Modifier.weight(3f)
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = seconds.value,
                onValueChange = {seconds.value = it},
                modifier = Modifier.weight(3f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("通知")
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { },
                modifier = Modifier.weight(2f)
            ) {
                Text("表示")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { },
                modifier = Modifier.weight(2f)
            ) {
                Text("削除")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("タイマー")
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { },
                modifier = Modifier.weight(2f)
            ) {
                Text("開始")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { },
                modifier = Modifier.weight(2f)
            ) {
                Text("停止")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    VariableNotificationTheme {
        var seconds = remember {
            mutableStateOf("")
        }
        InputField(seconds)
    }
}