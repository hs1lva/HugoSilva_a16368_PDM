package com.example.calculator

class CalculatorBrain {

    // Enum para representar as operações matemáticas
    enum class Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        PERCENT,
        NEGATE
    }

    private var operand: Double = 0.0
    private var operation: Operation? = null

    // Define o operando atual
    fun setOperand(value: Double) {
        operand = value
    }

    // Define a operação atual
    fun setOperation(op: Operation) {
        operation = op
    }

    // Função para realizar a operação atual
    fun doOperation(secondOperand: Double): Double {
        return when (operation) {
            Operation.ADD -> operand + secondOperand
            Operation.SUBTRACT -> operand - secondOperand
            Operation.MULTIPLY -> operand * secondOperand
            Operation.DIVIDE -> if (secondOperand != 0.0) operand / secondOperand else Double.NaN
            Operation.PERCENT -> operand / 100
            Operation.NEGATE -> -operand
            else -> secondOperand // No caso de nenhuma operação definida, retorna o segundo operando
        }.also {
            operand = it // Atualiza o operando após a operação
        }
    }

    // Função para limpar os dados da calculadora
    fun clear() {
        operand = 0.0
        operation = null
    }
}
