package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val calculatorViewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        setContent {
            CalculatorAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = calculatorViewModel
                    )
                }
            }
        }
    }
}

val buttonsList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "+",
    "1", "2", "3", "-",
    "AC", "0", ".", "="
)

@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {
    val operation = viewModel.operation.observeAsState()
    val result = viewModel.result.observeAsState()

    Box(modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = operation.value!!,
                style = TextStyle(
                    textAlign = TextAlign.End,
                    fontSize = 30.sp
                )

            )

            Spacer(modifier.weight(1f))

            Text(
                text = result.value!!,
                style = TextStyle(
                    textAlign = TextAlign.End,
                    fontSize = 60.sp
                )
            )

            Spacer(Modifier.height(10.dp))

            LazyVerticalGrid(GridCells.Fixed(4)) {
                items(buttonsList){
                    CalculatorButton(it, onClick = { viewModel.onButtonClick(it) })
                }
            }


        }
    }
}

@Composable
fun CalculatorButton(button: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(80.dp),
            containerColor = buttonColors(button),
            contentColor = if(button == "=") Color.Black else Color.White
            ) {
            Text(button, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Composable
fun buttonColors(button: String): Color{
    return when(button){
        "=" -> Color(0xFF2596BE)
        "/", "*", "+", "-", "C", "(", ")" -> Color(0xff393d3c)
        else -> Color(0xff303332)
    }
}

