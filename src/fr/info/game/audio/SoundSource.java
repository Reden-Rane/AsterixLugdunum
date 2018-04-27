package fr.info.game.audio;

import org.lwjgl.openal.AL10;

import static org.lwjgl.openal.AL10.*;

public class SoundSource {

    public final int sourcePointer;

    public SoundSource() {
        this.sourcePointer = alGenSources();
        AL10.alSourcef(sourcePointer, AL10.AL_GAIN, 1);
        AL10.alSourcef(sourcePointer, AL10.AL_PITCH, 1);
        AL10.alSource3f(sourcePointer, AL10.AL_POSITION, 0, 0, 0);
    }

    public void play(Sound sound) {
        AL10.alSourcei(sourcePointer, AL_BUFFER, sound.bufferPointer);
        AL10.alSourcei(sourcePointer, AL_LOOPING, AL_FALSE);
        AL10.alSourcePlay(sourcePointer);
    }

    public void loop(Sound sound) {
        AL10.alSourcei(sourcePointer, AL_BUFFER, sound.bufferPointer);
        AL10.alSourcei(sourcePointer, AL_LOOPING, AL_TRUE);
        AL10.alSourcePlay(sourcePointer);
    }

    public void stop() {
        AL10.alSourceStop(sourcePointer);
    }

    public void destroy() {
        alDeleteSources(sourcePointer);
    }
}
