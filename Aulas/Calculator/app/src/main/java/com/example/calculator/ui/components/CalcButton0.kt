package com.example.calculator.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.Orange
import com.example.calculator.ui.theme.Pink40

@Composable
fun CalcButton0(
    modifier: Modifier = Modifier,
    label: String = "",
    isOperation: Boolean = false,
    onButtonPressed: (String) -> Unit
) {
    Button(
        modifier = modifier
            .padding(4.dp), // Removido o aspectRatio para permitir que o weight funcione corretamente
        colors = ButtonDefaults.run { buttonColors(if (isOperation) Orange else Pink40) },
        onClick = {
            onButtonPressed(label)
        }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CalcButton0Preview() {
    CalculatorTheme {
        CalcButton(label = "0") {
        }
    }
}
