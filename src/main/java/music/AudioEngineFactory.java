package music;

public class AudioEngineFactory {
    public static AudioEngine create(String engineType) {
        if ("local".equalsIgnoreCase(engineType)) {
            return new LocalAudioEngine();
        }
        if ("cloud".equalsIgnoreCase(engineType)) {
            return new CloudAudioEngine();
        }
        if ("legacy".equalsIgnoreCase(engineType)) {
            return new LegacyAudioAdapter(new LegacyAudioSystem());
        }
        throw new IllegalArgumentException("Unknown audio engine: " + engineType);
    }
}
