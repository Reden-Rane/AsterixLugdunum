package fr.info.game.graphics.texture;

import fr.info.game.assets.TextureResource;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private static final int BYTES_PER_PIXEL = 4;

    private int texture;
    private int width, height;
    private ByteBuffer imageData;

    public Texture(TextureResource texture) {
        this(texture, GL_NEAREST, GL_NEAREST);
    }

    public Texture(TextureResource texture, int minFilter, int magFilter) {
        this.texture = load(texture, minFilter, magFilter);
    }

    public Texture(BufferedImage image, int minFilter, int magFilter) {
        this.texture = load(image, minFilter, magFilter);
    }

    private int load(TextureResource texture, int minFilter, int magFilter) {
        try {
            InputStream stream = texture.getResourceAsStream();
            BufferedImage image = ImageIO.read(stream);
            int textureID = load(image, minFilter, magFilter);
            stream.close();
            return textureID;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int load(BufferedImage image, int minFilter, int magFilter) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        int[] pixels = new int[this.width * this.height];
        image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);

        this.imageData = BufferUtils.createByteBuffer(this.width * this.height * BYTES_PER_PIXEL);

        for (int i = 0; i < this.width * this.height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);
            this.imageData.putInt(a << 24 | b << 16 | g << 8 | r);
        }

        this.imageData.flip();
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void destroy() {
        glDeleteTextures(texture);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getImageData() {
        return imageData;
    }
}
