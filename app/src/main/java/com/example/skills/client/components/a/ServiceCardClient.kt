package com.example.skills.client.components.a

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skills.data.Master
import com.example.skills.master.components.b.calendar.clickable
import com.example.skills.master.components.d.CustomAlertDialog
import com.example.skills.master.components.d.SingleService
import com.example.skills.role.ScreenRole


@Composable
fun ServiceCardClient(singleService: SingleService, navController: NavHostController, master: Master) {
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    if (showDialog) {
        CustomAlertDialog(
            onDismiss = {
                showDialog = false
            },
            onExit = {
                showDialog = false
            },
            "Удалить услугу",
            "Услуга будет удалена навсегда без возможности восстановления"
        )
    }
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 4.dp, end = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .clickable {
                navController.navigate("${ScreenRole.Client.SelectDate.route}")
            },
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = singleService.name,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(paddingBetweenText))
            Text(
                text = singleService.price.toString() + " руб",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(paddingBetweenText))
            Text(
                text = singleService.duration.toString() + " мин",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(paddingBetweenText))
            Text(
                text = "Описание: " + singleService.description,
                color = Color.LightGray,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                lineHeight = 17.sp
            )
            if (!expanded) {
                Text(
                    text = "... ещё",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { expanded = !expanded })
            } else {
                Spacer(modifier = Modifier.height(paddingBetweenText))
                Text(
                    text = "Адрес: " + master.address,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

val paddingBetweenText = 7.dp