package cz.cvut.fit.blazeva.app.view;

import cz.cvut.fit.blazeva.app.control.Control;
import cz.cvut.fit.blazeva.app.model.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Callback;

import java.io.IOException;
import java.nio.IntBuffer;

import static cz.cvut.fit.blazeva.app.model.Model.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class Window {

    private boolean windowed = true;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cpCallback;
    private GLFWMouseButtonCallback mbCallback;
    private GLFWFramebufferSizeCallback fbCallback;
    private GLFWWindowSizeCallback wsCallback;
    private Callback debugProc;
    private Control control;


    private void init() throws IOException {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 4);

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        if (!windowed) {
            width = vidmode.width();
            height = vidmode.height();
            fbWidth = width;
            fbHeight = height;
        }
        window = glfwCreateWindow(width, height, "Little Space Shooter Game", !windowed ? monitor : 0L, NULL);
        if (window == NULL) {
            throw new AssertionError("Failed to create the GLFW window");
        }
        glfwSetCursor(window, glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));

        glfwSetFramebufferSizeCallback(window, fbCallback = new GLFWFramebufferSizeCallback() {
            public void invoke(long window, int newWidth, int newHeight) {
                if (newWidth > 0 && newHeight > 0 && (fbWidth != newWidth || fbHeight != newHeight)) {
                    fbWidth = newWidth;
                    fbHeight = newHeight;
                }
            }
        });
        glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int newWidth, int newHeight) {
                if (newWidth > 0 && newHeight > 0 && (width != newWidth || height != newHeight)) {
                    width = newWidth;
                    height = newHeight;
                }
            }
        });

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int keyCode, int scancode, int action, int mods) {
                if (keyCode == GLFW_KEY_UNKNOWN)
                    return;
                if (keyCode == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }
                if (action == GLFW_PRESS) {
                    control.key.tap(keyCode);
                }
                if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                    control.key.down(keyCode);
                } else {
                    control.key.up(keyCode);
                }
            }
        });
        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                float normX = (float) ((xpos - width / 2.0) / width * 2.0);
                float normY = (float) ((ypos - height / 2.0) / height * 2.0);
                Model.mouseX = Math.max(-width / 2.0f, Math.min(width / 2.0f, normX));
                Model.mouseY = Math.max(-height / 2.0f, Math.min(height / 2.0f, normY));
            }
        });
        glfwSetMouseButtonCallback(window, mbCallback = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS) {
                    control.mouse.down(button);
                    control.mouse.click(button);
                } else {
                    control.mouse.up(button);
                }
            }
        });
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);

        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
        fbWidth = framebufferSize.get(0);
        fbHeight = framebufferSize.get(1);
        Model.caps = GL.createCapabilities();
        if (!Model.caps.OpenGL20) {
            throw new AssertionError("This demo requires OpenGL 2.0.");
        }
//        debugProc = GLUtil.setupDebugMessageCallback();

        control.initialize();

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    }

    public void run() {
        control = new Control();
        try {
            init();
            control.loop(window);
            if (debugProc != null) {
                debugProc.free();
            }
            keyCallback.free();
            cpCallback.free();
            mbCallback.free();
            fbCallback.free();
            wsCallback.free();
            glfwDestroyWindow(window);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
    }

}
