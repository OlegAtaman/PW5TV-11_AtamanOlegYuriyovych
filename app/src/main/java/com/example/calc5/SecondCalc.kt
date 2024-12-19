package com.example.calc5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calc5.ui.theme.Calc5Theme

@Composable
fun SecondCalculator() {
    var value1 by remember { mutableStateOf("0.01") }
    var value2 by remember { mutableStateOf("0.045") }
    var value3 by remember { mutableStateOf("5120") }
    var value4 by remember { mutableStateOf("6451") }
    var value5 by remember { mutableStateOf("23.6") }
    var value6 by remember { mutableStateOf("17.6") }
    var value7 by remember { mutableStateOf("0.004") }

    var output by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Частота відмов:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value1,
            onValueChange = { value1 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Сер час відновл 35вт:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value2,
            onValueChange = { value2 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Потужність:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value3,
            onValueChange = { value3 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Час простою очік:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value4,
            onValueChange = { value4 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Збитки у разі аварійного перивання:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value5,
            onValueChange = { value5 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Збитики у разі запланованого:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value6,
            onValueChange = { value6 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Середній час план прост:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = value7,
            onValueChange = { value7 = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val input1 = value1.toDoubleOrNull() ?: 0.0
                val input2 = value2.toDoubleOrNull() ?: 0.0
                val input3 = value3.toDoubleOrNull() ?: 0.0
                val input4 = value4.toDoubleOrNull() ?: 0.0
                val input5 = value5.toDoubleOrNull() ?: 0.0
                val input6 = value6.toDoubleOrNull() ?: 0.0
                val input7 = value7.toDoubleOrNull() ?: 0.0

                val calc1 = input1 * input2 * input3 * input4
                val calc2 = input7 * input3 * input4
                val calc3 = input5 * calc1 + input6 * calc2

                output = """
                    Очік відсутність енергопостачання в надзв сит - ${"%.2f".format(calc1)}
                    Очік дефіцит енергії в запланован сит - ${"%.2f".format(calc2)}
                    Загальна очік вартівсть перерв - ${"%.2f".format(calc3)}
                """.trimIndent()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Обчилити")
        }

        if (output.isNotEmpty()) {
            Text(
                text = output,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondCalcPreview() {
    Calc5Theme {
        SecondCalculator()
    }
}
