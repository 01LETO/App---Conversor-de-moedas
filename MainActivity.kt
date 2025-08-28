package com.example.currencycalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF121212)) {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen() {
    var brlValue by remember { mutableStateOf(1_000_000.0) }
    val usdValue = 200_682.0
    var input by remember { mutableStateOf("") }

    val darkGray = Color(0xFF2D2D2D)
    val midGray = Color(0xFF4B4B4B)
    val orange = Color(0xFFFF9800)
    val bg = Color(0xFF121212)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(12.dp)
    ) {
        // Exibição dos valores
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            CurrencyRow(
                color = Color(0xFF00C853), // Verde BR
                code = "BRL",
                amount = formatNumber(brlValue)
            )
            Spacer(Modifier.height(12.dp))
            CurrencyRow(
                color = Color(0xFF1565C0), // Azul US
                code = "USD",
                amount = formatNumber(usdValue)
            )
        }

        // Teclado
        Keypad(
            onKeyPress = { key ->
                when (key) {
                    "C" -> input = ""
                    "⌫" -> if (input.isNotEmpty()) input = input.dropLast(1)
                    "=" -> {
                        input.toDoubleOrNull()?.let { brlValue = it }
                        input = ""
                    }
                    else -> input += key
                }
            },
            dark = midGray,
            accent = orange
        )

        // Rodapé
        Spacer(Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "11/03/2024 15:56",
                color = Color(0xFF00C78A),
                fontSize = 12.sp
            )
            Text(
                text = "1 BRL = 0,2 USD",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun CurrencyRow(color: Color, code: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(Modifier.width(8.dp))
            Text(text = code, color = Color.White, fontSize = 16.sp)
        }
        Text(
            text = amount,
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun Keypad(onKeyPress: (String) -> Unit, dark: Color, accent: Color) {
    val buttons = listOf(
        "C", "⌫", "⇅", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "−",
        "1", "2", "3", "+",
        "0", ",", "%", "="
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        userScrollEnabled = false
    ) {
        items(buttons) { label ->
            val isRightColumn = (buttons.indexOf(label) % 4 == 3)
            KeyButton(
                label = label,
                modifier = Modifier
                    .padding(6.dp)
                    .height(64.dp),
                background = if (isRightColumn) accent else dark,
                onClick = { onKeyPress(label) }
            )
        }
    }
}

@Composable
fun KeyButton(label: String, modifier: Modifier, background: Color, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

fun formatNumber(value: Double): String {
    val nf = NumberFormat.getInstance(Locale("pt", "BR"))
    nf.maximumFractionDigits = 3
    nf.minimumFractionDigits = 0
    return nf.format(value)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun PreviewCurrencyScreen() {
    CurrencyConverterScreen()
}
