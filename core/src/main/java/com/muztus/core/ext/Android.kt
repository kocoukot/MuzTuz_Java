package com.muztus.core.ext


@Suppress("UNCHECKED_CAST")
inline fun <reified T> Any?.castSafe(): T? = this as? T

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Any.cast(): T = this as T
