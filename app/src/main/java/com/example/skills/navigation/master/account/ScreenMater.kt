package com.example.skills.navigation.master.account

import com.example.skills.R

sealed class ScreenMater(val route: String, val iconId: Int) {
    object MasterHomeScreen : ScreenMater(route = "master_home_screen", iconId = R.drawable.home)

    object MasterChatsScreen : ScreenMater(route = "master_chat_screen", iconId = R.drawable.message_buble)

    object MasterCalendarScreen : ScreenMater(route = "master_calendar_screen", iconId = R.drawable.person)
    object MasterCreateServiceScreen : ScreenMater(route = "master_create_service_screen", iconId = R.drawable.plus)
    object MasterCreateServiceScreen : ScreenMater(route = "master_create_service_screen", iconId = R.drawable.plus)
    SetupMasterServiceNavGraph




}