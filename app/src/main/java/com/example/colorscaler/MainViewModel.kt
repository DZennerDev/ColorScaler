package com.example.colorscaler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.colorscaler.ui.theme.Black

class MainViewModel: ViewModel() {

    var topModelType by mutableStateOf("RGB")
        private set

    var topModelColor by mutableStateOf(Black)
        private set

    var topModelNames by mutableStateOf(listOf("Red","Green","Blue"))
        private set

    var topModelValues by mutableStateOf(listOf("0","0","0"))
        private set

    var topModelRanges by mutableStateOf(listOf("255","255","255"))
        private set

    var bottomModel by mutableStateOf(
        ColorModel(
            "HSV",
            Black,
            listOf("Hue","Saturation","Value"),
            listOf("0","0","0"),
            listOf("360","100","100")
        )
    )
        private set

    private fun convertListToFloats(list: List<String>, allowZero: Boolean): List<Float> {
        val floatList = if(allowZero) {
            listOf(0f,0f,0f).toMutableList()
        } else {
            listOf(1f,1f,1f).toMutableList()
        }

        for(i in list.indices) {
            if((list[i].isEmpty() && allowZero)) {
                floatList[i] = 0f
            } else if((list[i].isEmpty() && !allowZero) || (list[i].toFloat() <= 0 && !allowZero) ) {
                floatList[i] = 1f
            } else {
                floatList[i] = list[i].toFloat()
            }
        }

        return floatList
    }

    fun updateTopModel(index: Int, input: String, isValues: Boolean) {
        val valuesList = topModelValues.toMutableList()
        val rangesList = topModelRanges.toMutableList()

        if(isValues) {
            valuesList[index] = input
        } else {
            rangesList[index] = input
        }

        val floatValuesList = convertListToFloats(valuesList, true)
        val floatRangesList = convertListToFloats(rangesList, false)

        topModelColor = calculateColorRGB(
            floatValuesList[0],
            floatValuesList[1],
            floatValuesList[2],
            floatRangesList[0],
            floatRangesList[1],
            floatRangesList[2],
            )

        topModelValues = valuesList
        topModelRanges = rangesList
    }

    private fun calculateColorRGB(red: Float, green: Float, blue: Float, maxRed: Float, maxGreen: Float, maxBlue: Float): Color {

        val clampedRed = Math.min(red/maxRed,1f)
        val clampedGreen = Math.min(green/maxGreen,1f)
        val clampedBlue = Math.min(blue/maxBlue,1f)

        convertRGBtoHSV(clampedRed, clampedGreen, clampedBlue)

        return Color(clampedRed, clampedGreen, clampedBlue)
    }

    private fun convertRGBtoHSV(red: Float, green: Float, blue: Float) {
        val cmax = Math.max(Math.max(red,green),blue)
        val cmin = Math.min(Math.min(red,green),blue)
        val difference = cmax - cmin
        var hue = 0f

        when (cmax) {
            cmin -> hue = 0f
            red -> hue = (60f * ((green - blue) / difference) + 360f) % 360f
            green -> hue = (60f * ((blue - red) / difference) + 120f) % 360f
            blue -> hue = (60f * ((red - green) / difference) + 240f) % 360f
        }

        val saturation = if(cmax == 0f)
            0f
        else
            (difference / cmax) * 100f

        val value = cmax * 100f



        bottomModel = bottomModel.copy(
            color = Color(red,green,blue),
            parameterValues = listOf(
                hue.toString(),
                saturation.toString(),
                value.toString()
            )
        )
    }
}