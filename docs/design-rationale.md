# Design Rationale

## 1. Problem

This Music Playback System is a small program with two player styles and three ways to handle playback. The user chooses an engine, a player, and a filename. The program prints what would happen; it does not play real audio.

## 2. Bridge Pattern

MusicPlayer is the Abstraction. BasicPlayer and PartyPlayer are the Refined Abstractions. AudioEngine is the Implementor interface. LocalAudioEngine, CloudAudioEngine, and LegacyAudioAdapter are its three Concrete Implementors.

Player behavior and playback technology are separate choices. BasicPlayer always passes volume 50, while PartyPlayer passes volume 90. Both delegate to the AudioEngine stored by MusicPlayer. Without Bridge, we might need classes such as BasicLocalPlayer, BasicCloudPlayer, PartyLocalPlayer, and PartyCloudPlayer. With more player types and engines, that number would keep growing.

## 3. Adapter Pattern

LegacyAudioSystem is the Adaptee. It does not implement AudioEngine. Its method is named startTrack and takes (int legacyVolume, String filePath). AudioEngine instead defines play(String filename, int volume). The name and signature differ, and the argument order is reversed. LegacyAudioSystem reports failure with integer status codes, while AudioEngine uses AudioException.

LegacyAudioAdapter implements AudioEngine and wraps one LegacyAudioSystem. It calls startTrack(volume, filename), returns normally for success, and converts file-not-found, device-busy, unsupported-format, and unknown statuses to AudioException. It also wraps an unexpected runtime exception from the old call. No player class uses legacy codes.

## 4. Why Both Patterns Are Needed

Bridge separates player types from engine types, but it cannot by itself change the old method name, argument order, or status-code errors. Adapter makes the old system fit AudioEngine, but Adapter alone does not separate the two changing dimensions of player style and playback engine.

## 5. Open/Closed Principle

A new player, for example QuietPlayer, could extend MusicPlayer and call its AudioEngine at volume 20. The existing engine classes would not change. A new engine could implement AudioEngine and work with BasicPlayer and PartyPlayer without changing those classes. This is extension on both sides of the Bridge.

## 6. Required Complexity Module

Chosen module: Dynamic Implementor Selection. Main reads the engine name from a command-line argument, with local as the default. It passes that name to AudioEngineFactory.create. The factory constructs the matching AudioEngine for local, cloud, or legacy input. Main receives the interface type and passes it to the selected player; the concrete engine selection stays inside the factory.

## 7. Limitation

Adding another engine name requires a new case in AudioEngineFactory. The existing player and engine classes stay unchanged, but the factory is still a central place that must be edited.
