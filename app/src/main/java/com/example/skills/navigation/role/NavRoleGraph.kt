package com.example.skills.navigation.role

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skills.client.registration.LogInClientScreen
import com.example.skills.master.registration.LogInMasterScreen
import com.example.skills.navigation.client.registration.ClientScreen
import com.example.skills.navigation.master.registration.MasterScreen

@Composable
fun SetupRoleNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = ScreenRole.RoleLayout.route) {
        composable(route = ScreenRole.MasterLogIn.route) {
            LogInMasterScreen(navController = navHostController)
        }
        composable(route = ScreenRole.ClientLogIn.route) {
            LogInClientScreen(navController = navHostController)
        }
        composable(route = ScreenRole.RoleLayout.route) {
            ChooseRoleScreen(navController = navHostController)
        }
    }
}