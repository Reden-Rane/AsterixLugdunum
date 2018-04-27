package fr.info.game;

public class Synchronizer {

    private static long variableYieldTime, lastTime;

    public static void sync(int fps) {
        if (fps <= 0) return;

        long sleepTime = 1000000000 / fps;
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
        long overSleep = 0;

        try {
            while (true) {
                long t = System.nanoTime() - lastTime;

                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if (t < sleepTime) {
                    Thread.yield();
                } else {
                    overSleep = t - sleepTime;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

            if (overSleep > variableYieldTime) {
                variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime);
            } else if (overSleep < variableYieldTime - 200 * 1000) {
                variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0);
            }
        }
    }


}
