package com.henrymeds.reservation.ui.screen

sealed class NavigationRoute(val route: String) {
  data object Main : NavigationRoute("main")
  data object ProviderAddSchedule : NavigationRoute("provider_add_schedule")
  data object ClientListAvailability : NavigationRoute("client_list_availability")
  data object ClientReservation : NavigationRoute("client_reservation")
}