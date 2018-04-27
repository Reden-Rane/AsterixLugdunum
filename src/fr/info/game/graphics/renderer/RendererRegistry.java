package fr.info.game.graphics.renderer;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.renderer.entity.PlayerRenderer;
import fr.info.game.graphics.renderer.level.*;
import fr.info.game.graphics.renderer.level.introduction.*;
import fr.info.game.logic.entity.Player;
import fr.info.game.logic.level.bridge.BridgeLevel;
import fr.info.game.logic.level.campus.CampusLevel;
import fr.info.game.logic.level.gates.GatesLevel;
import fr.info.game.logic.level.hub.HubLevel;
import fr.info.game.logic.level.introduction.IntroductionGameBrand;
import fr.info.game.logic.level.introduction.IntroductionGameTitle;
import fr.info.game.logic.level.introduction.IntroductionLevel;
import fr.info.game.logic.level.introduction.credits.IntroductionCredit;
import fr.info.game.logic.level.introduction.credits.IntroductionCreditDev;
import fr.info.game.logic.level.introduction.credits.IntroductionCredits;
import fr.info.game.logic.level.tavern.TavernLevel;

import java.util.HashMap;

public class RendererRegistry {

    private final HashMap<Class, Renderer> renderersRegistry = new HashMap<>();

    public RendererRegistry(RenderManager renderManager) {
        renderersRegistry.put(Player.class, new PlayerRenderer(renderManager));
        renderersRegistry.put(HubLevel.class, new HubLevelRenderer(renderManager));

        renderersRegistry.put(IntroductionLevel.class, new IntroductionLevelRenderer(renderManager));
        renderersRegistry.put(IntroductionGameBrand.class, new IntroductionGameBrandRenderer(renderManager));
        renderersRegistry.put(IntroductionCredits.class, new IntroductionCreditsRenderer(renderManager));
        renderersRegistry.put(IntroductionCredit.class, new IntroductionCreditRenderer(renderManager));
        renderersRegistry.put(IntroductionCreditDev.class, new IntroductionCreditDevRenderer(renderManager));
        renderersRegistry.put(IntroductionGameTitle.class, new IntroductionGameTitleRenderer(renderManager));

        renderersRegistry.put(TavernLevel.class, new TavernRenderer(renderManager));
        renderersRegistry.put(BridgeLevel.class, new BridgeRenderer(renderManager));
        renderersRegistry.put(GatesLevel.class, new GatesRenderer(renderManager));
        renderersRegistry.put(CampusLevel.class, new CampusRenderer(renderManager));
    }

    public <T> Renderer<T> getRenderer(T obj) {
        Class<?> cls = obj.getClass();

        do {
            if (renderersRegistry.containsKey(cls)) {
                return renderersRegistry.get(cls);
            }
        } while ((cls = cls.getSuperclass()) != null);

        return null;
    }
}