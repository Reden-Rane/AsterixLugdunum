package fr.info.game.logic.level.hub;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.ScheduledTask;
import fr.info.game.audio.Sound;
import fr.info.game.logic.entity.Player;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.level.bridge.BridgeLevel;
import fr.info.game.logic.level.campus.CampusLevel;
import fr.info.game.logic.level.gates.GatesLevel;
import fr.info.game.logic.level.tavern.TavernLevel;
import fr.info.game.logic.path.PathNode;
import fr.info.game.logic.path.PathTraveller;

import static org.lwjgl.glfw.GLFW.*;

public class HubLevel extends Level {

    public final int mapWidth = 683;
    public final int mapHeight = 367;

    public final Player player = new Player();

    public final HubPath hubPath = new HubPath();
    private PathTraveller pathTraveller;

    private final HubLevelMarkerNode tavernMarker;
    private final HubLevelMarkerNode bridgeMarker;
    private final HubLevelMarkerNode gatesMarker;
    private final HubLevelMarkerNode campusMarker;

    private HubLevelMarkerNode currentNode;

    public HubLevel() {
        super("Hub");
        getCamera().setZoom(3);

        hubPath.appendNode(this.tavernMarker = new HubLevelMarkerNode(new TavernLevel(), 95, 48));
        hubPath.appendNode(new PathNode(160, 75));
        hubPath.appendNode(new PathNode(200, 50));
        hubPath.appendNode(new PathNode(255, 75));
        hubPath.appendNode(this.bridgeMarker = new HubLevelMarkerNode(new BridgeLevel(), 312, 47));
        hubPath.appendNode(new PathNode(370, 17));
        hubPath.appendNode(new PathNode(410, 29));
        hubPath.appendNode(new PathNode(411, 67));
        hubPath.appendNode(new PathNode(455, 90));
        hubPath.appendNode(new PathNode(455, 120));
        hubPath.appendNode(this.gatesMarker = new HubLevelMarkerNode(new GatesLevel(), 405, 150));
        hubPath.appendNode(this.campusMarker = new HubLevelMarkerNode(new CampusLevel(), 405, 250));

        this.currentNode = this.tavernMarker;

        player.setX(this.tavernMarker.x);
        player.setY(this.tavernMarker.y);
        spawnEntity(player);

    }

    @Override
    public void update() {

        if (getLevelTicks() == 0) {
            openLevel(20, true);
        }

        if (getLevelTicks() == 10) {
            startPlayingHubMusic();
        }

        player.setMotionX(0);
        player.setMotionY(0);

        if (pathTraveller != null) {
            pathTraveller.update();

            if (pathTraveller.isFinished()) {
                this.currentNode = (HubLevelMarkerNode) pathTraveller.getEndNode();
                pathTraveller = null;
            }
        } else {
            if (KeyboardCallback.isKeyDown(GLFW_KEY_D)) {

                HubLevelMarkerNode nextNode = hubPath.getNextLevelMarker(this.currentNode);

                if (nextNode != null) {
                    pathTraveller = new HubPathTraveller(player, this, getCurrentNode(), nextNode, 2.5F);
                }
            } else if (KeyboardCallback.isKeyDown(GLFW_KEY_A)) {

                HubLevelMarkerNode prevNode = hubPath.getPreviousLevelMarker(this.currentNode);

                if (prevNode != null) {
                    pathTraveller = new HubPathTraveller(player, this, getCurrentNode(), prevNode, 2.5F);
                }
            } else if (KeyboardCallback.isKeyDown(GLFW_KEY_ENTER)) {
                enterLevel(getCurrentNode().level);
            } else if(KeyboardCallback.isKeyDown(GLFW_KEY_B)) {//TODO Remove this
                enterLevel(bridgeMarker.level);
            }
        }

        super.update();
    }

    public void startPlayingHubMusic() {
        Sound backgroundMusic = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("hub");
        if (backgroundMusic != null) {
            AsterixAndObelixGame.INSTANCE.getAudioManager().getMusicSource().loop(backgroundMusic);
        }
    }

    private void enterLevel(Level level) {
        AsterixAndObelixGame.INSTANCE.addScheduledTask(new ScheduledTask(0, 20) {

            @Override
            protected void updateTask() {
                if(getTicks() == 0) {
                    closeLevel(20);
                } else if (getTicks() == getDuration()) {
                    level.resetLevel();
                    AsterixAndObelixGame.INSTANCE.setCurrentLevel(level);
                    level.openLevel(20, true);
                }
            }
        });
    }

    public HubLevelMarkerNode getCurrentNode() {
        return currentNode;
    }
}
