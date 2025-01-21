package com.example.arabus

import kotlinx.coroutines.CoroutineScope

interface CoroutineScopeProvider {
    val ioScope: CoroutineScope
}
