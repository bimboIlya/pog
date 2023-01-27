package com.bimboilya.dbpg.domain.repository

import androidx.room.withTransaction
import com.bimboilya.dbpg.data.converter.SubConverter
import com.bimboilya.dbpg.data.converter.toDbSegments
import com.bimboilya.dbpg.data.db.SubDatabase
import com.bimboilya.dbpg.domain.entity.SubParams
import com.bimboilya.dbpg.domain.entity.Subscription
import kotlinx.coroutines.delay
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class SubRepository @Inject constructor(
    private val db: SubDatabase,
    private val subConverter: SubConverter,
) {

    private val subDao = db.subDao()

    private val startTime = System.currentTimeMillis()

    suspend fun create(params: SubParams) {
        val id = UUID.randomUUID().toString()
        kotlin.runCatching {
            db.withTransaction {
                val subscription = Subscription(id, params)
                Timber.d("POOP | ${id.takeLast(4)} start: ${System.currentTimeMillis() - startTime}")
                val dbModel = subConverter.revert(subscription)
                delay(1000)
                if (Random(System.currentTimeMillis()).nextInt(100) < 30) error("лотерея")
                subDao.insert(dbModel)
            }
        }

        Timber.d("POOP | ${id.takeLast(4)} end: ${System.currentTimeMillis() - startTime}")
    }

    suspend fun getAll(): List<Subscription> =
        subDao.selectAll()
            .map(subConverter::convert)

    suspend fun getById(id: String): Subscription? =
        subDao.selectById(id)
            ?.let(subConverter::convert)

    suspend fun getByParams(params: SubParams): Subscription? =
        subDao.selectByParams(
            segments = params.segments.toDbSegments(),
            adults = params.passengers.adults,
            children = params.passengers.children,
            infants = params.passengers.infants,
            tripClass = params.tripClass,
        )?.let(subConverter::convert)
}
