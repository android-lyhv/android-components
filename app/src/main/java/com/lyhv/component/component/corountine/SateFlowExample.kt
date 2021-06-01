package com.lyhv.component.component.corountine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

val downloadState = MutableStateFlow(DownloadState.INIT)
val downloadFlow = flow {
    emit(DownloadState.INIT)
    emit(DownloadState.DOWNLOADING)
    delay(1000)
    emit(DownloadState.SUCCESS)
}

suspend fun downloadProcess() {
    downloadState.value = DownloadState.DOWNLOADING
    delay(1000)
    downloadState.value = DownloadState.SUCCESS
}

suspend fun main() {
    downloadState.collect {
        print(it)
    }
    downloadProcess()
}

enum class DownloadState {
    INIT, DOWNLOADING, SUCCESS, FAILED
}