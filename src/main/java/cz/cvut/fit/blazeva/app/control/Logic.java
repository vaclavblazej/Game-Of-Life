package cz.cvut.fit.blazeva.app.control;

import cz.cvut.fit.blazeva.app.view.Drawer;
import cz.cvut.fit.blazeva.app.view.Program;
import org.joml.Vector3d;
import org.joml.Vector4d;
import org.joml.Vector4f;

import java.io.IOException;

import static cz.cvut.fit.blazeva.app.control.Model.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class Logic {

    private static int shotMilliseconds = 80;
    private static float maxShotLifetime = 30.0f;
    private static final int maxParticles = 4096;

    private Vector3d[] projectilePositions = new Vector3d[1024];
    private Vector4f[] projectileVelocities = new Vector4f[1024];

    {
        for (int i = 0; i < projectilePositions.length; i++) {
            Vector3d projectilePosition = new Vector3d(0, 0, 0);
            projectilePositions[i] = projectilePosition;
            Vector4f projectileVelocity = new Vector4f(0, 0, 0, 0);
            projectileVelocities[i] = projectileVelocity;
        }
    }

    private Vector3d[] particlePositions = new Vector3d[maxParticles];
    private Vector4d[] particleVelocities = new Vector4d[maxParticles];

    {
        for (int i = 0; i < particlePositions.length; i++) {
            Vector3d particlePosition = new Vector3d(0, 0, 0);
            particlePositions[i] = particlePosition;
            Vector4d particleVelocity = new Vector4d(0, 0, 0, 0);
            particleVelocities[i] = particleVelocity;
        }
    }

    private long lastShotTime = 0L;
    private long lastTime = System.nanoTime();
    private Vector3d newPosition = new Vector3d();

    private Program program = new Program();
    private Drawer drawer = new Drawer();

    private void update() {
        long thisTime = System.nanoTime();
        float dt = (thisTime - lastTime) / 1E9f;
        lastTime = thisTime;
        updateShots(dt);

        drawer.update(dt, program);

        updateControls();

        if (leftMouseDown && (thisTime - lastShotTime >= 1E6 * shotMilliseconds)) {
            lastShotTime = thisTime;
        }
    }

    private boolean keyTapped(int keycode) {
        final boolean b = Model.keyTapped[keycode];
        Model.keyTapped[keycode] = false;
        return b;
    }

    private void updateControls() {
        float rotZ = 0.0f;
        if (keyTapped(GLFW_KEY_LEFT)) Model.scenario.move(-1, 0);
        if (keyTapped(GLFW_KEY_RIGHT)) Model.scenario.move(1, 0);
        if (keyTapped(GLFW_KEY_UP)) Model.scenario.move(0, 1);
        if (keyTapped(GLFW_KEY_DOWN)) Model.scenario.move(0, -1);
        if (keyTapped(GLFW_KEY_R)) {
            Model.level--;
            Model.loadNextScenario();
        }
        if (rightMouseDown) {
            final float xxx = 2.0f * mouseX * mouseX * mouseX;
            final float yyy = 2.0f * mouseY * mouseY * mouseY;
        }
    }

    /* Create all needed GL resources */
    public void initialize() throws IOException {
        drawer.createEntities();
    }

    private void updateShots(float dt) {
        projectiles:
        for (int i = 0; i < projectilePositions.length; i++) {
            Vector4f projectileVelocity = projectileVelocities[i];
            if (projectileVelocity.w <= 0.0f)
                continue;
            projectileVelocity.w += dt;
            Vector3d projectilePosition = projectilePositions[i];
            newPosition.set(projectileVelocity.x, projectileVelocity.y, projectileVelocity.z).mul(dt).add(projectilePosition);
            if (projectileVelocity.w > maxShotLifetime) {
                projectileVelocity.w = 0.0f;
                continue;
            }
            projectilePosition.set(newPosition);
        }
    }


    private void render() {
        drawer.draw();
    }

    public void loop() {
        try {
            while (!glfwWindowShouldClose(window)) {
                glfwPollEvents();
                glViewport(0, 0, fbWidth, fbHeight);
                Thread.sleep(80);
                update();
                render();
                glfwSwapBuffers(window);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
