package com.bimboilya.dbpg.domain.entity

data class Segment(
//    val date: LocalDate,
    val originCityIata: String,
    val originAirportIata: String?,
    val destinationCityIata: String,
    val destinationAirportIata: String?,
)
