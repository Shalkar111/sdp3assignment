package music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AudioEngineFactoryTest {
    @Test
    void selectsEachEngineAtRuntime() {
        assertInstanceOf(LocalAudioEngine.class, AudioEngineFactory.create("local"));
        assertInstanceOf(CloudAudioEngine.class, AudioEngineFactory.create("cloud"));
        assertInstanceOf(LegacyAudioAdapter.class, AudioEngineFactory.create("legacy"));
    }

    @Test
    void rejectsUnknownEngine() {
        assertThrows(IllegalArgumentException.class,
                () -> AudioEngineFactory.create("unknown"));
    }
}
