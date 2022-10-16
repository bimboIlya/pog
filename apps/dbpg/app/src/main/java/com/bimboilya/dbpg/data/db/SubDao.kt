package com.bimboilya.dbpg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bimboilya.dbpg.data.db.model.SubscriptionDbModel

@Dao
interface SubDao {

    @Insert
    suspend fun insert(sub: SubscriptionDbModel)

    @Query("SELECT * FROM subscriptions")
    suspend fun selectAll(): List<SubscriptionDbModel>

    @Query("SELECT * FROM subscriptions WHERE subscription_id = :id")
    suspend fun selectById(id: String): SubscriptionDbModel?
}
