package cz.noharmdan.wirelessadb.service

import android.content.pm.PackageManager
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.annotation.RequiresApi
import cz.noharmdan.wirelessadb.R
import cz.noharmdan.wirelessadb.WADBApplication
import cz.noharmdan.wirelessadb.repository.GlobalSettingsRepository
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.N)
class WirelessTileService : TileService() {

    private val uiJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + uiJob + WADBApplication.coroutineExceptionHandler)

    override fun onStartListening() {
        super.onStartListening()

        GlobalSettingsRepository.update()
        uiScope.launch {
            GlobalSettingsRepository.wirelessDebugState.collect {
                updateTileState(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }

    override fun onClick() {
        super.onClick()

        if (applicationContext.checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                applicationContext,
                R.string.write_secure_settings_permission_disabled_message,
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        GlobalSettingsRepository.isWirelessDebugEnabled =
            !GlobalSettingsRepository.isWirelessDebugEnabled
    }

    private fun updateTileState(isWirelessDebuggingOn: Boolean) {
        qsTile?.let {
            if (isWirelessDebuggingOn) {
                it.state = Tile.STATE_ACTIVE
            } else {
                it.state = Tile.STATE_INACTIVE
            }
            it.updateTile()
        }
    }
}