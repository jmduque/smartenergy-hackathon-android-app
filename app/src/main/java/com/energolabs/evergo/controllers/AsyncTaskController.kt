package com.energolabs.evergo.controllers

import android.os.AsyncTask

import java.util.concurrent.Executor
import java.util.concurrent.Executors

object AsyncTaskController {

    internal var threadedExecutor: Executor = Executors.newCachedThreadPool()

    // EXECUTOR
    fun startTaskExecute(myTask: AsyncTask<Any, *, *>) {
        myTask.execute()
    }

    // EXECUTOR
    @JvmOverloads fun startTask(myTask: AsyncTask<Any, *, *>, params: Array<Any>? = null) {
        myTask.executeOnExecutor(threadedExecutor, params)
    }

}
