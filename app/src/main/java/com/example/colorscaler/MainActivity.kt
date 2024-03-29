package com.example.colorscaler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorscaler.ui.theme.ColorScalerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorScalerTheme {
                val viewModel = viewModel<MainViewModel>()
                HomeScreen(viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ColorScalerTheme {
        val viewModel = viewModel<MainViewModel>()
        HomeScreen(viewModel)
    }
}