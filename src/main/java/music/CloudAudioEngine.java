package music;

public class CloudAudioEngine implements AudioEngine {
    @Override
    public void play(String filename, int volume) {
        System.out.println("Streaming " + filename + " from cloud at volume " + volume);
    }
}
