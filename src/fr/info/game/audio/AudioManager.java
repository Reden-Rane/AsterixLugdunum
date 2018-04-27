package fr.info.game.audio;

import fr.info.game.assets.SoundResource;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static fr.info.game.audio.Sound.createSound;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioManager {

    private final long device;
    private final long context;

    private final HashMap<String, Sound> sounds = new HashMap<>();
    private final SoundSource musicSource;
    private final List<SoundSource> sources = new ArrayList<>();

    public AudioManager() throws IOException, UnsupportedAudioFileException {

        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        this.device = alcOpenDevice(defaultDeviceName);

        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default device.");
        }

        this.context = alcCreateContext(device, (IntBuffer) null);

        if (context == NULL) {
            throw new IllegalStateException("Failed to create an OpenAL context.");
        }

        alcMakeContextCurrent(context);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        AL.createCapabilities(alcCapabilities);

        setListenerData(new Vector3f(), new Vector3f());

        loadSounds();
        this.musicSource = new SoundSource();

        System.out.println("Initialized sound system.");
    }

    public void setListenerData(Vector3f listenerPos, Vector3f listenerVelocity) {
        AL10.alListener3f(AL10.AL_POSITION, listenerPos.x, listenerPos.y, listenerPos.z);
        AL10.alListener3f(AL10.AL_VELOCITY, listenerVelocity.x, listenerPos.y, listenerPos.z);
    }

    private void loadSounds() throws IOException, UnsupportedAudioFileException {
        loadSound("intro", new SoundResource("intro.wav"));
        loadSound("hub", new SoundResource("hub.wav"));
        loadSound("opening_world", new SoundResource("opening_world.wav"));
    }

    private void loadSound(String name, SoundResource soundResource) throws IOException, UnsupportedAudioFileException {
        if (this.sounds.containsKey(name)) {
            System.err.println("Sound with name " + name + " already registered.");
        } else {
            this.sounds.put(name, createSound(soundResource));
        }
    }

    public SoundSource createSoundSource() {
        SoundSource source = new SoundSource();
        sources.add(source);
        return source;
    }

    public void destroy() {
        for (Sound sound : this.sounds.values()) {
            sound.destroy();
        }
        for (SoundSource source : sources) {
            source.destroy();
        }
        this.musicSource.destroy();
        alcDestroyContext(this.context);
        alcCloseDevice(this.device);
    }

    public Sound getSound(String soundName) {
        for (String name : sounds.keySet()) {
            if (name.equals(soundName)) {
                return sounds.get(name);
            }
        }
        return null;
    }

    public SoundSource getMusicSource() {
        return musicSource;
    }
}
