package music;

import java.io.File;

// Represents an older API that cannot be changed to implement AudioEngine.
public class LegacyAudioSystem {
    public static final int SUCCESS = 0;
    public static final int FILE_NOT_FOUND = 1;
    public static final int DEVICE_BUSY = 2;
    public static final int UNSUPPORTED_FORMAT = 3;

    private boolean deviceBusy;

    public void setDeviceBusy(boolean deviceBusy) {
        this.deviceBusy = deviceBusy;
    }

    public int startTrack(int legacyVolume, String filePath) {
        if (deviceBusy) {
            return DEVICE_BUSY;
        }
        if (filePath == null || !new File(filePath).isFile()) {
            return FILE_NOT_FOUND;
        }
        if (!filePath.toLowerCase().endsWith(".mp3")) {
            return UNSUPPORTED_FORMAT;
        }

        System.out.println("Playing " + filePath + " on legacy device at volume " + legacyVolume);
        return SUCCESS;
    }
}
