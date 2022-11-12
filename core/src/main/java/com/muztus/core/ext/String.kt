package com.muztus.core.ext

fun String.letters() = filter { it.isLetter() || it == ' ' }
