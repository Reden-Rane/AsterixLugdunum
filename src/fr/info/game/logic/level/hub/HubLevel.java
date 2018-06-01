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
import fr.info.game.logic.path.Node;
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

        hubPath.appendNode(this.tavernMarker = new HubLevelMarkerNode(new TavernLevel(), 3, 1.5F));
        hubPath.appendNode(new Node(5, 2.34F));
        hubPath.appendNode(new Node(6.25F, 1.56F));
        hubPath.appendNode(new Node(8, 2.34F));
        hubPath.appendNode(this.bridgeMarker = new HubLevelMarkerNode(new BridgeLevel(), 9.75F, 1.5F));
        hubPath.appendNode(new Node(11.6F, 0.53F));
        hubPath.appendNode(new Node(12.8F, 0.91F));
        hubPath.appendNode(new Node(12.84F, 2.1F));
        hubPath.appendNode(new Node(14.2F, 2.81F));
        hubPath.appendNode(new Node(14.2F, 3.75F));
        hubPath.appendNode(this.gatesMarker = new HubLevelMarkerNode(new GatesLevel(), 12.66F, 4.69F));
        hubPath.appendNode(this.campusMarker = new HubLevelMarkerNode(new CampusLevel(), 12.66F, 7.81F));

        this.currentNode = this.tavernMarker;

        player.x = this.tavernMarker.x;
        player.y = this.tavernMarker.y;
        spawnEntity(player);
    }

    @Override
    public void update() {

        if (getLevelTicks() == 0) {
            openLevel(15, true);
        }

        if (getLevelTicks() == 10) {
            startPlayingHubMusic();
        }

        player.motionX = 0;
        player.motionY = 0;

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
                    pathTraveller = new HubPathTraveller(player, this, getCurrentNode(), nextNode, 0.07F);
                }
            } else if (KeyboardCallback.isKeyDown(GLFW_KEY_A)) {

                HubLevelMarkerNode prevNode = hubPath.getPreviousLevelMarker(this.currentNode);

                if (prevNode != null) {
                    pathTraveller = new HubPathTraveller(player, this, getCurrentNode(), prevNode, 0.07F);
                }
            } else if (KeyboardCallback.isKeyDown(GLFW_KEY_ENTER)) {
                enterLevel(getCurrentNode().level);
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
        AsterixAndObelixGame.INSTANCE.addScheduledTask(new ScheduledTask(0, 15) {

            @Override
            protected void updateTask() {
                if(getTicks() == 0) {
                    closeLevel(15);
                } else if (getTicks() == getDuration()) {
                    level.resetLevel();
                    AsterixAndObelixGame.INSTANCE.setCurrentLevel(level);
                    level.openLevel(15, true);
                }
            }
        });
    }

    public HubLevelMarkerNode getCurrentNode() {
        return currentNode;
    }
}
