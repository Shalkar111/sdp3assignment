package music;

public class PartyPlayer extends MusicPlayer {
    private static final int PARTY_VOLUME = 90;

    public PartyPlayer(AudioEngine audioEngine) {
        super(audioEngine);
    }

    @Override
    public void play(String filename) throws AudioException {
        audioEngine.play(filename, PARTY_VOLUME);
    }
}
