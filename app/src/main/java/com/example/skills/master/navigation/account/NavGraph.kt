package com.example.skills.master.navigation.account

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skills.client.account.CalendarScreen
import com.example.skills.master.MainMasterScreen
import com.example.skills.master.components.GoogleCalendarScreen
import com.example.skills.master.components.MasterClientServicesScreen
import com.example.skills.master.components.MasterMyServicesScreen
import com.example.skills.master.components.MasterSettingsScreen
import com.example.skills.master.creatingService.MainCreationLayout
import com.example.skills.role.ScreenRole

@Composable
fun SetupMasterNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenMaster.MasterHomeScreen.route
    ) {
        // person
        composable(route = ScreenMaster.MasterHomeScreen.route) {
            MainMasterScreen()
        }
        //calendar
        composable(route = ScreenMaster.MasterCalendarScreen.route) {
            CalendarScreen()
        }
        // done checkbox
        composable(route = ScreenMaster.MasterCreateServiceScreen.route) {
            MasterClientServicesScreen()
        }
        // server
        composable(route = ScreenMaster.MasterServerScreen.route) {
            MasterMyServicesScreen()
        }

        // all layout, setup navigation
        composable(route = ScreenMaster.MainCreationLayout.route) {
            MainCreationLayout()
        }

        // settings
        composable(route = ScreenMaster.MasterSettingsScreen.route) {
            MasterSettingsScreen(
                navigateToEditAccount = {
                    navHostController.navigate(ScreenRole.Master.FullProfile.route)
                },
                navigateToEditPassword = {
                    navHostController.navigate(ScreenRole.Master.CreateNewPassword.route)
                },
                navigateToCalendar = {
                    navHostController.navigate(ScreenRole.Master.GoogleCalendar.route)
                },
                navigateToNotifications = {
                    //  navController.navigate(ScreenRole.Master.Registration.route)
                },
                exit = {
                    navHostController.navigate(ScreenRole.Master.LogIn.route)
                }
            )
        }

        composable(ScreenRole.Master.GoogleCalendar.route) {
            GoogleCalendarScreen(
                navController = navHostController,
                navigateToMain = {
                    navHostController.navigate(ScreenRole.Master.MainLayout.route)
                }
            )
        }
    }
}