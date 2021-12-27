package com.bimboilya.common.ktx.jvm

inline fun <reified T : Enum<*>> String.findEnum(): T? =
    T::class.java.enumConstants?.find { it.name.equals(this, ignoreCase = true) }
