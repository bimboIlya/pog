package com.bimboilya.dbpg.data.db.typeconverters

import androidx.room.TypeConverter
import com.bimboilya.dbpg.data.db.model.SegmentDbModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SegmentsConverter {

    @TypeConverter
    fun segmentsToString(segments: List<SegmentDbModel>): String =
        Json.encodeToString(segments)

    @TypeConverter
    fun stringToSegments(json: String): List<SegmentDbModel> =
        Json.decodeFromString(json)
}
