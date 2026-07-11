package com.example.calculator789

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF90CAF9),
            secondary = Color(0xFFCE93D8),
            background = Color(0xFF1C1B1F),
            surface = Color(0xFF2D2D30),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
        ),
        content = content
    )
}

@Composable
fun CalculatorApp() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<String>()) }
    var showHistory by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Display area
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // History button
                if (history.isNotEmpty()) {
                    TextButton(
                        onClick = { showHistory = !showHistory },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            if (showHistory) "Скрыть историю" else "История",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // History
                if (showHistory && history.isNotEmpty()) {
                    Column(
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        history.reversed().forEach { item ->
                            Text(
                                text = item,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontSize = 14.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // Expression
                Text(
                    text = expression.ifEmpty { "0" },
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Result
                Text(
                    text = result,
                    color = if (result.startsWith("Ошибка")) Color(0xFFCF6679) else MaterialTheme.colorScheme.primary,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton("C", Color(0xFFCF6679), onClick = {
                    expression = ""
                    result = ""
                })
                CalculatorButton("(", Color(0xFF3D3D40), onClick = {
                    expression += "("
                })
                CalculatorButton(")", Color(0xFF3D3D40), onClick = {
                    expression += ")"
                })
                CalculatorButton("÷", Color(0xFF90CAF9), onClick = {
                    expression += "÷"
                })
            }

            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton("7", Color(0xFF3D3D40), onClick = {
                    expression += "7"
                })
                CalculatorButton("8", Color(0xFF3D3D40), onClick = {
                    expression += "8"
                })
                CalculatorButton("9", Color(0xFF3D3D40), onClick = {
                    expression += "9"
                })
                CalculatorButton("×", Color(0xFF90CAF9), onClick = {
                    expression += "×"
                })
            }

            // Row 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton("4", Color(0xFF3D3D40), onClick = {
                    expression += "4"
                })
                CalculatorButton("5", Color(0xFF3D3D40), onClick = {
                    expression += "5"
                })
                CalculatorButton("6", Color(0xFF3D3D40), onClick = {
                    expression += "6"
                })
                CalculatorButton("-", Color(0xFF90CAF9), onClick = {
                    expression += "-"
                })
            }

            // Row 4
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton("1", Color(0xFF3D3D40), onClick = {
                    expression += "1"
                })
                CalculatorButton("2", Color(0xFF3D3D40), onClick = {
                    expression += "2"
                })
                CalculatorButton("3", Color(0xFF3D3D40), onClick = {
                    expression += "3"
                })
                CalculatorButton("+", Color(0xFF90CAF9), onClick = {
                    expression += "+"
                })
            }

            // Row 5
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton("0", Color(0xFF3D3D40), onClick = {
                    expression += "0"
                }, modifier = Modifier.weight(2f))
                CalculatorButton(".", Color(0xFF3D3D40), onClick = {
                    if (!expression.contains(".")) {
                        expression += "."
                    }
                })
                CalculatorButton("\u232B", Color(0xFF3D3D40), onClick = {
                    if (expression.isNotEmpty()) {
                        expression = expression.dropLast(1)
                    }
                })
            }

            // Row 6 - Equals
            CalculatorButton("=", Color(0xFF90CAF9), onClick = {
                try {
                    if (expression.isNotEmpty()) {
                        val evalExpression = expression
                            .replace("÷", "/")
                            .replace("×", "*")
                        val evalResult = ExpressionBuilder(evalExpression).build().evaluate()
                        val formattedResult = if (evalResult == evalResult.roundToInt().toDouble()) {
                            evalResult.roundToInt().toString()
                        } else {
                            String.format("%.2f", evalResult)
                        }
                        result = "= $formattedResult"
                        history = history + "$expression = $formattedResult"
                    }
                }, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            fontSize = if (text == "\u232B") 24.sp else 20.sp,
            fontWeight = FontWeight.Medium,
            color = if (color == Color(0xFF90CAF9) || color == Color(0xFFCF6679)) Color.Black else Color.White
        )
    }
}