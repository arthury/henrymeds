package com.henrymeds.reservation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.henrymeds.reservation.R
import com.henrymeds.reservation.ui.theme.ButtonWidth

@Composable
fun MainScreen(
  innerPadding: PaddingValues,
  navHostController: NavHostController
) {
  val buttonWidthModifier = Modifier
    .width(ButtonWidth)
    .padding(bottom = 16.dp)

  Column(
    Modifier
      .fillMaxSize()
      .padding(innerPadding),
    Arrangement.Center,
    Alignment.CenterHorizontally
  ) {
    ElevatedButton(
      onClick = { navHostController.navigate(NavigationRoute.ProviderAddSchedule.route) },
      modifier = buttonWidthModifier
    ) {
      Text(stringResource(R.string.provider_add_schedule))
    }

    ElevatedButton(
      onClick = { navHostController.navigate(NavigationRoute.ClientListAvailability.route) },
      modifier = buttonWidthModifier
    ) {
      Text(stringResource(R.string.client_list_availability))
    }

    ElevatedButton(
      onClick = { navHostController.navigate(NavigationRoute.ClientReservation.route) },
      modifier = buttonWidthModifier
    ) {
      Text(stringResource(R.string.client_reserve_slot))
    }

    ElevatedButton(
      onClick = { /*TODO*/ },
      modifier = buttonWidthModifier
    ) {
      Text(stringResource(R.string.client_confirm_reservation))
    }
  }
}