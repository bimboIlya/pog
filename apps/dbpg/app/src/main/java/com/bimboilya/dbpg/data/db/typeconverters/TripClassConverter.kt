package com.bimboilya.dbpg.data.db.typeconverters

import androidx.room.TypeConverter
import com.bimboilya.dbpg.domain.entity.TripClass

class TripClassConverter {

    @TypeConverter
    fun tripClassToString(tripClass: TripClass): String =
        tripClass.code

    @TypeConverter
    fun stringToTripClass(code: String): TripClass =
        TripClass.fromCode(code)
}
