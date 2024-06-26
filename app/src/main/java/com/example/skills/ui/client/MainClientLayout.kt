package com.example.skills.ui.client

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.skills.data.viewmodel.MainViewModel
import com.example.skills.navigation.ScreenRole
import com.example.skills.navigation.nav_bar.client.ClientBottomNavigation
import com.example.skills.navigation.nav_bar.client.SetupClientNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainClientLayout(navController: NavHostController, mainViewmodel: MainViewModel) {
    val hideList = setOf(
        ScreenRole.Client.EditProfile.route,
        ScreenRole.Client.Notifications.route,
        ScreenRole.Client.QRCodeScanner.route,
        ScreenRole.Client.PasswordSettings.route
    )

    val screen = navController.currentBackStackEntryAsState().value
    val showBottomBar = screen?.destination?.route !in hideList

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                ClientBottomNavigation(navController = navController)
            }
        }) {
        SetupClientNavGraph(navHostController = navController, mainViewModel = mainViewmodel)
    }
}