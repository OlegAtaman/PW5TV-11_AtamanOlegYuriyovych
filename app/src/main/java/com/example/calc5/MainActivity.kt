package com.example.calc5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calc5.ui.theme.Calc5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calc5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NavigationTabs(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationTabs(modifier: Modifier = Modifier) {
    var activeTabIndex by remember { mutableStateOf(0) }

    val tabTitles = listOf("Page 1", "Page 2")

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = activeTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = activeTabIndex == index,
                    onClick = { activeTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (activeTabIndex) {
            0 -> FirstCalculator()
            1 -> SecondCalculator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Calc5Theme {
        NavigationTabs()
    }
}

