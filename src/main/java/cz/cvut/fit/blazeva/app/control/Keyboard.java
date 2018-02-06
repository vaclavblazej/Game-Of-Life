package cz.cvut.fit.blazeva.app.control;

import org.lwjgl.glfw.GLFW;

public class Keyboard {

    private boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] keyTapped = new boolean[GLFW.GLFW_KEY_LAST];

    public boolean wasTapped(int keycode) {
        final boolean b = keyTapped[keycode];
        keyTapped[keycode] = false;
        return b;
    }

    public boolean isDown(int keycode) {
        return keyDown[keycode];
    }

    public void tap(int keyCode) {
        keyTapped[keyCode] = true;
    }

    public void down(int keyCode) {
        keyDown[keyCode] = true;
    }

    public void up(int keyCode) {
        keyDown[keyCode] = false;
    }
}
