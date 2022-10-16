package com.bimboilya.dbpg.data.db.typeconverters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun localDateToString(localDate: LocalDate): String =
        localDate.format(formatter)

    @TypeConverter
    fun stringToLocalDate(date: String): LocalDate =
        LocalDate.parse(date, formatter)
}
