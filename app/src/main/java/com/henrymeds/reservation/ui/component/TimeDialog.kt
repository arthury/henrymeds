package com.henrymeds.reservation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.henrymeds.reservation.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

@Composable
fun TimeDialog(
  timeDialogState: MaterialDialogState,
  title: String,
  initialTime: LocalTime,
  updateTime: (LocalTime) -> Unit
) {
  MaterialDialog(
    dialogState = timeDialogState,
    buttons = {
      positiveButton(stringResource(R.string.ok))
      negativeButton(stringResource(R.string.cancel))
    }
  ) {
    timepicker(initialTime, title) {
      updateTime(it)
    }
  }
}
