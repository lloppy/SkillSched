package com.example.skills.navigation.nav_bar.client

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skills.ui.client.b.ClientBookingsScreen
import com.example.skills.ui.client.c.EditClientProfileScreen
import com.example.skills.ui.client.a.ClientMastersScreen
import com.example.skills.ui.client.a.MasterServicesScreen
import com.example.skills.ui.client.a.ViewMasterScreen
import com.example.skills.ui.client.a.edit_booking.EditConfirmClientBookingScreen
import com.example.skills.ui.client.a.edit_booking.EditDateScreen
import com.example.skills.ui.client.a.edit_booking.EditDoneClientBookingScreen
import com.example.skills.ui.client.a.edit_booking.EditTimeScreen
import com.example.skills.client.components.a.new_booking.ConfirmClientBookingScreen
import com.example.skills.client.components.a.new_booking.DoneClientBookingScreen
import com.example.skills.client.components.a.new_booking.SelectDateScreen
import com.example.skills.ui.client.a.new_booking.SelectTimeScreen
import com.example.skills.data.viewmodel.MainViewModel
import com.example.skills.data.viewmodel.route.BookingViewModel
import com.example.skills.data.viewmodel.route.EditBookingViewModel
import com.example.skills.navigation.ScreenRole
import com.example.skills.navigation.ScreenClient
import com.example.skills.ui.client.a.QRCodeScannerComposable
import com.example.skills.ui.master.e.EditPasswordScreen
import com.example.skills.ui.master.e.MasterSettingsScreen
import com.example.skills.ui.master.e.NotificationSettingsScreen

@Composable
fun SetupClientNavGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    // BookingViewModel
    val bookingViewModel: BookingViewModel = viewModel()
    val editBookingViewModel: EditBookingViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = ScreenClient.ClientBookingsScreen.route
    ) {
        // done checkbox
        composable(route = ScreenClient.ClientBookingsScreen.route) {
            ClientBookingsScreen(navController = navHostController, editBookingViewModel, viewModel = mainViewModel)
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
                navigateToNotifications = {
                    navHostController.navigate(ScreenRole.Client.Notifications.route)
                },
                mainViewModel = mainViewModel,
                exit = {
                    // крашится, потому что другой роут, нужно потом полностью закрывать приложение
                    navHostController.navigate(ScreenRole.Client.LogIn.route)
                }
            )
        }

        // выбираем мастера из списка мастеров
        composable(route = ScreenClient.ClientMastersScreen.route) {
            ClientMastersScreen(
                bookingViewModel = bookingViewModel,
                mainViewModel = mainViewModel,
                navigateToSelectedMasterProfile = {
                    navHostController.navigate(ScreenRole.Client.ViewMaster.route)
                },
                navigateToQr = {
                    navHostController.navigate(ScreenRole.Client.QRCodeScanner.route)
                }
            )
        }

        // Qr код экран
        composable(route = ScreenRole.Client.QRCodeScanner.route) {
            QRCodeScannerComposable (
                viewModel = mainViewModel,
                navController = navHostController
            ) { result ->
                navHostController.navigateUp()
            }
        }

        // нажимаем на кнопку записаться в профиле выбранного мастера
        composable(route = ScreenRole.Client.ViewMaster.route) {
            ViewMasterScreen(
                bookingViewModel,
                navHostController,
                navigateToServices = {
                    navHostController.navigate(ScreenRole.Client.ViewMasterServices.route)
                },
                mainViewModel = mainViewModel
            )
        }

        // экран с категориями и карточками Services
        composable(route = ScreenRole.Client.ViewMasterServices.route) {
            MasterServicesScreen(
                bookingViewModel = bookingViewModel,
                navController = navHostController,
                navigateToSelectDate = {
                    navHostController.navigate(ScreenRole.Client.SelectDate.route)
                },
                viewModel = mainViewModel
            )
        }

        // экран с календарем
        composable(route = ScreenRole.Client.SelectDate.route) {
            SelectDateScreen(
                bookingViewModel = bookingViewModel,
                navController = navHostController,
                navigateToSelectTime = {
                    navHostController.navigate(ScreenRole.Client.SelectTime.route)
                },
                mainViewModel = mainViewModel
            )
        }

        // экран с календарем
        composable(route = ScreenRole.Client.EditDate.route) {
            EditDateScreen(
                editBookingViewModel = editBookingViewModel,
                navController = navHostController,
                navigateToSelectTime = {
                    navHostController.navigate(ScreenRole.Client.EditTime.route)
                },
                mainViewModel = mainViewModel
            )
        }

        // экран с кружочками временных слотов
        composable(route = ScreenRole.Client.SelectTime.route) { backStackEntry ->
            SelectTimeScreen(
                bookingViewModel = bookingViewModel,
                navController = navHostController,
                navigateToConfirmBooking = {
                    navHostController.navigate(ScreenRole.Client.ConfirmClientBooking.route)
                },
                mainViewModel = mainViewModel
            )
        }

        // экран с кружочками временных слотов
        composable(route = ScreenRole.Client.EditTime.route) { backStackEntry ->
            EditTimeScreen(
                editBookingViewModel = editBookingViewModel,
                navController = navHostController,
                navigateToConfirmBooking = {
                    navHostController.navigate(ScreenRole.Client.EditConfirmClientBooking.route)
                },
                mainViewModel = mainViewModel
            )
        }

        // экран подтверждения и оставления комментария
        composable(ScreenRole.Client.ConfirmClientBooking.route) {
            ConfirmClientBookingScreen(
                navController = navHostController,
                bookingViewModel = bookingViewModel,
                navigateToDoneBooking = {
                    navHostController.navigate(ScreenRole.Client.DoneClientBooking.route)
                },
                mainViewModel = mainViewModel
            )
        }

        // экран подтверждения и оставления комментария
        composable(ScreenRole.Client.EditConfirmClientBooking.route) {
            EditConfirmClientBookingScreen(
                navController = navHostController,
                editBookingViewModel = editBookingViewModel,
                navigateToDoneBooking = {
                    navHostController.navigate(ScreenRole.Client.EditDoneClientBooking.route)
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

        // экран успешной регистрации после изменения
        composable(ScreenRole.Client.EditDoneClientBooking.route) {
            EditDoneClientBookingScreen(
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
                },
                viewModel = mainViewModel
            )
        }

        composable(ScreenRole.Client.Notifications.route) {
            NotificationSettingsScreen(
                navController = navHostController
            )
        }
    }
}