package cz.cvut.fit.blazeva.util;


import static org.lwjgl.opengl.GL11.*;

public class Text {

    public static void drawString(String s, float x, float y, float scale, float width) {
        float startX = x;
        scale = scale * 0.25f;
        glLineWidth(width);
        glBegin(GL_LINES);
        glEnable(GL_LINE_WIDTH);
        glColor3f(1, 1, 1);
        boolean space = false;
        int charNum = 1, spaceNum = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            switch (c) {
                case ' ':
                    space = true;
                    break;
                case '\n':
                    y -= 2;
                    startX -= charNum * (scale * 5);
                    startX -= spaceNum * (scale * 3);
                    spaceNum = 0;
                    charNum = 0;
                    break;
                case 'a':
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y + -1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + -1));
                    glVertex2f(scale * startX, scale * (y + -1));
                    glVertex2f(scale * startX, scale * (y + -1));
                    glVertex2f(scale * startX, scale * y);
                    glVertex2f(scale * startX, scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * startX, scale * y);
                    glVertex2f(scale * startX, scale * (y + 0.5f));
                    glVertex2f(scale * startX, scale * (y + 0.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.5f));
                    break;
                case 'b':
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    break;
                case 'd':
                    glVertex2f(scale * startX, scale * (y - 1));
                    glVertex2f(scale * startX, scale * (y + 0.8f));
                case 'c':
                    glVertex2f(scale * startX, scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * startX, scale * (y - 1));
                    break;
                case 'e':
                    glVertex2f(scale * startX, scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * startX, scale * y);
                    glVertex2f(scale * startX, scale * (y - 0.5f));
                    glVertex2f(scale * startX, scale * (y - 0.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 0.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 0.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 0.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case 'f':
                    glVertex2f(scale * (startX + 0.25f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.25f));
                    glVertex2f(scale * (startX), scale * (y + 0.25f));
                    break;
                case 'g':
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * (y - 1.5f));
                    glVertex2f(scale * (startX), scale * (y - 1.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1.5f));
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1f));
                    break;
                case 'h':
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case 'i':
                    glVertex2f(scale * (startX - 0.25f), scale * y);
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glEnd();
                    glPointSize(width);
                    glBegin(GL_POINTS);
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.25f));
                    glEnd();
                    glBegin(GL_LINES);
                    break;
                case 'j':
                    glVertex2f(scale * (startX - 0.25f), scale * y);
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1.5f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1.5f));
                    glEnd();
                    glPointSize(width);
                    glBegin(GL_POINTS);
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.25f));
                    glEnd();
                    glBegin(GL_LINES);
                    break;
                case 'k':
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y + 0.25f));
                    break;
                case 't':
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.15f));
                    glVertex2f(scale * (startX), scale * (y + 0.15f));
                case 'l':
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    break;
                case 'm':
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.25f), scale * y);
                    glVertex2f(scale * (startX - 0.25f), scale * y);
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.25f), scale * y);
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case 'p':
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1.5f));
                case 'o':
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                case 'n':
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case 'q':
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case 'r':
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 0.15f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y));
                    glVertex2f(scale * (startX - 0.25f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 0.1f));
                    break;
                case 's':
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 0.5f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 0.5f));
                    glVertex2f(scale * (startX), scale * (y - 0.5f));
                    glVertex2f(scale * (startX), scale * (y - 0.5f));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    break;
                case 'u':
                    glVertex2f(scale * (startX - 0.5f), scale * y);
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * y);
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case 'w':
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.125f), scale * (y));
                    glVertex2f(scale * (startX - 0.125f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX + 0.125f), scale * (y));
                    break;
                case 'v':
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y));
                    break;
                case 'x':
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    break;
                case 'y':
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.33f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1.5f));
                    break;
                case 'z':
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case '1':
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case '2':
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.65f));
                    glVertex2f(scale * (startX - 0.3f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.3f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.15f), scale * (y));
                    glVertex2f(scale * (startX - 0.15f), scale * (y));
                    glVertex2f(scale * (startX - 0.35f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    break;
                case '3':
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    break;
                case '4':
                    glVertex2f(scale * startX, scale * (y + 0.8f));
                    glVertex2f(scale * startX, scale * (y - 1));
                    glVertex2f(scale * startX, scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    break;
                case '8':
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                case '6':
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                case '5':
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    break;
                case '7':
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    break;
                case '9':
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX - 0.5f), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    break;
                case '0':
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
                    glVertex2f(scale * (startX), scale * (y + 0.8f));
                    break;
            }
            if (!space) {
                startX += scale * 5;
                charNum++;
            } else {
                startX += scale * 3;
                spaceNum++;
            }
            space = false;
        }
        glEnd();
        glDisable(GL_LINE_WIDTH);
    }

}