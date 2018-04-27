package fr.info.game.audio;

import com.sun.media.sound.WaveFileReader;
import fr.info.game.assets.SoundResource;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;

public class Sound {

    public final int bufferPointer;

    private Sound(int bufferPointer) {
        this.bufferPointer = bufferPointer;
    }

    public static Sound createSound(SoundResource sound) throws IOException, UnsupportedAudioFileException {
        int bufferPointer = alGenBuffers();
        WaveFileReader wfr = new WaveFileReader();

        InputStream stream = sound.getResourceAsStream();

        if(stream == null) {
            throw new IllegalStateException("Can't open sound file " + sound.getPath());
        }

        AudioInputStream ais = wfr.getAudioInputStream(sound.getResourceAsStream());

        AudioFormat format = ais.getFormat();
        int channels = 0;

        if (format.getChannels() == 1) {
            if (format.getSampleSizeInBits() == 8) {
                channels = AL10.AL_FORMAT_MONO8;
            } else if (format.getSampleSizeInBits() == 16) {
                channels = AL10.AL_FORMAT_MONO16;
            }
        } else if (format.getChannels() == 2) {
            if (format.getSampleSizeInBits() == 8) {
                channels = AL10.AL_FORMAT_STEREO8;
            } else if (format.getSampleSizeInBits() == 16) {
                channels = AL10.AL_FORMAT_STEREO16;
            }
        }

        ByteBuffer data;
        try {
            int available = ais.available();
            if (available <= 0) {
                available = ais.getFormat().getChannels() * (int) ais.getFrameLength() * ais.getFormat().getSampleSizeInBits() / 8;
            }
            byte[] buf = new byte[ais.available()];
            int read = 0, total = 0;
            while ((read = ais.read(buf, total, buf.length - total)) != -1
                    && total < buf.length) {
                total += read;
            }
            data = convertAudioBytes(buf, format.getSampleSizeInBits() == 16, format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        } catch (IOException ioe) {
            return null;
        }

        ais.close();
        alBufferData(bufferPointer, channels, data, (int) format.getSampleRate());
        data.clear();
        return new Sound(bufferPointer);
    }


    private static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data, ByteOrder order) {
        ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
        dest.order(ByteOrder.nativeOrder());
        ByteBuffer src = ByteBuffer.wrap(audio_bytes);
        src.order(order);
        if (two_bytes_data) {
            ShortBuffer dest_short = dest.asShortBuffer();
            ShortBuffer src_short = src.asShortBuffer();
            while (src_short.hasRemaining())
                dest_short.put(src_short.get());
        } else {
            while (src.hasRemaining())
                dest.put(src.get());
        }
        dest.rewind();
        return dest;
    }

    public void destroy() {
        alDeleteBuffers(bufferPointer);
    }
}
