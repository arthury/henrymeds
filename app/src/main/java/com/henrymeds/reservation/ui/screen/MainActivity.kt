package com.henrymeds.reservation.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.henrymeds.reservation.data.ReservationRemoteDataSource
import com.henrymeds.reservation.data.ReservationRepository
import com.henrymeds.reservation.ui.theme.ReservationTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

  private lateinit var navHostController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val reservationViewModel: ReservationViewModel by viewModels<ReservationViewModel> {
      // The coroutine scope is passed down here for future dependency injection.
      ReservationViewModel.Factory(
        Dispatchers.IO,
        ReservationRepository(ReservationRemoteDataSource(Dispatchers.IO))
      )
    }

    setContent {
      navHostController = rememberNavController()

      ReservationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavHost(navHostController, startDestination = NavigationRoute.Main.route) {
            composable(NavigationRoute.Main.route) {
              MainScreen(innerPadding, navHostController)
            }
            composable(NavigationRoute.ProviderAddSchedule.route) {
              ProviderAddScheduleScreen(reservationViewModel)
            }
            composable(NavigationRoute.ClientListAvailability.route) {
              ClientListAvailabilityScreen(reservationViewModel)
            }
            composable(NavigationRoute.ClientReservation.route) {
              ClientReservationScreen(reservationViewModel)
            }
          }
        }
      }
    }
  }
}
