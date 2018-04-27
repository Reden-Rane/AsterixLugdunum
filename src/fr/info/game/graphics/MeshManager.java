package fr.info.game.graphics;

import java.util.ArrayList;
import java.util.List;

public class MeshManager {

    private final List<VertexArray> loadedMeshes = new ArrayList<>();

    public final VertexArray rectangle;
    public final VertexArray texturedRectangle;

    public MeshManager() {
        this.rectangle = loadMesh(new float[]{0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0}, new byte[]{0, 1, 2, 2, 3, 0});
        this.texturedRectangle = loadMesh(new float[]{0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0}, new byte[]{0, 1, 2, 2, 3, 0}, new float[]{0, 0, 1, 0, 1, 1, 0, 1});
    }

    public VertexArray loadMesh(float[] vertices, byte[] indices) {
        return loadMesh(vertices, indices, null);
    }

    public VertexArray loadMesh(float[] vertices, byte[] indices, float[] textureCoordinates) {
        VertexArray vertexArray = new VertexArray(vertices, indices, textureCoordinates);
        loadedMeshes.add(vertexArray);
        return vertexArray;
    }

    public void destroy() {
        for (VertexArray mesh : loadedMeshes) {
            mesh.destroy();
        }
    }
}
