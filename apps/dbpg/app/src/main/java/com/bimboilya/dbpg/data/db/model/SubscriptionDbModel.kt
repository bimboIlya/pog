package com.bimboilya.dbpg.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bimboilya.dbpg.domain.entity.TripClass
import kotlinx.serialization.Serializable

@Entity(tableName = "subscriptions")
data class SubscriptionDbModel(
    @PrimaryKey
    @ColumnInfo(name = "subscription_id")
    val id: String,

    @Embedded val params: SubParamsDbModel,
)

data class SubParamsDbModel(
    val segments: List<SegmentDbModel>,
    @Embedded val passengers: PassengersDbModel,
    @ColumnInfo(name = "trip_class") val tripClass: TripClass,
)

data class PassengersDbModel(
    @ColumnInfo(name = "adults") val adults: Int,
    @ColumnInfo(name = "children") val children: Int,
    @ColumnInfo(name = "infants") val infants: Int,
)

@Serializable
data class SegmentDbModel(
//    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "origin_city_iata") val originCityIata: String,
    @ColumnInfo(name = "origin_airport_iata") val originAirportIata: String?,
    @ColumnInfo(name = "destination_city_iata") val destinationCityIata: String,
    @ColumnInfo(name = "destination_airport_iata") val destinationAirportIata: String?,
)
