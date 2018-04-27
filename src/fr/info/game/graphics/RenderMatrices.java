package fr.info.game.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static fr.info.game.graphics.RenderMatrices.EnumMatrixMode.*;

public class RenderMatrices {

    private static List<Matrix4f> projectionMatrices = new ArrayList<>();
    private static List<Matrix4f> viewMatrices = new ArrayList<>();
    private static List<Matrix4f> modelMatrices = new ArrayList<>();

    private static EnumMatrixMode matrixMode = EnumMatrixMode.PROJECTION;

    public static void init(RenderManager renderManager) {
        projectionMatrices.add(new Matrix4f());
        viewMatrices.add(new Matrix4f());
        modelMatrices.add(new Matrix4f());
        renderManager.updateProjectionMatrix();
    }

    /**
     * Add a copy of the current matrix on the stack to modify it without interfering with the others
     *
     * @return Return the matrix created
     */
    public static Matrix4f pushMatrix(EnumMatrixMode matrixMode) {
        switch (matrixMode) {
            case VIEW:
                viewMatrices.add(0, new Matrix4f(getMatrix()));
                return getMatrix();
            case PROJECTION:
                projectionMatrices.add(0, new Matrix4f(getMatrix()));
                return getMatrix();
            case MODEL:
                modelMatrices.add(0, new Matrix4f(getMatrix()));
                return getMatrix();
            default:
                return null;
        }
    }

    /**
     * Remove the current matrix from the stack
     *
     * @return Return the popped matrix
     */
    public static Matrix4f popMatrix(EnumMatrixMode matrixMode) {
        switch (matrixMode) {
            case VIEW:
                if (viewMatrices.size() <= 1) {
                    System.err.println("No more view matrix can be popped.");
                    return null;
                }
                return viewMatrices.remove(0);
            case PROJECTION:
                if (projectionMatrices.size() <= 1) {
                    System.err.println("No more projection matrix can be popped.");
                    return null;
                }
                return projectionMatrices.remove(0);
            case MODEL:
                if (modelMatrices.size() <= 1) {
                    System.err.println("No more model matrix can be popped.");
                    return null;
                }
                return modelMatrices.remove(0);
            default:
                return null;
        }
    }

    public static void scale(float x, float y, float z) {
        updateMatrix(getMatrix(), getMatrix().scale(x, y, z));
    }

    public static void translate(float x, float y, float z) {
        updateMatrix(getMatrix(), getMatrix().translate(x, y, z));
    }

    public static void rotate(float angle, Vector3f axis) {
        updateMatrix(getMatrix(), getMatrix().rotate(angle, axis));
    }

    public static void rotate(Quaternionfc quaternion) {
        updateMatrix(getMatrix(), getMatrix().rotate(quaternion));
    }

    public static void updateMatrix(Matrix4f mat, Matrix4f newMat) {
        mat.set(newMat);
    }

    public static Matrix4f getModelMatrix() {
        return modelMatrices.get(0);
    }

    public static Matrix4f getViewMatrix() {
        return viewMatrices.get(0);
    }

    public static Matrix4f getProjectionMatrix() {
        return projectionMatrices.get(0);
    }

    public static EnumMatrixMode getMatrixType(Matrix4f m) {
        if (viewMatrices.contains(m)) {
            return VIEW;
        } else if (projectionMatrices.contains(m)) {
            return PROJECTION;
        } else {
            return MODEL;
        }
    }

    public static List<Matrix4f> getMatrices(EnumMatrixMode matrixMode) {
        switch (matrixMode) {
            case VIEW:
                return viewMatrices;
            case PROJECTION:
                return projectionMatrices;
            case MODEL:
                return modelMatrices;
            default:
                return null;
        }
    }

    public static Matrix4f getMatrix() {
        return matrixMode.getMatrix();
    }

    public static void setMatrixMode(EnumMatrixMode matrixMode) {
        RenderMatrices.matrixMode = matrixMode;
    }

    public static EnumMatrixMode getMatrixMode() {
        return matrixMode;
    }

    public enum EnumMatrixMode {
        VIEW,
        PROJECTION,
        MODEL;

        private Matrix4f getMatrix() {
            switch (this) {
                case VIEW:
                    return RenderMatrices.viewMatrices.get(0);
                case PROJECTION:
                    return RenderMatrices.projectionMatrices.get(0);
                case MODEL:
                    return RenderMatrices.modelMatrices.get(0);
                default:
                    return new Matrix4f();
            }
        }
    }

}
