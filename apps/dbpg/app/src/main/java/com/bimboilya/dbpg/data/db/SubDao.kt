package com.bimboilya.dbpg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bimboilya.dbpg.data.db.model.SegmentDbModel
import com.bimboilya.dbpg.data.db.model.SubscriptionDbModel
import com.bimboilya.dbpg.domain.entity.TripClass

@Dao
interface SubDao {

    @Insert
    suspend fun insert(sub: SubscriptionDbModel)

    @Query("SELECT * FROM subscriptions")
    suspend fun selectAll(): List<SubscriptionDbModel>

    @Query("SELECT * FROM subscriptions WHERE subscription_id = :id")
    suspend fun selectById(id: String): SubscriptionDbModel?

    @Query(
        """
            SELECT *
            FROM subscriptions
            WHERE segments = :segments
              AND adults = :adults
              AND children = :children
              AND infants = :infants
              AND trip_class = :tripClass
        """
    )
    suspend fun selectByParams(
        segments: List<SegmentDbModel>,
        adults: Int,
        children: Int,
        infants: Int,
        tripClass: TripClass,
    ) : SubscriptionDbModel?
}
