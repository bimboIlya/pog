package com.bimboilya.dbpg.domain.entity

enum class TripClass(val code: String) {
    ECONOMY("Y"),
    BUSINESS("C"),
    ;

    override fun toString(): String = code

    companion object {
        fun fromCode(code: String): TripClass =
            TripClass.values().find { it.code.equals(code, ignoreCase = true) }
                ?: error("No TripClass with code $code")
    }
}
