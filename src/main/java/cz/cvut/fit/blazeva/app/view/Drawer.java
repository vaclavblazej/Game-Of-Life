package cz.cvut.fit.blazeva.app.view;

import cz.cvut.fit.blazeva.app.control.Scenario;
import cz.cvut.fit.blazeva.app.model.Camera;
import cz.cvut.fit.blazeva.app.model.Model;
import cz.cvut.fit.blazeva.util.MousePicker;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;

import static cz.cvut.fit.blazeva.app.model.EntityType.*;
import static cz.cvut.fit.blazeva.app.model.Model.height;
import static cz.cvut.fit.blazeva.app.model.Model.width;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Drawer {


    private Matrix4f projMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f viewProjMatrix = new Matrix4f();
    private Matrix4f invViewMatrix = new Matrix4f();
    private Matrix4f invViewProjMatrix = new Matrix4f();
    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private Camera camera = new Camera();
    private MousePicker mousePicker = null;

    private FrustumIntersection frustumIntersection = new FrustumIntersection();

    public void update(float dt, Program program) {
        camera.update(dt);

        projMatrix.setPerspective((float) Math.toRadians(40.0f), (float) width / height, 0.1f, 5000.0f);
        viewMatrix.set(camera.rotation).invert(invViewMatrix);
        viewProjMatrix.set(projMatrix).mul(viewMatrix).invert(invViewProjMatrix);
        frustumIntersection.set(viewProjMatrix);

        /* Update the ship shader */
        glUseProgram(program.program(SHIP));
        glUniformMatrix4fv(program.viewUniform(SHIP), false, viewMatrix.get(matrixBuffer));
        glUniformMatrix4fv(program.projection(SHIP), false, projMatrix.get(matrixBuffer));

        /* Update the shot shader */
        glUseProgram(program.program(SHOT));
        glUniformMatrix4fv(program.projection(SHOT), false, matrixBuffer);

        /* Update the particle shader */
        glUseProgram(program.program(PARTICLE));
        glUniformMatrix4fv(program.projection(PARTICLE), false, matrixBuffer);

        mousePicker.update();
    }

    public void initialize() throws IOException {
        mousePicker = new MousePicker(camera, invViewProjMatrix, invViewMatrix);
//        createAsteroids();
//        createShip();
//        createSphere();
    }

    public void draw() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        drawAllObjects();
    }

    private void drawTriangle(Vector3f a, Vector3f b, Vector3f c) {
        glVertex3f(a.x, a.y, a.z);
        glVertex3f(b.x, b.y, b.z);
        glVertex3f(c.x, c.y, c.z);
    }

    private void drawRect(int x, int y, int size, float border) {
        glBegin(GL_TRIANGLES);
        Vector3f a = new Vector3f(x + border, y + border, 0);
        Vector3f b = new Vector3f(x + size - border, y + border, 0);
        Vector3f c = new Vector3f(x + border, y + size - border, 0);
        Vector3f d = new Vector3f(x + size - border, y + size - border, 0);
        drawTriangle(a, b, c);
        drawTriangle(d, c, b);
        glEnd();
    }

    private void drawAll() {
        final Scenario scenario = Model.scenario;
        final int size = scenario.size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (scenario.map[i][scenario.size - 1 - j]) {
                    case EMPTY:
                        glColor4f(0.6f, 0.8f, 0.2f, 0.6f);
                        drawRect(i, j, 1, 0.05f);
                        break;
                    case OCCUPIED:
                        glColor4f(1, 1, 0.8f, 1);
                        drawRect(i, j, 1, 0.05f);
                        break;
                }
            }
        }
    }

    private void drawAllObjects() {
        glUseProgram(0);
        glEnable(GL_BLEND);
        glEnableClientState(GL_NORMAL_ARRAY);
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadMatrixf(projMatrix.get(matrixBuffer));
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
        glTranslatef(-20, -20, -60);
        glMultMatrixf(viewMatrix.get(matrixBuffer));

        drawAll();

        glPopMatrix();
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisable(GL_BLEND);
    }
}
