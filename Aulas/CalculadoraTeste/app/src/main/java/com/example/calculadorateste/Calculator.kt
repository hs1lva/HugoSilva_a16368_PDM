package com.example.calculadorateste

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorApp() {
    var displayText by remember { mutableStateOf("0") }
    var number1 by remember { mutableStateOf(0.0) }
    var operation by remember { mutableStateOf("") }
    var isSecondNumber by remember { mutableStateOf(false) }

    val buttons = listOf(
        listOf("AC", "+/-", "%", "/"),
        listOf("7", "8", "9", "x"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "=")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display da calculadora
        Text(
            text = displayText,
            color = Color.White,
            fontSize = 72.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botões da calculadora
        buttons.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { symbol ->
                    val buttonModifier = if (symbol == "0") {
                        Modifier.weight(2f)
                    } else {
                        Modifier.weight(1f)
                    }

                    CalculatorButton(
                        symbol = symbol,
                        modifier = buttonModifier,
                        isOperator = symbol in listOf("/", "x", "-", "+", "="),
                        onClick = {
                            when (symbol) {
                                in "0".."9", "." -> {
                                    if (isSecondNumber && operation.isEmpty()) {
                                        displayText = ""
                                        isSecondNumber = false
                                    }
                                    if (displayText == "0") displayText = ""
                                    displayText += symbol
                                    if (!isSecondNumber) number1 = displayText.toDoubleOrNull() ?: 0.0
                                }
                                "/", "x", "-", "+" -> {
                                    operation = symbol
                                    displayText += " $symbol "
                                    isSecondNumber = true
                                }
                                "=" -> {
                                    val number2 = displayText.split(" ").last().toDoubleOrNull() ?: 0.0
                                    val result = calculateResult(number1, number2, operation)
                                    displayText = result
                                    number1 = result.toDoubleOrNull() ?: 0.0 // O resultado se torna o novo número1
                                    isSecondNumber = false // Reiniciar o cálculo
                                }
                                "AC" -> {
                                    displayText = "0"
                                    number1 = 0.0
                                    operation = ""
                                    isSecondNumber = false
                                }
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, modifier: Modifier = Modifier, isOperator: Boolean = false, onClick: () -> Unit) {
    val backgroundColor = when {
        symbol == "AC" -> Color(0xFFA5A5A5)
        isOperator -> Color(0xFFFF9500)
        else -> Color(0xFF333333)
    }

    val textColor = when {
        isOperator || symbol == "AC" -> Color.White
        else -> Color.White
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(0.5f)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor), // Substituído backgroundColor por containerColor
        // Mexer na estrutura do botão 0
        shape = CircleShape
    ) {
        Text(
            text = symbol,
            color = textColor,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun calculateResult(number1: Double, number2: Double, operation: String): String {
    return when (operation) {
        "+" -> (number1 + number2).toString()
        "-" -> (number1 - number2).toString()
        "x" -> (number1 * number2).toString()
        "/" -> if (number2 != 0.0) (number1 / number2).toString() else "Erro"
        else -> ""
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp() {
    CalculatorApp()
}