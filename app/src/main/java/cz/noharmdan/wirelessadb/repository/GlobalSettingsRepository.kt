package cz.noharmdan.wirelessadb.repository

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
object GlobalSettingsRepository {

    private const val ADB_WIFI_ENABLED = "adb_wifi_enabled"

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, ex ->
        if (ex !is CancellationException) {
            Log.e("GlobalSettingsRepository", "Coroutine exception", ex)
        }
    }

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private var context: Context? = null
    private var contentResolver: ContentResolver? = null

    var isWirelessDebugEnabled: Boolean
        get() = try {
            Settings.Global.getInt(contentResolver, ADB_WIFI_ENABLED, 0) == 1
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        set(value) {
            ioScope.launch {
                Settings.Global.putInt(contentResolver, ADB_WIFI_ENABLED, if (value) 1 else 0)
                wirelessDebugState.emit(isWirelessDebugEnabled)
            }
        }

    val wirelessDebugState: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(isWirelessDebugEnabled)
    }

    fun init(context: Context) {
        this.context = context
        this.contentResolver = context.contentResolver
    }
}