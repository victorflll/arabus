package com.example.arabus

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application(), CoroutineScopeProvider {
    private val applicationJob = SupervisorJob()

    override val ioScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.IO + applicationJob)

    override fun onTerminate() {
        super.onTerminate()
        applicationJob.cancel()
    }
}
