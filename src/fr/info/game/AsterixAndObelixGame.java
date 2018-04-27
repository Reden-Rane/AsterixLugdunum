package fr.info.game;

import fr.info.game.audio.AudioManager;
import fr.info.game.exception.GameException;
import fr.info.game.exception.render.TextureLoadingException;
import fr.info.game.graphics.RenderManager;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.input.MouseButtonCallback;
import fr.info.game.logic.input.MousePositionCallback;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.level.hub.HubLevel;
import fr.info.game.logic.level.introduction.IntroductionLevel;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * The main game class, containing the main method in order to launch the game
 * and initialize the libraries required (GLFW, OpenGL, OpenAL).
 */
public class AsterixAndObelixGame implements Runnable {

    public static AsterixAndObelixGame INSTANCE;

    public static final String GAME_TITLE = "Astérix et Obélix à l'INSA Lugdunum";
    public static final int TICKS_PER_SECOND = 20;

    /*
    All the paths to the different resources types of the game
     */

    public static final String SHADERS_PATH = "assets/shaders/";
    public static final String TEXTURES_PATH = "assets/textures/";
    public static final String SOUNDS_PATH = "assets/sounds/";
    public static final String LANG_PATH = "assets/lang/";
    public static final String FONT_PATH = "assets/fonts/";

    private Thread gameThread;
    private Display display;
    private AudioManager audioManager;
    private RenderManager renderManager;

    private HubLevel hubLevel;
    private Level currentLevel;

    private final List<ScheduledTask> queuedScheduledTasks = new ArrayList<>();
    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();

    /**
     * Main method of the program
     *
     * @param args
     */
    public static void main(String[] args) {
        new AsterixAndObelixGame().start();
    }

    public AsterixAndObelixGame() {
        AsterixAndObelixGame.INSTANCE = this;
    }

    public void start() {
        this.gameThread = new Thread(this, "GameThread");
        this.gameThread.start();
    }

    public void run() {
        this.initGame();
        this.runGameLoop();
        this.renderManager.destroy();
        this.display.destroy();
        this.audioManager.destroy();
        glfwTerminate();
    }

    /**
     * Initialize the context for the game. Including the window, the audio, the renderer, and callbacks for inputs.
     *
     * @throws IOException
     * @throws GameException
     * @throws UnsupportedAudioFileException
     */
    private void initGame() {
        if (!glfwInit()) {
            System.err.println("Could not initialize GLFW!");
            return;
        }

        try {
            this.display = new Display(GAME_TITLE, 1280, 720);
        } catch (GameException e) {
            e.printStackTrace();
        }

        initInputCallbacks();

        try {
            this.audioManager = new AudioManager();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        try {
            this.renderManager = new RenderManager();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TextureLoadingException e) {
            e.printStackTrace();
        }

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
    }

    private void initInputCallbacks() {
        glfwSetKeyCallback(this.display.windowID, new KeyboardCallback());
        glfwSetMouseButtonCallback(this.display.windowID, new MouseButtonCallback());
        glfwSetCursorPosCallback(this.display.windowID, new MousePositionCallback());
    }

    /**
     * The game loop is the method which allows the game to be updated.
     * It also allows the game to be rendered.
     */
    private void runGameLoop() {

        //Start the game with the introduction level
        setCurrentLevel(new IntroductionLevel());
        this.hubLevel = new HubLevel();

        long lastTime = System.currentTimeMillis();
        long elapsed = 0;
        float period = 1000F / TICKS_PER_SECOND;
        long timer = System.currentTimeMillis();
        int frames = 0;

        /*
          Run the game loop until the window is forced to be closed
         */
        while (!this.display.shouldClose()) {
            long startTime = System.currentTimeMillis();
            glfwPollEvents();
            elapsed += System.currentTimeMillis() - lastTime;
            lastTime = startTime;

            if (elapsed >= period) {
                //Tick the game, this will update all the logic part of the game (world, entities, etc)
                update();
                elapsed -= period;
            }

            //Render the game at every iteration, allowing to have the maximum FPS possible
            //The partialTicks is an argument used for graphics interpolation
            render(elapsed / period);
            frames++;

            //Update the game title to display the number of FPS
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.display.setTitle(AsterixAndObelixGame.GAME_TITLE + " | " + frames + " FPS");
                frames = 0;
            }

            //Avoids the CPU to be overused. Pauses the thread in order to skip useless render calls.
            Synchronizer.sync(60);
        }
    }

    /**
     * This method update the game's logic
     */
    private void update() {
        //Poll the events and updates the input callbacks
        glfwPollEvents();

        updateScheduledTasks();

        if (this.currentLevel != null) {
            this.currentLevel.update();
        }
    }

    private void updateScheduledTasks() {
        scheduledTasks.addAll(queuedScheduledTasks);
        queuedScheduledTasks.clear();

        Iterator<ScheduledTask> scheduledTaskIterator = scheduledTasks.iterator();

        while (scheduledTaskIterator.hasNext()) {
            ScheduledTask task = scheduledTaskIterator.next();
            task.update();
            if(task.isFinished()) {
                scheduledTaskIterator.remove();
            }
        }
    }

    /**
     * This method graphics the game
     *
     * @param partialTicks This argument allows computing some interpolation for a smooth graphics
     */
    private void render(float partialTicks) {

        if (this.currentLevel != null) {
            //Render the level
            this.renderManager.render(currentLevel, partialTicks);
        }

        checkRenderErrors();
        //Swap the buffers of the window to see it on the screen
        glfwSwapBuffers(this.display.windowID);
    }

    private void checkRenderErrors() {
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.err.println("OpenGL Error: " + error);
        }
    }

    public void enterHub() {
        if(getCurrentLevel() != hubLevel) {
            setCurrentLevel(hubLevel);
            hubLevel.openLevel(20, true);
        }
    }

    public void addScheduledTask(ScheduledTask task) {
        this.queuedScheduledTasks.add(task);
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Display getDisplay() {
        return display;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
