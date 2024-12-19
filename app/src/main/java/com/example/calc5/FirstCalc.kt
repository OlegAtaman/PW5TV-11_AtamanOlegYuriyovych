package com.example.calc5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calc5.ui.theme.Calc5Theme

data class NetworkParams(
    val failureRate: Double,
    val restorationTime: Int,
    val idleCoefficient: Double,
    val capacity: Int
)

val networkData = mapOf(
    "Лінія 110 кВ" to NetworkParams(0.007, 10, 0.167, 35),
    "Лінія 35 кВ" to NetworkParams(0.02, 8, 0.167, 35),
    "Лінія 10 кВ" to NetworkParams(0.02, 10, 0.167, 35),
    "Кабель 10 кВ (траншея)" to NetworkParams(0.03, 44, 1.0, 9),
    "Кабель 10 кВ (канал)" to NetworkParams(0.005, 18, 1.0, 9),
    "Трансформатор 110 кВ" to NetworkParams(0.015, 100, 1.0, 43),
    "Трансформатор 35 кВ" to NetworkParams(0.02, 80, 1.0, 28),
    "Трансформатор 10 кВ (кабельна мережа)" to NetworkParams(0.005, 60, 0.5, 10),
    "Трансформатор 10 кВ (повітряна мережа)" to NetworkParams(0.05, 60, 0.5, 10),
    "Вимикач 110 кВ" to NetworkParams(0.01, 30, 0.1, 30),
    "Вимикач 10 кВ" to NetworkParams(0.02, 15, 0.33, 15),
    "Шини 10 кВ" to NetworkParams(0.03, 2, 0.33, 15),
    "Автомат 0.38 кВ" to NetworkParams(0.05, 20, 1.0, 15),
    "Двигун 6 кВ" to NetworkParams(0.1, 50, 0.5, 0),
    "Двигун 0.38 кВ" to NetworkParams(0.1, 50, 0.5, 0)
)

@Composable
fun FirstCalculator() {
    val userInputs = networkData.keys.associateWith { remember { mutableStateOf("0") } }
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        userInputs.forEach { (label, valueState) ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "$label:", modifier = Modifier.padding(end = 8.dp))
                TextField(
                    value = valueState.value,
                    onValueChange = { valueState.value = it },
                    modifier = Modifier.fillMaxWidth()
                )
                if (label == "Трансформатор 10 кВ (кабельна мережа)" || label == "Трансформатор 10 кВ (повітряна мережа)") {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Button(
            onClick = {
                resultText = computeMetrics(userInputs)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Розрахувати")
        }

        if (resultText.isNotEmpty()) {
            Text(
                text = resultText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

fun computeMetrics(inputs: Map<String, MutableState<String>>): String {
    var totalFailureRate = 0.0
    var weightedRestorationTime = 0.0
    var singleSystemIdle = 0.0
    var plannedIdleTime = 0.0
    var dualFailureRate = 0.0
    var totalMetric = 0.0

    inputs.forEach { (key, inputState) ->
        val quantity = inputState.value.toIntOrNull() ?: 0
        val params = networkData[key]
        if (quantity > 0 && params != null) {
            totalFailureRate += quantity * params.failureRate
            weightedRestorationTime += quantity * params.restorationTime * params.failureRate
        }
    }

    if (totalFailureRate > 0) {
        weightedRestorationTime /= totalFailureRate
    }
    singleSystemIdle = (weightedRestorationTime * totalFailureRate) / 8760
    plannedIdleTime = 1.2 * 43 / 8760
    dualFailureRate = 2 * totalFailureRate * (singleSystemIdle + plannedIdleTime)
    totalMetric = dualFailureRate + 0.02

    return """
        Загальна частота відмов: %.3f
        Середній час відновлення: %.3f
        Аварійний простій: %.10f
        Плановий простій: %.5f
        Відмова двох систем: %.5f
        Відмова двох систем із секційним вимикачем: %.5f
    """.trimIndent().format(
        totalFailureRate,
        weightedRestorationTime,
        singleSystemIdle,
        plannedIdleTime,
        dualFailureRate,
        totalMetric
    )
}