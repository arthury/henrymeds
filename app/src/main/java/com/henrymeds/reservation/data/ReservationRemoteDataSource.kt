package com.henrymeds.reservation.data

import com.henrymeds.reservation.data.model.Provider
import com.henrymeds.reservation.network.MockBackend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ReservationRemoteDataSource(
  private val coroutineDispatcher: CoroutineDispatcher
) : ReservationApi {

  override suspend fun getProviders() : ProvidersState<List<Provider>> = withContext(coroutineDispatcher) {
    try {
      val response = MockBackend.getProviders()
      if (response != null) ProvidersState.Success(response)
      else ProvidersState.Failure(NullPointerException())
    } catch (e: Exception) {
      ProvidersState.Failure(e)
    }
  }

}