# WirelessADB
A replacement for A12's missing wireless debugging Quick Settings tile. Because seriously Google?

To make it work:
1. Enable Developer Options
2. Make sure you know what you're getting yourself into
3. In Developer Options, find and enable "Disable Permission Monitoring" (or "USB debugging (Security settings)" on some devices)
4. Install on the device and make sure it's connected via USB
5. Run `adb shell pm grant cz.noharmdan.wirelessadb android.permission.WRITE_SECURE_SETTINGS` in your favorite terminal (or any other terminal)
6. Add the Quick Settings tile and rejoice! (Alternatively there's a switch in the only screen of the app.)
