package cz.noharmdan.wirelessadb

import android.app.Application
import android.util.Log
import cz.noharmdan.wirelessadb.repository.GlobalSettingsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler

class WADBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalSettingsRepository.init(applicationContext)
    }

    companion object {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, ex ->
            if (ex !is CancellationException) {
                Log.e("WADBApplication", "Coroutine exception", ex)
            }
        }
    }
}