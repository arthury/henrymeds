package com.henrymeds.reservation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.henrymeds.reservation.R
import com.henrymeds.reservation.data.model.Client
import com.henrymeds.reservation.data.model.TimeSlot
import com.henrymeds.reservation.ui.component.DateDialog
import com.henrymeds.reservation.ui.component.TimeDialog
import com.henrymeds.reservation.ui.theme.ButtonWidth
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ClientReservationScreen(
  reservationViewModel: ReservationViewModel
) {
  val addReservation = reservationViewModel::addReservation

  val buttonWidthModifier = Modifier
    .width(ButtonWidth)
    .padding(bottom = 16.dp)

  var clientId by rememberSaveable { mutableStateOf("") }
  val dateDialogState = rememberMaterialDialogState()
  val timeStartDialogState = rememberMaterialDialogState()
  val timeEndDialogState = rememberMaterialDialogState()

  var date by rememberSaveable { mutableStateOf(LocalDate.now().plusDays(1)) }  // 24 hours in advance
  var timeStart by rememberSaveable { mutableStateOf(LocalTime.now().plusMinutes(60)) }  // README explains why 25 hours is used
  var timeEnd by rememberSaveable { mutableStateOf(LocalTime.NOON.plusMinutes(60 + 15)) }

  val dateString by remember {
    derivedStateOf { DateTimeFormatter.ofPattern("MMM dd yyyy").format(date) }
  }

  val timeStartString by remember {
    derivedStateOf { DateTimeFormatter.ofPattern("HH:mm").format(timeStart) }
  }

  val timeEndString by remember {
    derivedStateOf { DateTimeFormatter.ofPattern("HH:mm").format(timeEnd) }
  }

  fun updateDate(newDate: LocalDate) {
    date = newDate
  }

  fun updateTimeStart(newTime: LocalTime) {
    timeStart = newTime
  }

  fun updateTimeEnd(newTime: LocalTime) {
    timeEnd = newTime
  }

  // 15 minute minimum slot
  val isEndTimeValid = timeEnd >= timeStart.plusMinutes(15)

  val context = LocalContext.current

  fun submitReservation() {
    val timeDateStart = LocalDateTime.of(date, timeStart)
    val timeDateEnd = LocalDateTime.of(date, timeEnd)

    addReservation(Client(clientId), TimeSlot(timeDateStart, timeDateEnd))

    clientId = ""
    Toast.makeText(context, context.getString(R.string.submitted), Toast.LENGTH_SHORT).show()
  }

  Column(
    Modifier.fillMaxSize(),
    Arrangement.Center,
    Alignment.CenterHorizontally,
  ) {
    Text(stringResource(R.string.client_reserve_slot))
    Spacer(Modifier.height(16.dp))

    Text(stringResource(R.string.client_id))
    TextField(
      value = clientId,
      onValueChange = { clientId = it },
      modifier = buttonWidthModifier
    )

    val dateTitle = stringResource(R.string.select_date)
    DateDialog(dateDialogState, dateTitle, date, ::updateDate)
    Text(text = dateString)
    ElevatedButton(
      onClick = { dateDialogState.show() },
      modifier = buttonWidthModifier
    ) {
      Text(dateTitle)
    }

    val startTimeTitle = stringResource(R.string.select_start_time)
    TimeDialog(timeStartDialogState, startTimeTitle, timeStart, ::updateTimeStart)
    Text(text = timeStartString)
    ElevatedButton(
      onClick = { timeStartDialogState.show() },
      modifier = buttonWidthModifier
    ) {
      Text(startTimeTitle)
    }

    val endTimeTitle = stringResource(R.string.select_end_time)
    TimeDialog(timeEndDialogState, endTimeTitle, timeEnd, ::updateTimeEnd)
    Text(text = timeEndString)
    ElevatedButton(
      onClick = { timeEndDialogState.show() },
      modifier = buttonWidthModifier
    ) {
      Text(endTimeTitle)
    }

    val isSubmitEnabled = clientId.isNotEmpty() && isEndTimeValid
    Spacer(Modifier.height(16.dp))
    ElevatedButton(
      onClick = { submitReservation() },
      modifier = buttonWidthModifier,
      enabled = isSubmitEnabled
    ) {
      Text(stringResource(R.string.submit))
    }
  }
}
