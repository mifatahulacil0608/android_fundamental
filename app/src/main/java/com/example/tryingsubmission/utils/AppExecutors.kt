@file:Suppress("unused", "unused", "unused", "unused")

package com.example.tryingsubmission.utils

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutors {
    val diskIO : Executor = Executors.newSingleThreadExecutor()
    val netWorkIO : Executor = Executors.newFixedThreadPool(3)
    val mainThread : Executor = MainThreadExecutor()

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}