package com.example.skills.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skills.client.registration.LogInScreen

@Composable
fun SetupNavGraph(
    navHastController: NavHostController
) {
    NavHost(navController = navHastController, startDestination = Screen.LogIn.route) {
        composable(
            route = Screen.LogIn.route,
        ){
            LogInScreen()
        }
    }
}