package com.example.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme

import com.example.calculator.ui.components.CalcButton // Import necessário para o botão personalizado
import com.example.calculator.ui.components.CalcButton0

@Composable
fun CalculatorView(modifier: Modifier = Modifier) {

    var display by remember { mutableStateOf("0") } // valor 0 para o display default
    var firstNumber by remember { mutableStateOf(0.0) } // num decimais
    var secondNumber by remember { mutableStateOf(0.0) } // num decimais
    var operation by remember { mutableStateOf("") } // string vazia para a operação

    // Função Calculate para processar a operação
    fun calculate() {
        when (operation) {
            "+" -> display = (firstNumber + secondNumber).toString() // toString para converter para string
            "-" -> display = (firstNumber - secondNumber).toString()
            "*" -> display = (firstNumber * secondNumber).toString()
            "/" -> display = if (secondNumber != 0.0) (firstNumber / secondNumber).toString() else "Erro: div por 0"
            // Secound number diferente de 0 por causa da divisão por 0
        }

        firstNumber = display.toDoubleOrNull() ?: 0.0 // string para um valor numerico do tipo double
        // define um valor padrão caso a parte à esquerda do operador seja null
        // assim o programa não realiza operações com valores inválidos
        operation = "" // Limpa a operação após o cálculo para a próxima operação
    }

    Column(modifier = modifier) {
        // Display da calculadora
        Text(
            text = display,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.End, // Alinhamento à direita
            style = MaterialTheme.typography.displayLarge
        )

        // Primeira linha de botões (AC, +-, %, /)
        Row {
            CalcButton(label = "AC", isOperation = true, onButtonPressed = { // isOperation= true para ser orange
                display = "0"
                firstNumber = 0.0
                secondNumber = 0.0
                operation = ""
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "+/-", isOperation = true, onButtonPressed = {
                // Implementação da lógica +/- (falta) !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                display = (display.toDouble() * -1).toString()
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "%", isOperation = true, onButtonPressed = {
                // Implementação da lógica de percentagem (falta) !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                display = (display.toDouble() / 100).toString()
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "/", isOperation = true, onButtonPressed = {
                operation = "/"
                firstNumber = display.toDoubleOrNull() ?: 0.0
                display = ""
            }, modifier = Modifier.weight(1f))
        }

        // Segunda linha de números (7, 8, 9, *)
        Row {
            listOf("7", "8", "9").forEach { num ->
                CalcButton(label = num, onButtonPressed = {
                    if (display == "0") display = num else display += num
                }, modifier = Modifier.weight(1f))
            }
            CalcButton(label = "*", isOperation = true, onButtonPressed = {
                operation = "*"
                firstNumber = display.toDoubleOrNull() ?: 0.0
                display = ""
            }, modifier = Modifier.weight(1f))
        }

        // Terceira linha de números (4, 5, 6, -)
        Row {
            listOf("4", "5", "6").forEach { num ->
                CalcButton(label = num, onButtonPressed = {
                    if (display == "0") display = num else display += num
                }, modifier = Modifier.weight(1f))
            }
            CalcButton(label = "-", isOperation = true, onButtonPressed = {
                operation = "-"
                firstNumber = display.toDoubleOrNull() ?: 0.0
                display = ""
            }, modifier = Modifier.weight(1f))
        }

        // Quarta linha de números (1, 2, 3, +)
        Row {
            listOf("1", "2", "3").forEach { num ->
                CalcButton(label = num, onButtonPressed = {
                    if (display == "0") display = num else display += num
                }, modifier = Modifier.weight(1f))
            }
            CalcButton(label = "+", isOperation = true, onButtonPressed = {
                operation = "+"
                firstNumber = display.toDoubleOrNull() ?: 0.0
                display = ""
            }, modifier = Modifier.weight(1f))
        }

        // Quinta linha (0, . , =)
        Row {
            CalcButton0(
                label = "0",
                onButtonPressed = {
                    if (display == "0") display = "0" else display += "0" // evita 00
                },
                modifier = Modifier
                    .weight(2f) // Botão "0" ocupa 2/4 da linha
                    .fillMaxHeight() // O botão "0" agora irá preencher a altura da linha
            )

            CalcButton(label = ".", onButtonPressed = {
                if (!display.contains(".")) display += "."
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "=", isOperation = true, onButtonPressed = {
                secondNumber = display.toDoubleOrNull() ?: 0.0
                calculate()
            }, modifier = Modifier.weight(1f))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorView()
    }
}