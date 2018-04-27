package fr.info.game.logic.math;

public class MathUtils {

    /**
     * Linear interpolation method, this is used to get a value in-between two bounds for the given progression.
     * This is mainly used for rendering smooth animations.
     * @return Return the interpolated value
     */
    public static float lerp(float a, float b, float p) {
        return a + (b - a) * p;
    }

    /**
     * Clamp a double value between two bounds.
     * @param x The value to clamp.
     * @param min The minimum bound.
     * @param max The maximum bound.
     * @return Return the clamped value.
     */
    public static double clamp(double x, double min, double max) {
        if(x < min) {
            return min;
        } else if(x > max) {
            return max;
        } else {
            return x;
        }
    }

    /**
     * Clamp a float value between two bounds.
     * @param x The value to clamp.
     * @param min The minimum bound.
     * @param max The maximum bound.
     * @return Return the clamped value.
     */
    public static float clamp(float x, float min, float max) {
        return (float) clamp((double) x, (double) min, (double) max);
    }

    /**
     * Clamp a long value between two bounds.
     * @param x The value to clamp.
     * @param min The minimum bound.
     * @param max The maximum bound.
     * @return Return the clamped value.
     */
    public static long clamp(long x, long min, long max) {
        return (long) clamp((double) x, (double) min, (double) max);
    }

    /**
     * Clamp an int value between two bounds.
     * @param x The value to clamp.
     * @param min The minimum bound.
     * @param max The maximum bound.
     * @return Return the clamped value.
     */
    public static long clamp(int x, int min, int max) {
        return (int) clamp((double) x, (double) min, (double) max);
    }

}
