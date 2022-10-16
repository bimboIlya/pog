package com.bimboilya.dbpg.presentation

import com.bimboilya.dbpg.domain.entity.TripClass

data class DbpgState(
    val originCity: String,
    val originAirport: String,
    val destinationCity: String,
    val destinationAirport: String,
    val isRoundTrip: Boolean,
    val adults: Int,
    val children: Int,
    val infants: Int,
    val tripClass: TripClass,
    val isInputValidated: Boolean,
)
