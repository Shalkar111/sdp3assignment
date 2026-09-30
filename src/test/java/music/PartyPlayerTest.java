package music;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PartyPlayerTest {
    @Test
    void playsAtPartyVolume() throws AudioException {
        AudioEngine audioEngine = mock(AudioEngine.class);
        PartyPlayer player = new PartyPlayer(audioEngine);

        player.play("party.mp3");

        verify(audioEngine).play("party.mp3", 90);
    }
}
