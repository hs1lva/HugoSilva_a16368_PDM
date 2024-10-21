package com.example.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.components.CalcButton
import com.example.calculator.ui.components.CalcButton0

@Composable
fun CalculatorView(modifier: Modifier = Modifier) {

    var display by remember { mutableStateOf("0") } // Mostrador da calculadora
    val calculatorBrain = remember { CalculatorBrain() } // Instância do CalculatorBrain
    var isOperatorPressed by remember { mutableStateOf(false) } // Controla se o operador foi pressionado

    /**
     * Lida com os dígitos pressionados e atualiza o display.
     */
    fun onDigitPressed(digit: String) {
        if (isOperatorPressed) {
            display = digit // Começa um novo número se uma operação foi selecionada
            isOperatorPressed = false
        } else {
            display = if (display == "0") digit else display + digit
        }
    }

    /**
     * Lida com a operação e define o primeiro operando.
     */
    fun onOperationPressed(operation: CalculatorBrain.Operation) {
        calculatorBrain.setOperand(display.toDoubleOrNull() ?: 0.0) // Define o primeiro operando
        calculatorBrain.setOperation(operation)
        isOperatorPressed = true
    }

    /**
     * Executa o cálculo quando "=" é pressionado.
     */
    fun calculate() {
        val secondOperand = display.toDoubleOrNull() ?: 0.0
        display = calculatorBrain.doOperation(secondOperand).toString()
        isOperatorPressed = true
    }

    /**
     * Função para limpar a calculadora.
     */
    fun clearCalculator() {
        display = "0"
        calculatorBrain.clear()
        isOperatorPressed = false
    }

    Column(modifier = modifier) {
        // Display da calculadora
        Text(
            text = display,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.displayLarge
        )

        // Primeira linha de botões (AC, +-, %, /)
        Row {
            CalcButton(label = "AC", isOperation = true, onButtonPressed = {
                clearCalculator()
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "+/-", isOperation = true, onButtonPressed = {
                calculatorBrain.setOperand(display.toDoubleOrNull() ?: 0.0)
                onOperationPressed(CalculatorBrain.Operation.NEGATE)
                display = calculatorBrain.doOperation(0.0).toString() // Aplica a negação no display
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "%", isOperation = true, onButtonPressed = {
                onOperationPressed(CalculatorBrain.Operation.PERCENT)
                calculate()
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "/", isOperation = true, onButtonPressed = {
                onOperationPressed(CalculatorBrain.Operation.DIVIDE)
            }, modifier = Modifier.weight(1f))
        }

        // Segunda linha de números (7, 8, 9, *)
        Row {
            listOf("7", "8", "9").forEach { num ->
                CalcButton(label = num, onButtonPressed = { onDigitPressed(num) }, modifier = Modifier.weight(1f))
            }
            CalcButton(label = "*", isOperation = true, onButtonPressed = {
                onOperationPressed(CalculatorBrain.Operation.MULTIPLY)
            }, modifier = Modifier.weight(1f))
        }

        // Terceira linha de números (4, 5, 6, -)
        Row {
            listOf("4", "5", "6").forEach { num ->
                CalcButton(label = num, onButtonPressed = { onDigitPressed(num) }, modifier = Modifier.weight(1f))
            }
            CalcButton(label = "-", isOperation = true, onButtonPressed = {
                onOperationPressed(CalculatorBrain.Operation.SUBTRACT)
            }, modifier = Modifier.weight(1f))
        }

        // Quarta linha de números (1, 2, 3, +)
        Row {
            listOf("1", "2", "3").forEach { num ->
                CalcButton(label = num, onButtonPressed = { onDigitPressed(num) }, modifier = Modifier.weight(1f))
            }
            CalcButton(label = "+", isOperation = true, onButtonPressed = {
                onOperationPressed(CalculatorBrain.Operation.ADD)
            }, modifier = Modifier.weight(1f))
        }

        // Quinta linha (0, ., =)
        Row {
            CalcButton0(
                label = "0",
                onButtonPressed = { onDigitPressed("0") },
                modifier = Modifier.weight(2f)
            )

            CalcButton(label = ".", onButtonPressed = {
                if (!display.contains(".")) {
                    display += "."
                }
            }, modifier = Modifier.weight(1f))

            CalcButton(label = "=", isOperation = true, onButtonPressed = {
                calculate()
            }, modifier = Modifier.weight(1f))
        }
    }
}