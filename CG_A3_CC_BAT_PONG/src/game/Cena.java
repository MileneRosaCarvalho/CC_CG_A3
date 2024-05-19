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
public class Cena implements GLEventListener{
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
    Textures textures = new Textures ();
    Primitives primitives = new Primitives ();

    
    
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
                menu();
                break;

            case 1:
                fundoF1();
                fase1();
                if (score == 200){
                    screen = 2;
                }
                if (lifes < 1){
                    screen = 3;
                }
                break;

            case 2:
                fundoF2();
                fase1();

                gl.glPushMatrix();
                primitives.obstaculo(gl);
                gl.glPopMatrix();
                speed += 0.00001f;

                if (lifes < 1){
                    screen = 3;
                }
                if (score == 400)
                {
                    screen = 6;
                }
                break;

            case 3:
                gameOver();
                break;

            case 4:
                fundoCJ();
                break;

            case 5:
                fundoCred();
                break;

            case 6:
                telaFim();
                break;

            default:

                break;
        }

        gl.glFlush();
    }

    public void menu() {
        fundoMenu();
        gl.glPushMatrix();
        if(textoVisivel) {
            desenhaTextoSuave(gl, 470, 50, Color.WHITE, "Aperte S para INICIAR!");
        }
        gl.glPopMatrix();
        gl.glEnd();
    }

    public boolean gameOver(){
        if (lifes <= 0) {
            fundoGo();
            gl.glPushMatrix();
                desenhaTexto(gl, 480, 225, Color.WHITE, "Almas coletadas " + score);
            gl.glPopMatrix();
            gl.glEnd();
        }
        return true;
    }

    public void telaFim() {
            fundoFim();
            gl.glPushMatrix();
            desenhaTexto(gl, 480, 225, Color.WHITE, "Almas coletadas " + score);
            desenhaTextoSuave(gl, 450, 50, Color.WHITE, "Aperte 2 para continuar");
            gl.glPopMatrix();
            gl.glEnd();
    }
    
//DESENHAR VIDAS

    public void fase1() {


        gl.glPushMatrix();
        primitives.criaRetangulo(barMovement, gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        primitives.criaBola(ballX, ballY, aspect, gl);
        gl.glPopMatrix();

        if (!pause) {
            bolaMove();
        }
        else {
            desenhaTextoEspecifico(gl, 440, 500, Color.YELLOW, "PAUSED", 70);
            //desenhaTexto(gl, 460, 350, Color.WHITE, "Aperte R para RECOMEÇAR");
            //desenhaTexto(gl, 410, 300, Color.WHITE, "Aperte M para voltar ao MENU");
        }

        
        gl.glPushMatrix();
        //primitives.criaVida(gl, lifes);
        
                        for (int i = 1; i <= 5; i++) {		
                        if (lifes >= i)
				primitives.drawLives(gl, glut, 0.01f*i, true);
			else
				primitives.drawLives(gl, glut, 0.01f*i, false);
		} 
        gl.glPopMatrix();
        
        desenhaTexto(gl, 100, 670, Color.WHITE, "SCORE " + score);
        
  
    }

    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }




    public void fundoMenu() {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureStart.enable(gl);
        textureStart.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        textureStart.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void fundoCJ() {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureRules.enable(gl);
        textureRules.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        textureRules.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void fundoCred ()  {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureDevelopers.enable(gl);
        textureDevelopers.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        textureDevelopers.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void fundoF1() {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        texturePhase1.enable(gl);
        texturePhase1.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        texturePhase1.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void fundoF2() {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        texturePhase2.enable(gl);
        texturePhase2.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        texturePhase2.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void fundoGo() {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureGameOver.enable(gl);
        textureGameOver.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        textureGameOver.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void fundoFim() {

        gl.glEnable(GL2.GL_TEXTURE_2D);
        textureYouWin.enable(gl);
        textureYouWin.bind(gl);

        // Desenhar o objeto
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

        // Desativar a textura
        textureYouWin.disable(gl);
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public void randomBola() {
        double xRandom = -0.8f + Math.random() * 1.6f;
        if (xRandom > 0) {
            directionX = 'r';
        }
        else {
            directionX = 'l';
        }

        ballX = Float.valueOf(String.format(Locale.US, "%.2f", xRandom));
    }

    public boolean colisaoBolaBarra(float xTranslatedBallFixed) {
        float leftBarLimit = Float.valueOf(String.format(Locale.US, "%.1f", barMovement - 0.2f));
        float rightBarLimit = Float.valueOf(String.format(Locale.US, "%.1f", barMovement + 0.2f));

        if (leftBarLimit <= xTranslatedBallFixed && rightBarLimit >= xTranslatedBallFixed) {
            return true;
        }

        return false;
    }

    public boolean colisaoBolaY(float xObj, float yObj, float bLimit, float tLimit, float xPoint) {
        if (tLimit >= yObj && bLimit <= yObj && xObj == xPoint) {
            return true;
        }

        return false;
    }

    public boolean colisaoBolaX(float xObj, float heightObj, float lLimit, float rLimit, float tLimit) {
        if (lLimit <= xObj && rLimit >= xObj && heightObj == tLimit) {
            return true;
        }

        return false;
    }

    public void bolaMove() {
        float xTransBallFixed = Float.valueOf(String.format(Locale.US, "%.1f", ballX));
        float yTransBallFixed = Float.valueOf(String.format(Locale.US, "%.1f", ballY));

        // Colisão com objeto da fase 2
        if (screen == 2 && directionX == 'l'
                && colisaoBolaY(xTransBallFixed, yTransBallFixed, -0.1f, 0.5f, 0.2f)) {
            directionX = 'r';
        }
        if (screen == 2 && directionX == 'r'
                && colisaoBolaY(xTransBallFixed, yTransBallFixed, -0.1f, 0.5f, -0.2f)) {
            directionX = 'l';
        }
        // Colisão com a plataforma
        if (xTransBallFixed > -1f && directionX == 'l') {
            ballX -= speed/2;
        }
        else if (xTransBallFixed == -1f && directionX == 'l') {
            directionX = 'r';
        }
        else if (xTransBallFixed < 1f && directionX == 'r') {
            ballX += speed/2;
        }
        else if (xTransBallFixed == 1f && directionX == 'r') {
            directionX = 'l';
        }

        // Colisão com objeto da fase 2
        if (screen == 2 && directionY == 'u'
                && colisaoBolaX(xTransBallFixed, yTransBallFixed, -0.2f, 0.2f, -0.2f)) {
            directionY = 'd';
        }
        else if (screen == 2 && directionY == 'd'
                && colisaoBolaX(xTransBallFixed, yTransBallFixed, -0.2f, 0.2f, 0.6f)) {
            directionY = 'u';
        }
        // Colisão com a plataforma
        if (yTransBallFixed == -0.7f && directionY == 'd' && colisaoBolaBarra(xTransBallFixed)) {
            directionY = 'u';
            //lightOn = false;
            toning = toning == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
            score += 10;
        }
        else if (yTransBallFixed < 0.9f && directionY == 'u') {
            ballY += speed;
        }
        else if (yTransBallFixed == 0.9f && directionY == 'u') {
            directionY = 'd';
        }
        else if (yTransBallFixed < -1f) {
            ballY = 1f;
            ballX = 0;
            lifes--;
            randomBola();
        }
        else {
            ballY -= speed;
            //lightOn = true;
            toning = toning == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
        }
    }

    private void desenhaTextoSuave(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase) {
        long tempoAtual = System.currentTimeMillis();
        long tempoPassado = tempoAtual - time;

        float alpha = (float) Math.abs(Math.sin(tempoPassado / 1300.0)); // Ajuste a frequência aqui

        // Configura a cor com a intensidade alpha
        cor = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), (int) (alpha * 255));
        desenhaTexto(gl, xPosicao, yPosicao, cor, frase);

        gl.glColor4f(1f, 1f, 1f, 1f);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width , int height) {
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();
        //evita a divisão por zero
        if(height == 0) height = 1;
        //calcula a proporção da janela (aspect ratio) da nova janela
        aspect = (float) width / height;
        //seta o viewport para abranger a janela inteira
        gl.glOrtho(-1, 1, -1, 1, -1, 1);
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
    }

    // Função para mudar tamanho de um TEXTO ESPECÍFICOS
    public void desenhaTextoEspecifico(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase, int tamanhoFonte) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        TextRenderer textRendererEspecifico = new TextRenderer(new Font("OptimusPrinceps", Font.BOLD, tamanhoFonte));
        textRendererEspecifico.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRendererEspecifico.setColor(cor);
        textRendererEspecifico.draw(frase, xPosicao, yPosicao);
        textRendererEspecifico.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Dados iniciais da cena
        gl = drawable.getGL().getGL2();
        glut = new GLUT();
        textRenderer = new TextRenderer(new Font("Castellar", Font.BOLD,25));
        randomBola();

        // temporizador de texto
        time = System.currentTimeMillis();
        
        // Carrega a textura usando a classe Textura
        textureStart = Textures.loadTexture(gl, "src/images/start.PNG");
        textureRules = Textures.loadTexture(gl, "src/images/rules.PNG");
        textureDevelopers = Textures.loadTexture(gl, "src/images/developers.PNG");
        textureGameOver = Textures.loadTexture(gl, "src/images/gameOver.PNG");
        textureYouWin = Textures.loadTexture(gl, "src/images/youWin.PNG");
        texturePhase1 = Textures.loadTexture(gl, "src/images/phase1.PNG");
        texturePhase2 = Textures.loadTexture(gl, "src/images/phase2.PNG");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
}