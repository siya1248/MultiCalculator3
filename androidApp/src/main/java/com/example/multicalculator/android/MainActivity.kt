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
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.multicalculator.Calculator
import kotlin.math.absoluteValue
import androidx.compose.runtime.saveable.rememberSaveable

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
    var leftNumber by rememberSaveable{ mutableStateOf(0) }
    var rightNumber by rememberSaveable{ mutableStateOf(0) }
    var operation by rememberSaveable{ mutableStateOf("") }
    var complete by rememberSaveable{ mutableStateOf(false) }
    val displayText = remember{mutableStateOf("0")}
    if(complete && operation.isNotEmpty()){
        var answer = 0
        answer = when (operation){
            "+" -> leftNumber + rightNumber
            "-" -> leftNumber - rightNumber
            "*" -> leftNumber * rightNumber
            "/" -> if (rightNumber != 0) leftNumber / rightNumber else 0
            else ->0
        }
        displayText = answer.toString()
    }
    else if(operation.isNotEmpty() && complete == false){
        displayText = rightNumber.toString()
    }
    else{
        displayText = leftNumber.toString()
    }
    fun numberPress(btnNum: Int){
        if(complete == true){
            leftNumber = 0
            rightNumber =0
            operation = ""
            complete = false
        }
        if(operation.isNotBlank() && complete != true){
            rightNumber = rightNumber * 10 + btnNum
        }
        else if (operation.isBlank() && complete != true){
            leftNumber = leftNumber * 10 + btnNum
        }
    }
    fun operationPress(op: String){
        if(complete == false){
            operation = op
        }
    }
    fun equalsPress(){
        complete = true
    }
        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.Top) {
            Column (verticalArrangement = Arrangement.SpaceBetween){
            CalcDisplay(displayText)
            CalcNumericButton(0, displayText)}
        }


        Row {
            Column {
                for (i in 7 downTo 1 step 3) {
                    CalcRow { numberPress(it) }
                }
                Column {
                    CalcOperationButton("+", displayText)
                    CalcOperationButton("-", displayText)
                    CalcOperationButton("*", displayText)
                }
            }
        }
        Row (verticalAlignment = Alignment.Bottom){
                Column{CalcNumericButton(0 , displayText)}
                Column{CalcEqualsButton(displayText)}
                Column{ CalcOperationButton("+", displayText)}
        }
}





@Composable
fun CalcRow(onPress: (number:Int) -> Unit, startNum: Int, numButtons: Int){
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
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit){
    Button( onClick = {onPress(number)},
        modifier = Modifier.padding(4.dp))
    {
        Text(text = number.toString())
    }

}

@Composable
fun CalcOperationButton(operation: String, onPress:(operation: String) -> Unit){
    Button(
        onClick = {
            onPress(operation)
        },
        modifier = Modifier.padding(4.dp)
    ){
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit){
Button(
    onClick = {
        onPress()
    },
    modifier = Modifier.padding(4.dp)
){
    Text(text = "=")
}
}

