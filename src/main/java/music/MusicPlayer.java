package music;

public abstract class MusicPlayer {
    protected final AudioEngine audioEngine;

    protected MusicPlayer(AudioEngine audioEngine) {
        this.audioEngine = audioEngine;
    }

    public abstract void play(String filename) throws AudioException;
}
