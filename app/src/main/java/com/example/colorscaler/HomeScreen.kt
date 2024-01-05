package com.example.colorscaler

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colorscaler.ui.theme.Black
import com.example.colorscaler.ui.theme.DarkGray
import com.example.colorscaler.ui.theme.DarkestGray
import com.example.colorscaler.ui.theme.Gray
import com.example.colorscaler.ui.theme.White

@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    Box(modifier = Modifier
        .background(White)
        .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            ColorModelCard(
                viewModel,
                true
            )
            ColorModelCard(
                viewModel,
                false
            )
        }
    }
}

@Composable
fun ColorModelCard(
    viewModel: MainViewModel,
    isTopModel: Boolean
) {
    val color = if(isTopModel) {viewModel.topModelColor} else {viewModel.bottomModel.color}

    Card(
        shape = RoundedCornerShape(2.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 2.dp, end = 2.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color)
                    .align(Alignment.Start)
                    .padding(50.dp)
            ) {}

            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .align(Alignment.End)
            ) {
                Column(
                    modifier = Modifier
                    .height(IntrinsicSize.Min)
                ) {
                    ColorParameterHeaderRow(
                        viewModel,
                        isTopModel,
                        valuesTitle = "Values",
                        rangesTitle = "Ranges",
                        modifier = Modifier
                    )
                    if(isTopModel) {
                        for(index in viewModel.topModelNames.indices) {
                            ColorParameterRowEditable(
                                viewModel,
                                index,
                                Modifier)
                        }
                    } else {
                        for(index in viewModel.topModelNames.indices) {
                            ColorParameterRow(
                                viewModel,
                                index,
                                Modifier)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ColorParameterHeaderRow(
    viewModel: MainViewModel,
    isTopModel: Boolean,
    valuesTitle: String,
    rangesTitle: String,
    modifier: Modifier
) {
    val colorModelTitle = if(isTopModel) {viewModel.topModelType} else {viewModel.bottomModel.type}

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            text = colorModelTitle,
            modifier = Modifier
                .background(DarkGray)
                .align(Alignment.Top)
                .weight(1f)
                .padding(5.dp)
        )
        Text(
            text = valuesTitle,
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            modifier = Modifier
                .background(DarkGray)
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(5.dp)
        )
        Text(
            text = rangesTitle,
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            modifier = Modifier
                .background(DarkGray)
                .padding(5.dp)
                .weight(1f)
        )
    }
}

@Composable
fun ColorParameterRowEditable(
    viewModel: MainViewModel,
    parameterIndex: Int,
    modifier: Modifier
) {
    val parameterName = viewModel.topModelNames[parameterIndex]
    val parameterValue = viewModel.topModelValues[parameterIndex]
    val parameterRange = viewModel.topModelRanges[parameterIndex]

    val pattern = remember { Regex("^\\d+\$") }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            text = parameterName,
            modifier = Modifier
                .background(DarkGray)
                .weight(1f)
                .padding(5.dp)
        )
        BasicTextField(
            value = parameterValue,
            onValueChange = { newValue ->
                if(newValue.isEmpty() || newValue.matches(pattern)) {
                    viewModel.updateTopModel(parameterIndex, newValue, true)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = White,
            ),
            singleLine = true,
            modifier = Modifier
                .background(Gray)
                .weight(1f)
                .padding(5.dp)
        )

        BasicTextField(
            value = parameterRange,
            onValueChange = { newValue ->
                if(newValue.isEmpty() || newValue.matches(pattern)) {
                    viewModel.updateTopModel(parameterIndex, newValue, false)
                }
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = White,
            ),
            singleLine = true,
            modifier = Modifier
                .background(DarkGray)
                .weight(1f)
                .padding(5.dp)
        )
    }
}

@Composable
fun ColorParameterRow(
    viewModel: MainViewModel,
    parameterIndex: Int,
    modifier: Modifier
) {
    val parameterName = viewModel.bottomModel.parameterNames[parameterIndex]
    val parameterValue = viewModel.bottomModel.parameterValues[parameterIndex]
    val parameterRange = viewModel.bottomModel.parameterRanges[parameterIndex]

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            text = parameterName,
            modifier = Modifier
                .background(DarkestGray)
                .weight(1f)
                .padding(5.dp)
        )
        Text(
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            text = parameterValue,
            modifier = Modifier
                .background(DarkGray)
                .weight(1f)
                .padding(5.dp)
        )

        Text(
            maxLines = 1,
            fontSize = 16.sp,
            color = White,
            text = parameterRange,
            modifier = Modifier
                .background(DarkestGray)
                .weight(1f)
                .padding(5.dp)
        )
    }
}