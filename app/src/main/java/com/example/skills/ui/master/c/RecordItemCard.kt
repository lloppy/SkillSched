package com.example.skills.ui.master.c

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.skills.R
import com.example.skills.data.models.RecordItem
import com.example.skills.data.models.RecordStatus
import com.example.skills.data.viewmodel.EditBookingViewModel
import com.example.skills.data.viewmodel.MyRepository.getMaster
import com.example.skills.data.viewmodel.MyRepository.getService
import com.example.skills.general.ScreenRole
import com.example.skills.ui.master.d.CustomAlertDialog
import java.time.ZoneId
import java.time.ZonedDateTime


@SuppressLint("DefaultLocale")
@Composable
fun RecordItemCard(
    recordItem: RecordItem,
    isClient: Boolean = false,
    // возможны нул значения для мастера, для клиента всегда не нул
    navController: NavHostController? = null,
    editBookingViewModel: EditBookingViewModel? = null
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var showCalendarDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        CustomAlertDialog(
            onDismiss = {
                showDialog = false
            },
            onExit = {
                showDialog = false
            },
            "Отменить запись",
            "Запись будет отменена, мы уведомим об этом " + if (isClient) "мастера" else "клиента"
        )
    }

    if (showCalendarDialog) {
        CustomAlertDialog(
            onDismiss = {
                showCalendarDialog = false
            },
            onExit = {
                showCalendarDialog = false
                openGoogleCalendar(context, recordItem)
            },
            "Google Календарь",
            "Добавить событие в Google Календарь"
        )
    }

    Column(
        modifier = Modifier
            .padding(bottom = 10.dp, top = 10.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .height(
                164.dp
                    .plus(if (recordItem.recordStatus == RecordStatus.ARCHIVE) 24.dp else 0.dp)
                    .minus(if (isClient && recordItem.comment == null) 24.dp else 0.dp)
            )
            .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 15.dp)) {
            Spacer(modifier = Modifier.height(paddingBetweenText.plus(paddingBetweenText)))
            val timeEnd = recordItem.timeFrom.plusMinutes(recordItem.duration.toLong())
            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    if (recordItem.recordStatus == RecordStatus.ARCHIVE) {
                        if (recordItem.isDone!!) {
                            BadgeCard("Выполнена", Color(41, 174, 41))
                        } else {
                            BadgeCard("Отменена", Color(236, 19, 19))
                        }
                    }
                    Text(
                        text = "${String.format("%02d", recordItem.timeFrom.hour)}:${
                            String.format("%02d", recordItem.timeFrom.minute)
                        } - ${String.format("%02d", timeEnd.hour)}:${
                            String.format("%02d", timeEnd.minute)
                        }",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(paddingBetweenText))
                    Text(
                        text = recordItem.serviceName,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .align(Alignment.TopEnd)
                ) {
                    if (recordItem.recordStatus == RecordStatus.ACTUAL) {
                        IconButton(onClick = { showCalendarDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "add to calendar",
                                tint = Color.Unspecified
                            )
                        }
                    }
                    IconButton(onClick = {
                        if (isClient) {
                            try {
                                editBookingViewModel!!.data1 =
                                    MutableLiveData(getMaster(recordItem.masterId))
                                editBookingViewModel.data2 =
                                    MutableLiveData(getService(recordItem.serviceId))

                                navController!!.navigate(ScreenRole.Client.EditDate.route)
                            } catch (e: NullPointerException) {
                            }
                        }
                    }) {
                        val iconResId = when {
                            isClient && recordItem.recordStatus == RecordStatus.ACTUAL -> R.drawable.edit
                            !isClient -> R.drawable.phone_circle
                            else -> null
                        }

                        if (iconResId != null) {
                            val painter = painterResource(id = iconResId)

                            Icon(
                                painter = painter,
                                contentDescription = "edit/phone",
                                tint = Color.Unspecified
                            )
                        }
                    }
                    if (recordItem.recordStatus == RecordStatus.ACTUAL) {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_circle),
                                contentDescription = "close",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(paddingBetweenText))
            Text(
                text = recordItem.price.toString() + " руб",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                maxLines = 1
            )

            if (!(isClient && recordItem.comment == null)) {
                Spacer(modifier = Modifier.height(paddingBetweenText))
                Text(
                    text = if (!isClient) "Клиент: ${recordItem.clientName} ${recordItem.clientAge} лет  " else "Коментарий: ${recordItem.comment}",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    maxLines = 3,
                    lineHeight = lineHeight
                )
            }
        }
    }
}


@Composable
fun BadgeCard(text: String, color: Color) {
    Box(
        modifier = Modifier
            .height(28.dp)
            .offset(x = -10.dp)
            .background(color.copy(0.1f), shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}


val paddingBetweenText = 9.dp
var lineHeight = 18.sp
var spacerValue = 14.dp
var instructionTextSize = 14.sp


fun openGoogleCalendar(context: Context, recordItem: RecordItem) {
    val zonedDateTime: ZonedDateTime =
        recordItem.timeFrom.atZone(ZoneId.systemDefault())

    val startMillis: Long = zonedDateTime.toInstant().toEpochMilli()
    val endMillis: Long =
        startMillis + recordItem.duration * 60 * 1000

    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(
            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            startMillis
        )
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
        .putExtra(CalendarContract.Events.TITLE, recordItem.serviceName)
        .putExtra(
            CalendarContract.Events.DESCRIPTION,
            recordItem.comment
        )
//        .putExtra(
//            CalendarContract.Events.EVENT_LOCATION,
//            recordItem.masterId
//        )
        .putExtra(
            CalendarContract.Events.AVAILABILITY,
            CalendarContract.Events.AVAILABILITY_BUSY
        )
        .putExtra(CalendarContract.Events.HAS_ALARM, 1)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            context,
            "Пожалуйста, установите Google Календарь",
            Toast.LENGTH_SHORT
        ).show()
    }
}