package cz.cvut.fit.blazeva.app.view;

import cz.cvut.fit.blazeva.app.control.Model;
import cz.cvut.fit.blazeva.app.control.Scenario;
import cz.cvut.fit.blazeva.app.model.Goal;
import cz.cvut.fit.blazeva.app.model.Player;
import cz.cvut.fit.blazeva.app.model.SpaceCamera;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;

import static cz.cvut.fit.blazeva.app.control.Model.*;
import static cz.cvut.fit.blazeva.app.model.EntityType.*;
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
    private SpaceCamera cam = new SpaceCamera();

    private FrustumIntersection frustumIntersection = new FrustumIntersection();

    public void update(float dt, Program program) {
        cam.update(dt);

        projMatrix.setPerspective((float) Math.toRadians(40.0f), (float) width / height, 0.1f, 5000.0f);
        viewMatrix.set(cam.rotation).invert(invViewMatrix);
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
    }

    public void createEntities() throws IOException {
//        createAsteroids();
//        createShip();
//        createSphere();
    }

    public void draw() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        drawAllObjects();
    }

    private void drawRect(int x, int y, int size, float border) {
        glBegin(GL_LINES);
        glVertex3f(x + border, y + border, 0);
        glVertex3f(x + size - border, y + border, 0);
        glVertex3f(x + border, y + size - border, 0);
        glVertex3f(x + size - border, y + size - border, 0);
        glVertex3f(x + border, y + border, 0);
        glVertex3f(x + border, y + size - border, 0);
        glVertex3f(x + size - border, y + border, 0);
        glVertex3f(x + size - border, y + size - border, 0);
        glEnd();
    }

    private void drawPlayer() {
        final Player player = Model.scenario.player;
        drawRect(player.x, player.y, 1, 0.3f);
    }

    private void drawAll() {
        final Scenario scenario = Model.scenario;
        final int size = scenario.size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (scenario.map[i][j]) {
                    case WALL:
                        glColor4f(0.8f, 0, 0, 1);
                        drawRect(i, j, 1, 0f);
                        break;
                    case BOX:
                        glColor4f(0.8f, 0.8f, 0, 1);
                        drawRect(i, j, 1, 0.2f);
                    case EMPTY:
                        glColor4f(1, 1, 1, 0.8f);
                        drawRect(i, j, 1, 0.05f);
                        break;
                }
            }
        }
        for (Goal goal : scenario.goals) {
            glColor4f(0, 1, 0, 0.8f);
            drawRect(goal.x, goal.y, 1, 0.1f);
        }
        glColor4f(1f, 1f, 0.4f, 1);
        if (scenario.won) {
            loadNextScenario();
        }
        drawPlayer();
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
        glTranslatef(-5, -5, -15);
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
