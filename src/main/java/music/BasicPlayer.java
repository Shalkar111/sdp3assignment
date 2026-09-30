package music;

public class BasicPlayer extends MusicPlayer {
    private static final int DEFAULT_VOLUME = 50;

    public BasicPlayer(AudioEngine audioEngine) {
        super(audioEngine);
    }

    @Override
    public void play(String filename) throws AudioException {
        audioEngine.play(filename, DEFAULT_VOLUME);
    }
}
