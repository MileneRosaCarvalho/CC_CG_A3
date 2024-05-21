package game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 *
 * @author Milene Rosa Carvalho
 */
public class Primitives {

    private float livesAnimation = 0;

    public GL2 drawBall(float xBall, float yBall, float aspect, GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(xBall, yBall, 0);

        double limit = 2 * Math.PI;
        double i;
        double cX = 0;
        double cY = 0;
        double rX = 0.1f / aspect;
        double rY = 0.1f;

        gl.glBegin(GL2.GL_POLYGON);
        for (i = 0; i < limit; i += 0.01) {
            double x = cX + rX * Math.cos(i);
            double y = cY + rY * Math.sin(i);
            //gl.glColor3f(0f, 0f, 0f);
            gl.glColor3f(1.0f, 1.0f, 0.0f);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();

        // Contorno 
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glBegin(GL2.GL_LINE_LOOP);
        for (i = 0; i < limit; i += 0.01) {
            double x = cX + rX * Math.cos(i);
            double y = cY + rY * Math.sin(i);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
        gl.glPopMatrix();

        return gl;
    }

    public GL2 drawBar(float moveBarra, GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(moveBarra, 0, 0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        gl.glVertex2f(-0.2f, -0.8f);
        gl.glColor3f(0f, 0f, 0f);
        gl.glVertex2f(0.2f, -0.8f);
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        gl.glVertex2f(0.2f, -0.9f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glVertex2f(-0.2f, -0.9f);
        gl.glEnd();
        gl.glPopMatrix();

        return gl;
    }

    public GL2 drawLives(GL2 gl, GLUT glut, float pos, boolean filled) {
        gl.glPushMatrix();
        if (filled) {
            gl.glColor3f(0, 0, 255);
        } else {
            gl.glColor3f(255, 255, 255);
        }
        gl.glTranslatef(0.5f + pos, 0.88f, 0.5f);
        gl.glRotatef(livesAnimation, 5, 50, 5);
        livesAnimation += 1.5f;
        glut.glutSolidTorus(0.027f, 0.01f, 5, 100);
        gl.glPopMatrix();

        return gl;
    }

    public GL2 drawObstacle(GL2 gl) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);  
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // Cor do contorno 
        gl.glVertex2f(-0.19f, 0.55f);
        gl.glVertex2f(0.19f, 0.55f);
        gl.glVertex2f(0.19f, -0.15f);
        gl.glVertex2f(-0.19f, -0.15f);
        gl.glEnd();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);  

        return gl;
    }
}
