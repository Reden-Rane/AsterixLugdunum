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
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * The main game class, containing the main method in order to launch the game
 * and initialize the libraries required (GLFW, OpenGL, OpenAL).
 * <p>
 * La classe principale du jeu, elle contient le Main pour lancer le jeu, initialiser
 * les librairies nécessaires (GLFW et OpenGL pour le moteur graphique, OpenAL pour le son)
 */
public class AsterixAndObelixGame implements Runnable {

    /**
     * Those constants defines the resolution of the game for the render engine
     * <p>
     * Ces constantes définissent la résolution du jeu pour le moteur graphique
     */
    public static final int RESOLUTION_X = 1920;
    public static final int RESOLUTION_Y = 1080;

    /**
     * The JSwing launcher used to show the loading progress of the game
     */
    public static Launcher LAUNCHER = new Launcher();

    /**
     * Stores the game's instance (useful to access render-engine related classes and others)
     * <p>
     * Contient l'instance du jeu (utile pour accéder aux classes du moteur graphique et autres)
     */
    public static AsterixAndObelixGame INSTANCE;

    public static final String GAME_TITLE = "Astérix et Obélix à l'INSA Lugdunum";
    private static final int TICKS_PER_SECOND = 20;
    private static final int FRAMES_PER_SECOND = 60;

    /*
    All the paths to the different resources types of the game

    Chemins d'accès vers les différentes ressources du jeu
     */
    public static final String SHADERS_PATH = "assets/shaders/";
    public static final String TEXTURES_PATH = "assets/textures/";
    public static final String SOUNDS_PATH = "assets/sounds/";
    public static final String LANG_PATH = "assets/lang/";
    public static final String FONT_PATH = "assets/fonts/";
    public static final String MAPS_PATH = "assets/maps/";

    /**
     * The thread of the game, updated 60 times per second for the render and 20 times per second for the logic-loop
     * <p>
     * Le processus du jeu, mis à jour 60 fois par seconde pour le rendu et 20 fois par seconde pour la boucle logique
     */
    private Thread gameThread;

    /**
     * Contains the information about the display that we render the game at (width, height, ...)
     * <p>
     * Contient les informations sur l'écran sur lequel on dessinera (largeur, hauteur, ...)
     */
    private Display display;

    /**
     * Used to manage sound-effects and musics in the game
     * <p>
     * Sert à gérer les effets de sons et musiques du jeu
     */
    private AudioManager audioManager;
    /**
     * Used to render the game (terrain, entities, GUIs, ...)
     * <p>
     * Sert à faire le rendu du jeu (monde, entités, interfaces graphiques, ...)
     */
    private RenderManager renderManager;

    /**
     * The hub level instance, this is being stored as a separate variable since we need it every time the player leaves a level
     * <p>
     * Le niveau du hub, stocké comme une variable indépendante car on en a besoin à chaque sortie de niveau
     *
     * @see #enterHub()
     */
    private HubLevel hubLevel;

    /**
     * The current level the player is in
     * <p>
     * Le level actuel dans lequel le joueur se trouve
     */
    private Level currentLevel;

    /**
     * Scheduled tasks that need to be executed when the update loop is reached
     * <p>
     * Tâches planifiées à executer quand la méthode de mise-à-jour est appelée
     *
     * @see #update()
     */
    private final List<ScheduledTask> queuedScheduledTasks = new ArrayList<>();
    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();

    /**
     * The number of modules to load
     *
     * Le nombre de modules qu'on charge pour le jeu
     */
    public static final int LOADING_UNITS = 6;

    public int loadedUnits;

    /**
     * Main method of the program, called at the very beginning
     * <p>
     * Méthode principale du programme, appelée au tout début
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
        We create the game instance and we start the thread

        On crée l'instance du jeu et on lance le processus
         */

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                LAUNCHER = new Launcher();
                LAUNCHER.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        new AsterixAndObelixGame().start();
    }

    public AsterixAndObelixGame() {
        AsterixAndObelixGame.INSTANCE = this;
    }

    private void start() {
        this.gameThread = new Thread(this, "AsterixAndObelixThread");
        this.gameThread.start();
    }

    /**
     * This method is called when the thread starts
     * <p>
     * Cette méthode est appelée quand le processus est démarré
     *
     * @see #gameThread
     */
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
     * <p>
     * Initialise le contexte du jeu. Les informations sur la fenêtre, l'audio, le moteur graphique, les évènements pour les entrées clavier/souris.
     */
    private void initGame() {
        initializeGLFW();
        initializeDisplay();
        initializeInputCallbacks();
        initializeAudio();
        initializeRender();
        LAUNCHER.setProgressText("Initialized! Launching the game...");
        System.out.println("Game initialized.");
        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));

        LAUNCHER.setVisible(false);
        getDisplay().show();
    }

    private void updateProgress() {
        loadedUnits++;
        LAUNCHER.setProgress(loadedUnits / (float) LOADING_UNITS);
    }

    private void initializeGLFW() {
        LAUNCHER.setProgressText("Initializing GLFW...");
        if (!glfwInit()) {
            throw new IllegalStateException("Could not initialize GLFW!");
        } else {
            System.out.println("Initialized GLFW.");
        }
        updateProgress();
    }

    private void initializeDisplay() {
        LAUNCHER.setProgressText("Initializing display...");
        try {
            this.display = new Display(GAME_TITLE, RESOLUTION_X, RESOLUTION_Y);
        } catch (GameException e) {
            e.printStackTrace();
        }
        System.out.println("Initialized display.");
        updateProgress();
    }

    private void initializeInputCallbacks() {
        LAUNCHER.setProgressText("Initializing input callbacks...");
        glfwSetKeyCallback(this.display.windowID, new KeyboardCallback());
        glfwSetMouseButtonCallback(this.display.windowID, new MouseButtonCallback());
        glfwSetCursorPosCallback(this.display.windowID, new MousePositionCallback());
        System.out.println("Initialized input callbacks.");
        updateProgress();
    }

    private void initializeAudio() {
        LAUNCHER.setProgressText("Initializing audio...");
        try {
            this.audioManager = new AudioManager();
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        System.out.println("Initialized audio manager.");
        updateProgress();
    }

    private void initializeRender() {
        LAUNCHER.setProgressText("Initializing render...");
        try {
            this.renderManager = new RenderManager();
        } catch (IOException | TextureLoadingException e) {
            e.printStackTrace();
        }
        System.out.println("Initialized render manager.");
        updateProgress();
    }

    /**
     * The game loop is the method which allows the game to be updated and rendered given a certain frequency.
     * <p>
     * La boucle du jeu est la méthode qui permet de raffraîchir le jeu (logique et rendu) à une fréquence donnée
     *
     * @see #TICKS_PER_SECOND
     * @see #FRAMES_PER_SECOND
     */
    private void runGameLoop() {

        /*
        Start the game with the introduction level

        Démarre le jeu avec le niveau d'introduction
         */
        setCurrentLevel(new IntroductionLevel());
        this.hubLevel = new HubLevel();

        long lastTime = System.currentTimeMillis();
        long elapsed = 0;
        float period = 1000F / TICKS_PER_SECOND;
        long timer = System.currentTimeMillis();
        int frames = 0;

        /*
          Run the game loop until the window is forced to be closed

          Continue le raffraîchissement jusqu'à ce que la fenêtre est fermée
         */
        while (!this.display.shouldClose()) {

            long startTime = System.currentTimeMillis();
            /*
             * We add the elapsed time between this loop and the last one, this corresponds to the time the last loop took to be updated
             *
             * On ajoute le temps écoulé entre cette boucle et le début de la précédente. Cela correspond au temps qu'elle a mis pour se raffraîchir
             */
            elapsed += System.currentTimeMillis() - lastTime;
            lastTime = startTime;

            /*
             * If the elapsed time is superior to the update period, we update the game
             * Si le temps écoulé est supérieur à la période de raffraîchissement du jeu, on le met à jour
             */
            if (elapsed >= period) {
                //Tick the game, this will update all the logic part of the game (world, entities, etc)
                update();
                elapsed = 0;
//                elapsed -= period;
            }

            /*
             * Render the game at every iteration (60fps),
             * The partialTicks is a variable used for graphics interpolation.
             *
             * Dessine le jeu à chaque itération (60 images par seconde)
             * Le partialTicks est une variable utilisée pour faire des rendus fluides (par interpolation) sur les déplacements par exemple.
             */
            render(elapsed / period);
            frames++;

            /*
             * Update the game title to display the number of FPS
             *
             * Met à jour le titre de la fenêtre pour y afficher le nombre d'images par seconde
             */
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.display.setTitle(AsterixAndObelixGame.GAME_TITLE + " | " + frames + " FPS");
                frames = 0;
            }

            /*
             * Avoids the CPU to be overused. Pauses the thread in order to skip useless render calls.
             *
             * Evite le processeur d'être sur-utilisé inutilement. Met en pause le processus pour éviter des rendus inutiles (> 60fps inutile sur des écrans <= 60Hz).
             */
            Synchronizer.sync(FRAMES_PER_SECOND);
        }

        System.out.println("Closing game...");
        LAUNCHER.close();
    }

    /**
     * This method update the game's logic
     * <p>
     * Met à jour la logique du jeu (monde, entités,
     */
    private void update() {
        /*
         * Check for any keyboard/mouse event, call the associated callback if necessary
         *
         * Vérifie si une touche du clavier ou un bouton de la souris est actionné, appelle la classe d'évènement liée si besoin
         */
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
            if (task.isFinished()) {
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
            //Render the levelClass
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

    /**
     * Move the player to the HubLevel
     * <p>
     * Déplace le joueur vers le niveau hub
     *
     * @see #hubLevel
     */
    public void enterHub() {
        if (getCurrentLevel() != hubLevel) {
            setCurrentLevel(hubLevel);
            hubLevel.openLevel(15, true);
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
