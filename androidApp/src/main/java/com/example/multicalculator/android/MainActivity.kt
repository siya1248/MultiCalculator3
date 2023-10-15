package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import com.example.multicalculator.Calculator
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CalcView()

            }
        }
    }
}
@Preview
@Composable
fun CalcView(){
    val displayText = remember{mutableStateOf("0")}
    Column(
        modifier = Modifier
            .background(Color.LightGray),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row{
            CalcDisplay(displayText)
            CalcNumericButton(0 , displayText)
        }
        Row{
            Column {
                for(i in 7 downTo 1 step 3){
                    CalcRow(displayText, i, 3)
                }
                Row {
                    CalcNumericButton(0 , displayText)
                    CalcEqualsButton(displayText)
                }
            }
            Column {
                CalcOperationButton("/", displayText)
                CalcOperationButton("*", displayText)
                CalcOperationButton("-", displayText)
                CalcOperationButton("+", displayText)
            }
        }
    }
}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int){
    val endNum = startNum + numButtons
    Row(modifier = Modifier.padding(0.dp)){
        for(i in startNum downTo endNum - 1){
            CalcNumericButton(i, display)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>){

        Text(text = display.value,
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
                .padding(5.dp))

}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>){
    Button( onClick = {display.value += number.toString()},
        modifier = Modifier.padding(4.dp))
    {
        Text(text = number.toString())
    }

}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>){
    Button(
        onClick = {
            display.value += operation
        },
        modifier = Modifier.padding(4.dp)
    ){
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>){
Button(
    onClick = {
        display.value = "0"
    },
    modifier = Modifier.padding(4.dp)
){
    Text(text = "=")
}
}

