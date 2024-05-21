package game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.*;
import java.util.Locale;
import com.jogamp.opengl.util.texture.Texture;

/**
 *
 * @author Milene Rosa Carvalho
 */
public class Cena implements GLEventListener {
    private GL2 gl;
    private GLUT glut;
    private int toning = GL2.GL_SMOOTH;
    private float aspect;
    private TextRenderer textRenderer;
    private Texture textureStart;
    private Texture textureRules;
    private Texture textureDevelopers;
    private Texture textureGameOver;
    private Texture textureYouWin;
    private Texture texturePhase1;
    private Texture texturePhase2;
    public int mode;
    private long time;
    public int screen = 0;
    private float ballX = 0;
    private float ballY = 1f;
    private char directionX;
    private char directionY = 'd';
    private float speed = 0.02f;
    public float barMovement = 0;
    public int score = 0;
    public int lifes = 5;
    public boolean pause = false;
    private boolean textoVisivel = true;

    Textures textures = new Textures();
    Primitives primitives = new Primitives();

    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        // Define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        // Limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity(); // Lê a matriz identidade

        switch (screen) {
            case 0:
                start();
                break;

            case 1:
                backgroundPhase_01();
                phase_01();

                if (score == 50) {
                    screen = 2;
                }

                if (lifes < 1) {
                    screen = 3;
                }
                break;

            case 2:
                backgroundPhase_02();
                phase_01();

                gl.glPushMatrix();
                primitives.drawObstacle(gl);
                gl.glPopMatrix();
                speed += 0.00001f;

                if (lifes < 1) {
                    screen = 3;
                }

                if (score == 100) {
                    screen = 6;
                }
                break;

            case 3:
                GameOver();
                break;

            case 4:
                backgroundRules();
                break;

            case 5:
                backgroundDevelopers();
                break;

            case 6:
                YouWin();
                break;

            default:
                break;
        }
        gl.glFlush();
    }

    public void start() {
        backgroundStart();
        gl.glPushMatrix();
        gl.glPopMatrix();
        gl.glEnd();
    }

    public void YouWin() {
        backgroundYouWin();
        gl.glPushMatrix();
        renderTextSoft(gl, 100, 670, Color.WHITE, "SCORE FINAL: " + score);
        gl.glPopMatrix();
        gl.glEnd();
    }

    public boolean GameOver() {
        if (lifes <= 0) {
            backgroundGameOver();
            gl.glPushMatrix();
            renderTextSoft(gl, 100, 670, Color.WHITE, "SCORE FINAL: " + score);
            gl.glPopMatrix();
            gl.glEnd();
        }
        return true;
    }

    public void phase_01() {
        gl.glPushMatrix();
        primitives.drawBar(barMovement, gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        primitives.drawBall(ballX, ballY, aspect, gl);
        gl.glPopMatrix();

        if (!pause) {
            ballMechanism();
        } else {
            renderText_02(gl, 480, 400, Color.WHITE, "PAUSED", 80);
        }

        gl.glPushMatrix();
        for (int i = 1; i <= 5; i++) {
            if (lifes >= i) {
                primitives.drawLives(gl, glut, 0.08f * i, true);
            } else {
                primitives.drawLives(gl, glut, 0.08f * i, false);
            }
        }
        gl.glPopMatrix();

        renderText_01(gl, 40, 670, Color.WHITE, "SCORE " + score);
    }

    public void backgroundStart() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureStart.enable(gl);
        textureStart.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        textureStart.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void backgroundRules() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureRules.enable(gl);
        textureRules.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        textureRules.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void backgroundDevelopers() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureDevelopers.enable(gl);
        textureDevelopers.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        textureDevelopers.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void backgroundPhase_01() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        texturePhase1.enable(gl);
        texturePhase1.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        texturePhase1.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void backgroundPhase_02() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        texturePhase2.enable(gl);
        texturePhase2.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        texturePhase2.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void backgroundGameOver() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureGameOver.enable(gl);
        textureGameOver.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        textureGameOver.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void backgroundYouWin() {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureYouWin.enable(gl);
        textureYouWin.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(1.0f, -1.0f);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(1.0f, 1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();

        textureYouWin.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void randomPositionBall() {
        double xRandom = -0.8f + Math.random() * 1.6f;
        if (xRandom > 0) {
            directionX = 'r';
        } else {
            directionX = 'l';
        }

        ballX = Float.valueOf(String.format(Locale.US, "%.2f", xRandom));
    }

    public boolean collisionBallBar(float xTranslatedBallFixed) {
        float leftBarLimit = Float.valueOf(String.format(Locale.US, "%.1f", barMovement - 0.2f));
        float rightBarLimit = Float.valueOf(String.format(Locale.US, "%.1f", barMovement + 0.2f));

        if (leftBarLimit <= xTranslatedBallFixed && rightBarLimit >= xTranslatedBallFixed) {
            return true;
        }
        return false;
    }

    public boolean collisionBallX(float xObj, float heightObj, float lLimit, float rLimit, float upperLimit) {
        if (lLimit <= xObj && rLimit >= xObj && heightObj == upperLimit) {
            return true;
        }
        return false;
    }

    public boolean collisionBallY(float xObj, float yObj, float inferiorLimit, float upperLimit, float xPoint) {
        if (upperLimit >= yObj && inferiorLimit <= yObj && xObj == xPoint) {
            return true;
        }
        return false;
    }

    public void ballMechanism() {
        float xTransBallFixed = Float.valueOf(String.format(Locale.US, "%.1f", ballX));
        float yTransBallFixed = Float.valueOf(String.format(Locale.US, "%.1f", ballY));

        // Colisão com o objeto da fase 2
        if (screen == 2 && directionX == 'l'
                && collisionBallY(xTransBallFixed, yTransBallFixed, -0.1f, 0.5f, 0.2f)) {
            directionX = 'r';
        }
        if (screen == 2 && directionX == 'r'
                && collisionBallY(xTransBallFixed, yTransBallFixed, -0.1f, 0.5f, -0.2f)) {
            directionX = 'l';
        }

        // Colisão com a barra
        if (xTransBallFixed > -1f && directionX == 'l') {
            ballX -= speed / 2;
        } else if (xTransBallFixed == -1f && directionX == 'l') {
            directionX = 'r';
        } else if (xTransBallFixed < 1f && directionX == 'r') {
            ballX += speed / 2;
        } else if (xTransBallFixed == 1f && directionX == 'r') {
            directionX = 'l';
        }

        // Colisão com objeto da fase 2
        if (screen == 2 && directionY == 'u'
                && collisionBallX(xTransBallFixed, yTransBallFixed, -0.2f, 0.2f, -0.2f)) {
            directionY = 'd';
        } else if (screen == 2 && directionY == 'd'
                && collisionBallX(xTransBallFixed, yTransBallFixed, -0.2f, 0.2f, 0.6f)) {
            directionY = 'u';
        }

        // Colisão com a barra
        if (yTransBallFixed == -0.7f && directionY == 'd' && collisionBallBar(xTransBallFixed)) {
            directionY = 'u';
            //lightOn = false;
            toning = toning == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
            score += 10;
        } else if (yTransBallFixed < 0.9f && directionY == 'u') {
            ballY += speed;
        } else if (yTransBallFixed == 0.9f && directionY == 'u') {
            directionY = 'd';
        } else if (yTransBallFixed < -1f) {
            ballY = 1f;
            ballX = 0;
            lifes--;
            randomPositionBall();
        } else {
            ballY -= speed;
            toning = toning == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
        }
    }

    public void renderText_01(GL2 gl, int xPosition, int yPosition, Color cor, String phrase) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(phrase, xPosition, yPosition);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    public void renderText_02(GL2 gl, int xPosition, int yPosition, Color cor, String phrase, int fontSize) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        TextRenderer textRendererEspecifico = new TextRenderer(new Font("BatmanForeverAlternate", Font.BOLD, fontSize));
        textRendererEspecifico.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRendererEspecifico.setColor(cor);
        textRendererEspecifico.draw(phrase, xPosition, yPosition);
        textRendererEspecifico.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    private void renderTextSoft(GL2 gl, int xPosition, int yPosition, Color cor, String phrase) {
        long tempoAtual = System.currentTimeMillis();
        long tempoPassado = tempoAtual - time;
        float alpha = (float) Math.abs(Math.sin(tempoPassado / 1300.0));
        cor = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), (int) (alpha * 255));
        renderText_01(gl, xPosition, yPosition, cor, phrase);

        gl.glColor4f(1f, 1f, 1f, 1f);
    }

    public void resetData() {
        ballX = 0;
        ballY = 1f;
        directionY = 'd';
        pause = false;
        barMovement = 0;
        screen = 0;
        speed = 0.02f;
        score = 0;
        lifes = 5;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //Obtém o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();
        //Evita a divisão por zero
        if (height == 0) {
            height = 1;
        }
        //Calcula a proporção da janela (aspect ratio) da nova janela
        aspect = (float) width / height;
        //Viewport para abranger a janela inteira
        gl.glOrtho(-1, 1, -1, 1, -1, 1);
        //Ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //Lê a matriz identidade
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glut = new GLUT();
        time = System.currentTimeMillis();
        textRenderer = new TextRenderer(new Font("BatmanForeverAlternate", Font.BOLD, 25));
        randomPositionBall();

        textureStart = Textures.loadTexture(gl, "src/images/start.PNG");
        textureRules = Textures.loadTexture(gl, "src/images/rules.PNG");
        textureDevelopers = Textures.loadTexture(gl, "src/images/developers.PNG");
        textureGameOver = Textures.loadTexture(gl, "src/images/gameOver.PNG");
        textureYouWin = Textures.loadTexture(gl, "src/images/youWin.PNG");
        texturePhase1 = Textures.loadTexture(gl, "src/images/phase1.PNG");
        texturePhase2 = Textures.loadTexture(gl, "src/images/phase2.PNG");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
