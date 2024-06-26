package com.example.skills.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.skills.ui.theme.paddingBetweenElements

@Composable
fun CustomButton(
    navigateTo: () -> Unit,
    buttonText: String,
    height: Dp = buttonHeight,
    heightCoeff: Float = 1f,
    width: Float = 1f,
    color: Color = Color.Black,
    enabled: Boolean = true,
) {
    Button(
        onClick = {
            navigateTo.invoke()
        },
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height * heightCoeff)
            .padding(start = paddingBetweenElements, end = paddingBetweenElements),
        shape = RoundedCornerShape(buttonRoundedCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = if (color == Color.Black) Color.White else Color.Black,
            disabledContainerColor = Color.LightGray ,
            disabledContentColor = Color.White
        ),
        enabled = enabled,
        border = if(enabled)BorderStroke(1.dp, Color.Black) else BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(text = buttonText)
    }
}


var buttonHeight = 60.dp
val buttonRoundedCorner = 16.dp
