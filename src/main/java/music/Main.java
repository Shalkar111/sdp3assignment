package music;

public class Main {
    public static void main(String[] args) {
        String engineType = args.length > 0 ? args[0] : "local";
        String playerType = args.length > 1 ? args[1] : "basic";
        String filename = args.length > 2 ? args[2] : "song.mp3";

        try {
            AudioEngine audioEngine = AudioEngineFactory.create(engineType);
            MusicPlayer player;

            if ("basic".equalsIgnoreCase(playerType)) {
                player = new BasicPlayer(audioEngine);
            } else if ("party".equalsIgnoreCase(playerType)) {
                player = new PartyPlayer(audioEngine);
            } else {
                throw new IllegalArgumentException("Unknown player type: " + playerType);
            }

            player.play(filename);
        } catch (AudioException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
