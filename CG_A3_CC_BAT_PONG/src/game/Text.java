package game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Milene Rosa Carvalho
 */
public class Text {
    private TextRenderer textRenderer;
    public int mode;

    public Text() {
        textRenderer = new TextRenderer(new Font("BatmanForeverAlternate", Font.BOLD, 25));
    }
    
    public GL2 renderText(GL2 gl, int xPosition, int yPosition, Color color, String phrase, int width, int height) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer.beginRendering(width, height);
        textRenderer.setColor(color);
        textRenderer.draw(phrase, xPosition, yPosition);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
        return gl;
    }
    
    public GL2 renderText(GL2 gl, int xPosition, int yPosition, Color color, String phrase, int fontSize, int width, int height) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        TextRenderer textRendererSpecific = new TextRenderer(new Font("BatmanForeverAlternate", Font.BOLD, fontSize));
        textRendererSpecific.beginRendering(width, height);
        textRendererSpecific.setColor(color);
        textRendererSpecific.draw(phrase, xPosition, yPosition);
        textRendererSpecific.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
        return gl;
    }
    
    public GL2 renderTextSoft(GL2 gl, int xPosition, int yPosition, Color cor, String phrase,int widgth, int height, long time) {
        long currentTime = System.currentTimeMillis();
        long timePast = currentTime - time;
        float alpha = (float) Math.abs(Math.sin(timePast / 1300.0));
        cor = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), (int) (alpha * 255));
        renderText(gl, xPosition, yPosition, cor, phrase, widgth, height);
        gl.glColor4f(1f, 1f, 1f, 1f);
        return gl;
    }
}
