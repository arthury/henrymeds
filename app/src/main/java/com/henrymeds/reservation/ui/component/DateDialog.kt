package com.henrymeds.reservation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.henrymeds.reservation.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DateDialog(
  dateDialogState: MaterialDialogState,
  title: String,
  initialDate: LocalDate,
  updateDate: (LocalDate) -> Unit
) {
  MaterialDialog(
    dialogState = dateDialogState,
    buttons = {
      positiveButton(stringResource(R.string.ok))
      negativeButton(stringResource(R.string.cancel))
    }
  ) {
    datepicker(
      initialDate,
      title,
      allowedDateValidator = { it >= initialDate }
    ) {
      updateDate(it)
    }
  }
}
