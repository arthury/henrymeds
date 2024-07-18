package com.henrymeds.reservation.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.henrymeds.reservation.R
import com.henrymeds.reservation.data.ProvidersState
import com.henrymeds.reservation.data.model.Provider
import com.henrymeds.reservation.data.model.TimeSlot
import java.time.format.DateTimeFormatter

@Composable
fun ClientListAvailabilityScreen(
  reservationViewModel: ReservationViewModel
) {
  reservationViewModel.getProviders()

  when(val providersState = reservationViewModel.providersState.collectAsState().value) {
    is ProvidersState.Initial -> Text("Loading...")
    is ProvidersState.Failure -> Text("Error")  // TODO handle various errors
    is ProvidersState.Success -> {
      if (providersState.providers.isEmpty()) {
        Text(stringResource(R.string.no_appointments_available), Modifier.padding(16.dp))
      }

      LazyColumn(
        Modifier.padding(16.dp)
      ) {
        items(providersState.providers) { provider ->
          provider.schedule.forEach { timeSlot ->
            AvailabilityRow(provider, timeSlot)
          }
        }
      }
    }
  }
}

@Composable
fun AvailabilityRow(
  provider: Provider,
  timeSlot: TimeSlot
) {
  Row(
    Modifier.fillMaxWidth()
  ) {
    val date = DateTimeFormatter.ofPattern("MMM dd yyyy").format(timeSlot.start)
    val timeStart = DateTimeFormatter.ofPattern("HH:mm").format(timeSlot.start)
    val timeEnd = DateTimeFormatter.ofPattern("HH:mm").format(timeSlot.end)

    Text("${provider.id}  $date  $timeStart - $timeEnd")
  }
}
