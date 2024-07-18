package com.henrymeds.reservation.data

import com.henrymeds.reservation.data.model.Provider

sealed class ProvidersState<out T> {
  data object Initial : ProvidersState<Nothing>()
  data class Success(val providers: List<Provider>) : ProvidersState<List<Provider>>()
  data class Failure(val exception: Exception) : ProvidersState<Nothing>()
}