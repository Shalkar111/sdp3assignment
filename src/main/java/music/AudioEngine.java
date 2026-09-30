package music;

public interface AudioEngine {
    void play(String filename, int volume) throws AudioException;
}
