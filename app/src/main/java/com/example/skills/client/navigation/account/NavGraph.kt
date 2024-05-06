package com.example.skills.client.navigation.account

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skills.data.viewmodel.BookingViewModel
import com.example.skills.client.components.a.ClientMastersScreen
import com.example.skills.client.components.a.ConfirmClientBookingScreen
import com.example.skills.client.components.a.DoneClientBookingScreen
import com.example.skills.data.viewmodel.EditBookingViewModel
import com.example.skills.client.components.a.EditConfirmClientBookingScreen
import com.example.skills.client.components.a.EditDateScreen
import com.example.skills.client.components.a.MasterServicesScreen
import com.example.skills.client.components.a.SelectDateScreen
import com.example.skills.client.components.a.ViewMasterScreen
import com.example.skills.client.components.a.calendar.EditTimeScreen
import com.example.skills.client.components.a.calendar.SelectTimeScreen
import com.example.skills.client.components.b.ClientBookingsScreen
import com.example.skills.client.components.c.EditClientProfileScreen
import com.example.skills.data.viewmodel.MainViewModel
import com.example.skills.master.components.e.EditPasswordScreen
import com.example.skills.master.components.e.GoogleCalendarScreen
import com.example.skills.master.components.e.MasterSettingsScreen
import com.example.skills.master.components.e.NotificationSettingsScreen
import com.example.skills.role.ScreenRole

@Composable
fun SetupClientNavGraph(
    navHostController: NavHostController,
) {
    val mainViewModel = MainViewModel()

    // BookingViewModel
    val bookingViewModel: BookingViewModel = viewModel()
    val editBookingViewModel: EditBookingViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = ScreenClient.ClientBookingsScreen.route
    ) {
        // done checkbox
        composable(route = ScreenClient.ClientBookingsScreen.route) {
            ClientBookingsScreen(navController = navHostController, editBookingViewModel)
        }

        // settings
        composable(route = ScreenClient.ClientSettingsScreen.route) {
            // reused component
            MasterSettingsScreen(
                navigateToEditAccount = {
                    // тут потом быть осторожнее, перепроверить что все норм
                    navHostController.navigate(ScreenRole.Master.EditProfile.route)
                },
                navigateToEditPassword = {
                    navHostController.navigate(ScreenRole.Client.PasswordSettings.route)
                },
                navigateToCalendar = {
                    navHostController.navigate(ScreenRole.Client.GoogleCalendar.route)
                },
                navigateToNotifications = {
                    navHostController.navigate(ScreenRole.Client.Notifications.route)
                },
                exit = {
                    // крашится, потому что другой роут, нужно потом полностью закрывать приложение
                    navHostController.navigate(ScreenRole.Client.LogIn.route)
                }
            )
        }

        composable(ScreenRole.Client.GoogleCalendar.route) {
            GoogleCalendarScreen(
                navController = navHostController,
                // no navigateToMain
                navigateToMain = {
                    navHostController.navigate(ScreenClient.ClientBookingsScreen.route)
                }
            )
        }

        // выбираем мастера из списка мастеров
        composable(route = ScreenClient.ClientMastersScreen.route) {
            ClientMastersScreen(
                bookingViewModel = bookingViewModel,
                navigateToSelectedMasterProfile = {
                    navHostController.navigate(ScreenRole.Client.ViewMaster.route)
                }
            )
        }

        // нажимаем на кнопку записаться в профиле выбранного мастера
        composable(route = ScreenRole.Client.ViewMaster.route) {
            ViewMasterScreen(
                bookingViewModel,
                navHostController,
                navigateToServices = {
                    navHostController.navigate(ScreenRole.Client.ViewMasterServices.route)
                }
            )
        }

        // экран с категориями и карточками Services
        composable(route = ScreenRole.Client.ViewMasterServices.route) {
            MasterServicesScreen(
                bookingViewModel = bookingViewModel,
                navController = navHostController,
                navigateToSelectDate = {
                    navHostController.navigate(ScreenRole.Client.SelectDate.route)
                }
            )
        }

        // экран с календарем
        composable(route = ScreenRole.Client.SelectDate.route) {
            SelectDateScreen(
                bookingViewModel = bookingViewModel,
                navController = navHostController,
                navigateToSelectTime = {
                    navHostController.navigate(ScreenRole.Client.SelectTime.route)
                }
            )
        }

        // экран с календарем
        composable(route = ScreenRole.Client.EditDate.route) {
            EditDateScreen(
                editBookingViewModel = editBookingViewModel,
                navController = navHostController,
                navigateToSelectTime = {
                    navHostController.navigate(ScreenRole.Client.EditTime.route)
                }
            )
        }

        // экран с кружочками временных слотов
        composable(route = ScreenRole.Client.SelectTime.route) { backStackEntry ->
            SelectTimeScreen(
                bookingViewModel = bookingViewModel,
                navController = navHostController,
                navigateToConfirmBooking = {
                    navHostController.navigate(ScreenRole.Client.ConfirmClientBooking.route)
                }
            )
        }

        // экран с кружочками временных слотов
        composable(route = ScreenRole.Client.EditTime.route) { backStackEntry ->
            EditTimeScreen(
                editBookingViewModel = editBookingViewModel,
                navController = navHostController,
                navigateToConfirmBooking = {
                    navHostController.navigate(ScreenRole.Client.ConfirmClientBooking.route)
                }
            )
        }

        // экран подтверждения и оставления комментария
        composable(ScreenRole.Client.ConfirmClientBooking.route) {
            ConfirmClientBookingScreen(
                navController = navHostController,
                bookingViewModel = bookingViewModel,
                navigateToDoneBooking = {
                    navHostController.navigate(ScreenRole.Client.DoneClientBooking.route)
                }
            )
        }

        // экран подтверждения и оставления комментария
        composable(ScreenRole.Client.EditConfirmClientBooking.route) {
            EditConfirmClientBookingScreen(
                navController = navHostController,
                editBookingViewModel = editBookingViewModel,
                navigateToDoneBooking = {
                    navHostController.navigate(ScreenRole.Client.DoneClientBooking.route)
                }
            )
        }

        // экран успешной регистрации
        composable(ScreenRole.Client.DoneClientBooking.route) {
            DoneClientBookingScreen(
                navigateToBookings = {
                    navHostController.navigate(ScreenClient.ClientBookingsScreen.route)
                }
            )
        }

        composable(ScreenRole.Client.PasswordSettings.route) {
            EditPasswordScreen(
                navController = navHostController,
                navigateToMain = {
                    navHostController.navigate(ScreenClient.ClientBookingsScreen.route)
                }
            )
        }

        composable(ScreenRole.Client.EditProfile.route) {
            EditClientProfileScreen(
                navController = navHostController,
                navigateToMain = {
                    navHostController.navigate(ScreenClient.ClientBookingsScreen.route)
                }
            )
        }

        composable(ScreenRole.Client.Notifications.route) {
            NotificationSettingsScreen(
                navController = navHostController
            )
        }
    }
}