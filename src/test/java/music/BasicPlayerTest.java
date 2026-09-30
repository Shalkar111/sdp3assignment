package music;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BasicPlayerTest {
    @Test
    void playsAtDefaultVolume() throws AudioException {
        AudioEngine audioEngine = mock(AudioEngine.class);
        BasicPlayer player = new BasicPlayer(audioEngine);

        player.play("song.mp3");

        verify(audioEngine).play("song.mp3", 50);
    }
}
