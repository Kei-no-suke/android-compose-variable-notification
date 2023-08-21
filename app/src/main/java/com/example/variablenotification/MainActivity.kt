package com.example.variablenotification

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.variablenotification.ui.theme.VariableNotificationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var timerOperator: TimerOperator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            .padding(10.dp)
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
        Row {
            Button(
                onClick = {
                    timerOperator.setTimer(seconds.value.toLongOrNull() ?: 0)
                    timerOperator.timerStart()
                }
            ) {
                Text("確定")
            }
            Button(
                onClick = {
                    startNotifyForegroundService(context)
                }
            ) {
                Text("通知表示")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(seconds: MutableState<String>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
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
        Row {
            Button(
                onClick = { }
            ) {
                Text("確定")
            }
            Button(
                onClick = { }
            ) {
                Text("通知表示")
            }
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