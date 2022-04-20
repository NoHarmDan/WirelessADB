package cz.noharmdan.wirelessadb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.noharmdan.wirelessadb.repository.GlobalSettingsRepository
import cz.noharmdan.wirelessadb.ui.theme.WirelessADBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WirelessADBTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    WirelessDebugSwitch()
                }
            }
        }
    }
}

@Composable
fun WirelessDebugSwitch() {
    val state = remember { GlobalSettingsRepository.wirelessDebugState }.collectAsState().value

    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentHeight(Alignment.Top)
            .height(64.dp)
            .fillMaxWidth()
            .clickable {
                GlobalSettingsRepository.isWirelessDebugEnabled = !state
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.title_wireless_debugging),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 24.dp),
        )

        Switch(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            checked = state,
            onCheckedChange = { isChecked ->
                GlobalSettingsRepository.isWirelessDebugEnabled = isChecked
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WirelessADBTheme {
        WirelessDebugSwitch()
    }
}