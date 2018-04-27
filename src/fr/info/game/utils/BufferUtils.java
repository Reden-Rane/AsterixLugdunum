package fr.info.game.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

	/**
	 * Convert a byte array into a {@link ByteBuffer}
	 *
	 * @param array The array containing the data
	 * @return Return the created {@link ByteBuffer}
	 */
	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}

    /**
     * Convert a float array into a {@link FloatBuffer}
     *
     * @param array The array containing the data
     * @return Return the created {@link FloatBuffer}
     */
	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}

    /**
     * Convert a int array into a {@link IntBuffer}
     *
     * @param array The array containing the data
     * @return Return the created {@link IntBuffer}
     */
	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}

}
