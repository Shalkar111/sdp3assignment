package music;

public class LocalAudioEngine implements AudioEngine {
    @Override
    public void play(String filename, int volume) {
        System.out.println("Playing " + filename + " locally at volume " + volume);
    }
}
