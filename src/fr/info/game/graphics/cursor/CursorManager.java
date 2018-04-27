package fr.info.game.graphics.cursor;

import fr.info.game.assets.TextureResource;
import fr.info.game.graphics.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class CursorManager {

    private final List<Cursor> loadedCursors = new ArrayList<>();

    public final Cursor normalCursor;

    public CursorManager() {
        this.normalCursor = loadCursor("gui/cursor/cursor_normal.png", 0, 0);
    }

    private Cursor loadCursor(String path, int xHot, int yHot) {
        Cursor cursor = new Cursor(new Texture(new TextureResource(path)), xHot, yHot);
        loadedCursors.add(cursor);
        return cursor;
    }

    public void destroy() {
        for (Cursor cursor : loadedCursors) {
            cursor.destroy();
        }
    }

}
