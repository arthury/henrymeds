package com.henrymeds.reservation.data.model

sealed class User(open val id: String)

data class Client(
  override val id: String
) : User(id)

data class Provider(
  override val id: String,
  val schedule: List<TimeSlot>  // assume LocalDateTime
) : User(id)


// network mock
data class MutableProvider(
  override val id: String,
  var schedule: List<TimeSlot>  // assume LocalDateTime
) : User(id)

fun Provider.toMutable() = MutableProvider(
  this.id,
  this.schedule
)

fun MutableProvider.toImmutable() = Provider(
  this.id,
  this.schedule
)
