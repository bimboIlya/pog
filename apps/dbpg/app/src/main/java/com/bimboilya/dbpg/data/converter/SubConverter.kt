package com.bimboilya.dbpg.data.converter

import com.bimboilya.dbpg.data.db.model.PassengersDbModel
import com.bimboilya.dbpg.data.db.model.SegmentDbModel
import com.bimboilya.dbpg.data.db.model.SubParamsDbModel
import com.bimboilya.dbpg.data.db.model.SubscriptionDbModel
import com.bimboilya.dbpg.domain.entity.Passengers
import com.bimboilya.dbpg.domain.entity.Segment
import com.bimboilya.dbpg.domain.entity.SubParams
import com.bimboilya.dbpg.domain.entity.Subscription
import javax.inject.Inject

class SubConverter @Inject constructor() {

    fun convert(subscriptionDb: SubscriptionDbModel): Subscription =
        with(subscriptionDb) {
            Subscription(
                id = id,
                params = params.toSubParams(),
            )
        }

    fun revert(subscription: Subscription): SubscriptionDbModel =
        with(subscription) {
            SubscriptionDbModel(
                id = id,
                params = params.toDbSubParams(),
            )
        }
}

fun SubParamsDbModel.toSubParams(): SubParams =
    SubParams(
        segments = segments.toSegments(),
        passengers = passengers.toPassengers(),
        tripClass = tripClass,
    )

fun List<SegmentDbModel>.toSegments(): List<Segment> =
    map { segmentDb ->
        with(segmentDb) {
            Segment(
//                date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE),
                originCityIata = originCityIata,
                originAirportIata = originAirportIata,
                destinationCityIata = destinationCityIata,
                destinationAirportIata = destinationAirportIata,
            )
        }
    }

fun PassengersDbModel.toPassengers(): Passengers =
    Passengers(adults, children, infants)

fun SubParams.toDbSubParams(): SubParamsDbModel =
    SubParamsDbModel(
        segments = segments.toDbSegments(),
        passengers = passengers.toDbPassengers(),
        tripClass = tripClass,
    )

fun List<Segment>.toDbSegments(): List<SegmentDbModel> =
    map { segment ->
        with(segment) {
            SegmentDbModel(
//                date = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                originCityIata = originCityIata,
                originAirportIata = originAirportIata,
                destinationCityIata = destinationCityIata,
                destinationAirportIata = destinationAirportIata,
            )
        }
    }

fun Passengers.toDbPassengers(): PassengersDbModel =
    PassengersDbModel(adults = adults, children = children, infants = infants)
