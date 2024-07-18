package com.henrymeds.reservation.network

import com.henrymeds.reservation.data.model.Client
import com.henrymeds.reservation.data.model.MutableProvider
import com.henrymeds.reservation.data.model.Provider
import com.henrymeds.reservation.data.model.TimeSlot
import com.henrymeds.reservation.data.model.toImmutable
import com.henrymeds.reservation.data.model.toMutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

object MockBackend {
  private var mutableProviders: MutableList<MutableProvider> = mutableListOf()
  private var reservations: MutableList<Reservation> = mutableListOf()

  suspend fun getProviders(): List<Provider>? = withContext(Dispatchers.IO) {
    val delaySec = if ((0..100).random() > 50)  1L else 0L
    delay(TimeUnit.SECONDS.toMillis(delaySec))

    val providers = mutableListOf<Provider>()
    mutableProviders.forEach { mutableProvider ->
      providers.add(mutableProvider.toImmutable())
    }

    return@withContext providers
  }

  // Note: Overwrites existing provider's schedule instead of updating the list due to lack of time.
  suspend fun updateProvider(provider: Provider) = withContext(Dispatchers.IO) {
    val delaySec = if ((0..100).random() > 50)  1L else 0L
    delay(TimeUnit.SECONDS.toMillis(delaySec))

    var doesProviderExist = false

    mutableProviders.forEach { mutableProvider ->
      if (mutableProvider.id == provider.id) {
        // TODO add ability to add new available time slots to the existing list instead of overwriting
        mutableProvider.schedule = provider.schedule
        doesProviderExist = true
      }
    }

    if (!doesProviderExist) {
      mutableProviders.add(provider.toMutable())
    }
  }

  // TODO parse through mutableProviders and return a response if invalid reservation time
  suspend fun addReservation(client: Client, timeSlot: TimeSlot) = withContext(Dispatchers.IO) {
    val delaySec = if ((0..100).random() > 50)  1L else 0L
    delay(TimeUnit.SECONDS.toMillis(delaySec))

    reservations.add(Reservation(client, timeSlot))
  }

  data class Reservation(val client: Client, val timeSlot: TimeSlot, var isConfirmed: Boolean = false)
}