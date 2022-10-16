package com.bimboilya.dbpg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bimboilya.dbpg.data.db.model.SubscriptionDbModel
import com.bimboilya.dbpg.data.db.typeconverters.LocalDateConverter
import com.bimboilya.dbpg.data.db.typeconverters.SegmentsConverter
import com.bimboilya.dbpg.data.db.typeconverters.TripClassConverter

@Database(
    entities = [
        SubscriptionDbModel::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    value = [
        TripClassConverter::class,
        LocalDateConverter::class,
        SegmentsConverter::class,
    ],
)
abstract class SubDatabase : RoomDatabase() {
    abstract fun subDao(): SubDao
}
