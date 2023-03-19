package com.bimboilya.common.ktx.jvm

inline fun <reified E : Enum<E>> String.findEnum(): E? =
    enumValues<E>().find { it.name.equals(this, ignoreCase = true) }
