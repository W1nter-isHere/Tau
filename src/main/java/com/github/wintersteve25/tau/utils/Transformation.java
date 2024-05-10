package com.github.wintersteve25.tau.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;

public class Transformation {

    protected final Matrix4f transform;
    protected final Matrix4f inverted;

    protected Transformation(Matrix4f transform, Matrix4f inverted) {
        this.transform = transform;
        this.inverted = inverted;
    }

    public void transform(PoseStack poseStack) {
        poseStack.mulPoseMatrix(transform);
    }

    public void transformMousePos(SimpleVec2i pos) {
        // Apply the inverted translation to the mouse position
        Vector3f transformedPos = new Vector3f(pos.x, pos.y, 0.0f); // Convert to 3D for matrix multiplication
        transformedPos.mulPosition(inverted);

        // Update the original position with the transformed coordinates (truncate z for 2D)
        pos.x = (int) Math.floor(transformedPos.x);
        pos.y = (int) Math.floor(transformedPos.y);
    }

    public void transformMousePos(Vector2d pos) {
        Vector3f transformedPos = new Vector3f((float) pos.x, (float) pos.y, 0.0f);
        transformedPos.mulPosition(inverted);
        pos.x = transformedPos.x;
        pos.y = transformedPos.y;
    }

    public static Transformation scale(Vector3f scale) {
        Matrix4f matrix4f = new Matrix4f().identity().scale(scale);
        return new Transformation(matrix4f, new Matrix4f(matrix4f).invert());
    }

    public static Transformation translate(Vector3f translation) {
        Matrix4f matrix4f = new Matrix4f().identity().translation(translation);
        return new Transformation(matrix4f, new Matrix4f(matrix4f).invert());
    }
}
