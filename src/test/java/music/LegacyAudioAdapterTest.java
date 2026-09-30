package music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LegacyAudioAdapterTest {
    private final LegacyAudioSystem legacy = mock(LegacyAudioSystem.class);
    private final AudioEngine adapter = new LegacyAudioAdapter(legacy);

    @Test
    void callsLegacySystemWithReorderedArguments() throws AudioException {
        when(legacy.startTrack(70, "song.mp3")).thenReturn(LegacyAudioSystem.SUCCESS);

        adapter.play("song.mp3", 70);

        verify(legacy).startTrack(70, "song.mp3");
    }

    @Test
    void translatesFileNotFound() {
        when(legacy.startTrack(50, "missing.mp3"))
                .thenReturn(LegacyAudioSystem.FILE_NOT_FOUND);

        AudioException error = assertThrows(AudioException.class,
                () -> adapter.play("missing.mp3", 50));

        assertEquals("Audio file not found", error.getMessage());
    }

    @Test
    void translatesDeviceBusy() {
        when(legacy.startTrack(50, "song.mp3"))
                .thenReturn(LegacyAudioSystem.DEVICE_BUSY);

        AudioException error = assertThrows(AudioException.class,
                () -> adapter.play("song.mp3", 50));

        assertEquals("Audio device is busy", error.getMessage());
    }

    @Test
    void translatesUnsupportedFormat() {
        when(legacy.startTrack(50, "song.wav"))
                .thenReturn(LegacyAudioSystem.UNSUPPORTED_FORMAT);

        AudioException error = assertThrows(AudioException.class,
                () -> adapter.play("song.wav", 50));

        assertEquals("Unsupported audio format", error.getMessage());
    }

    @Test
    void translatesUnknownStatus() {
        when(legacy.startTrack(50, "song.mp3")).thenReturn(99);

        AudioException error = assertThrows(AudioException.class,
                () -> adapter.play("song.mp3", 50));

        assertEquals("Unknown audio playback error", error.getMessage());
    }

    @Test
    void wrapsUnexpectedLegacyException() {
        IllegalStateException legacyError = new IllegalStateException("legacy failure");
        when(legacy.startTrack(50, "song.mp3")).thenThrow(legacyError);

        AudioException error = assertThrows(AudioException.class,
                () -> adapter.play("song.mp3", 50));

        assertEquals("Legacy audio system failed", error.getMessage());
        assertSame(legacyError, error.getCause());
    }
}
