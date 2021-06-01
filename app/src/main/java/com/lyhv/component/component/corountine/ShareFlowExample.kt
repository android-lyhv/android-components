package com.lyhv.component.component.corountine

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

/**
Management stream state
 */
data class Bod(val id: Int, val name: String)

val sharedFlowDefault = MutableSharedFlow<Int>(
    replay = 0,
    extraBufferCapacity = 0,
    onBufferOverflow = BufferOverflow.SUSPEND
)
val shareFlowWithBufferSuspend = MutableSharedFlow<Int>(
    replay = 2,
    extraBufferCapacity = 5,
    onBufferOverflow = BufferOverflow.SUSPEND
)

val shareFlowWithBufferDropLatest = MutableSharedFlow<Int>(
    replay = 2,
    extraBufferCapacity = 5,
    onBufferOverflow = BufferOverflow.DROP_LATEST
)
val sharedFlowWithBufferDropOldest = MutableSharedFlow<Int>(
    replay = 2,
    extraBufferCapacity = 5,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

suspend fun processSharedFlow() {
    for (i in 1..10) {
        delay(100)
        sharedFlowDefault.emit(i)
    }
}
suspend fun processShareFlowWithBufferSuspend() {
    for (i in 1..10) {
        delay(100)
        shareFlowWithBufferSuspend.emit(i)
    }
}
suspend fun processShareFlowWithBufferDropOldest() {
    for (i in 1..10) {
        delay(100)
        sharedFlowWithBufferDropOldest.emit(i)
    }
}