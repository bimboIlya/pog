package com.bimboilya.dbpg.domain.entity

data class SubParams(
    val segments: List<Segment>,
    val passengers: Passengers,
    val tripClass: TripClass,
)
