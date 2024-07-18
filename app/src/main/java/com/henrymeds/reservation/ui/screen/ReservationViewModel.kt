package com.henrymeds.reservation.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.henrymeds.reservation.data.ProvidersState
import com.henrymeds.reservation.data.ReservationRepository
import com.henrymeds.reservation.data.model.Client
import com.henrymeds.reservation.data.model.Provider
import com.henrymeds.reservation.data.model.TimeSlot
import com.henrymeds.reservation.network.MockBackend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReservationViewModel(
  private val coroutineDispatcher: CoroutineDispatcher,
  private val reservationRepository: ReservationRepository
) : ViewModel() {

  private val _providersState = MutableStateFlow<ProvidersState<List<Provider>>>(ProvidersState.Initial)
  val providersState: StateFlow<ProvidersState<List<Provider>>> = _providersState

  fun getProviders() = viewModelScope.launch(coroutineDispatcher) {
    _providersState.value = reservationRepository.getProviders()
  }

  fun updateProvider(provider: Provider) = viewModelScope.launch(coroutineDispatcher) {
    MockBackend.updateProvider(provider)
  }

  fun addReservation(client: Client, timeSlot: TimeSlot) = viewModelScope.launch(coroutineDispatcher) {
    MockBackend.addReservation(client, timeSlot)
  }


  class Factory(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val reservationRepository: ReservationRepository
  ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
      modelClass: Class<T>,
    ): T {
      return ReservationViewModel(coroutineDispatcher, reservationRepository) as T
    }
  }

}