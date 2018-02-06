package cz.cvut.fit.blazeva.app.control;

import org.lwjgl.glfw.GLFW;

public class Mouse {

    private boolean[] mouseDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private boolean[] mouseClicked = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    public boolean wasClicked(int keycode) {
        final boolean b = mouseClicked[keycode];
        mouseClicked[keycode] = false;
        return b;
    }

    public boolean isDown(int keycode) {
        return mouseDown[keycode];
    }

    public void click(int keyCode) {
        mouseClicked[keyCode] = true;
    }

    public void down(int keyCode) {
        mouseDown[keyCode] = true;
    }

    public void up(int keyCode) {
        mouseDown[keyCode] = false;
    }
}
