package cz.cvut.fit.blazeva.util;

import cz.cvut.fit.blazeva.app.model.Camera;
import cz.cvut.fit.blazeva.app.model.Model;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MousePicker {

    private static final int RECURSION_COUNT = 200;
    private static final float RAY_RANGE = 600;

    private Vector3f currentRay = new Vector3f();

    private Matrix4f invertedProjectionMatrix;
    private Matrix4f invertedViewMatrix;
    private Camera camera;
    public Vector3f pointerPosition;

    public MousePicker(Camera camera, Matrix4f invertedProjectionMatrix, Matrix4f invertedViewMatrix) {
        this.camera = camera;
        this.invertedProjectionMatrix = invertedProjectionMatrix;
        this.invertedViewMatrix = invertedViewMatrix;
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
//        currentRay = calculateMouseRay(mouseX, mouseY);
        if (intersectionInRange(0, RAY_RANGE, currentRay)) {
            pointerPosition = binarySearch(0, 0, RAY_RANGE, currentRay);
        } else {
            pointerPosition = null;
        }
    }

    private Vector3f calculateMouseRay(float mouseX, float mouseY) {
        Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldCoords(eyeCoords);
        return worldRay;
    }

    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Vector4f rayWorld = invertedViewMatrix.transform(new Vector4f(eyeCoords));
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }

    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Vector4f eyeCoords = invertedProjectionMatrix.transform(new Vector4f(clipCoords));
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
        float x = (2.0f * mouseX) / Model.width - 1f;
        float y = (2.0f * mouseY) / Model.height - 1f;
        return new Vector2f(x, y);
    }

    //**********************************************************

    private Vector3f getPointOnRay(Vector3f ray, float distance) {
        Vector3f camPos = camera.position;
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return start.add(scaledRay);
    }

    private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
        float half = start + ((finish - start) / 2f);
        if (count >= RECURSION_COUNT) {
            return getPointOnRay(ray, half);
        }
        if (intersectionInRange(start, half, ray)) {
            return binarySearch(count + 1, start, half, ray);
        } else {
            return binarySearch(count + 1, half, finish, ray);
        }
    }

    private boolean intersectionInRange(float start, float finish, Vector3f ray) {
        Vector3f startPoint = getPointOnRay(ray, start);
        Vector3f endPoint = getPointOnRay(ray, finish);
        return !isBehindWall(startPoint) && isBehindWall(endPoint);
    }

    private boolean isBehindWall(Vector3f testPoint) {
        int z = -60;
        return testPoint.z < z;
    }

}
