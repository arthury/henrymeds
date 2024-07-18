package com.henrymeds.reservation.data

import com.henrymeds.reservation.data.model.Provider

class ReservationRepository(
  private val reservationRemoteDataSource: ReservationRemoteDataSource
) : ReservationApi {
  override suspend fun getProviders() = reservationRemoteDataSource.getProviders()
}

interface ReservationApi {
  suspend fun getProviders() : ProvidersState<List<Provider>>
}