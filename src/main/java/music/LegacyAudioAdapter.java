package music;

public class LegacyAudioAdapter implements AudioEngine {
    private final LegacyAudioSystem legacyAudioSystem;

    public LegacyAudioAdapter(LegacyAudioSystem legacyAudioSystem) {
        this.legacyAudioSystem = legacyAudioSystem;
    }

    @Override
    public void play(String filename, int volume) throws AudioException {
        int status;
        try {
            status = legacyAudioSystem.startTrack(volume, filename);
        } catch (RuntimeException e) {
            throw new AudioException("Legacy audio system failed", e);
        }

        switch (status) {
            case LegacyAudioSystem.SUCCESS:
                return;
            case LegacyAudioSystem.FILE_NOT_FOUND:
                throw new AudioException("Audio file not found");
            case LegacyAudioSystem.DEVICE_BUSY:
                throw new AudioException("Audio device is busy");
            case LegacyAudioSystem.UNSUPPORTED_FORMAT:
                throw new AudioException("Unsupported audio format");
            default:
                throw new AudioException("Unknown audio playback error");
        }
    }
}
