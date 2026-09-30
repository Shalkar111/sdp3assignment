# Assignment 3 — Bridge + Adapter

## Overview

A small Music Playback System. Choose a player style and an audio engine; the program prints a playback message instead of playing real audio.

## Patterns

### Bridge

MusicPlayer is the Abstraction. BasicPlayer (volume 50) and PartyPlayer (volume 90) are Refined Abstractions. AudioEngine is the Implementor interface. LocalAudioEngine, CloudAudioEngine, and LegacyAudioAdapter are its three implementations.

### Adapter

LegacyAudioAdapter wraps LegacyAudioSystem. The old class has startTrack(int, String), reversed arguments, and integer status codes. The adapter presents AudioEngine.play(String, int) and translates failures into AudioException.

## Dynamic Implementor Selection

Main reads the engine type from the first command-line argument. AudioEngineFactory.create chooses local, cloud, or legacy at runtime, so Main does not select a concrete engine class. The second argument chooses basic or party; the third is the filename. Defaults are local basic song.mp3.

## Project Structure

- src/main/java/music/ — application code
- src/test/java/music/ — JUnit 5 and Mockito tests
- docs/ — UML, rationale, defense notes, and submission checklist
- pom.xml — Maven build

## Requirements

Java 17 or newer and Maven 3. In IntelliJ IDEA, open pom.xml as a Maven project and run music.Main.

## Run

From the project directory:

    mvn package
    java -cp target/classes music.Main
    java -cp target/classes music.Main cloud party party.mp3

For legacy mode, pass an existing .mp3 file as the third argument. LegacyAudioSystem checks that the file exists and has an .mp3 name.

On this Mac, Maven is bundled with IntelliJ but is not currently on the Terminal PATH. This exact one-line test command works here:

    PATH="/Applications/IntelliJ IDEA.app/Contents/plugins/maven-plugin/lib/maven3/bin:$PATH" mvn test

Use the same PATH prefix with mvn package if needed.

## Tests

    mvn test

The tests check both player volumes and delegation, runtime engine selection, and the adapter's success and error translation.

## Documentation

- [Design rationale](docs/design-rationale.md)
- [UML image](docs/uml.png)
- [PlantUML source](docs/uml.puml)
- [Defense cheat sheet](docs/defense-cheat-sheet.md)
- [Submission checklist](docs/submission-checklist.md)
