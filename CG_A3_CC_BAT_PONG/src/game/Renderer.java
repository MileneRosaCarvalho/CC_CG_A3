package game;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
/**
 *
 * @author Milene Rosa Carvalho
 */
public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 1280;
    public static int screenHeight = 720;

    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        window.setResizable(false);

        //Cena cena = new Cena();
        Cena cena = new Cena(screenWidth,screenHeight);
        cena.height = screenHeight;
        cena.widgth = screenWidth;

        window.addGLEventListener(cena);

        window.addKeyListener(new KeyBoard(cena) {
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        FPSAnimator framesPerSecond = new FPSAnimator(window, 60);
        framesPerSecond.start();

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                framesPerSecond.stop();
                System.exit(0);
            }
        });

        window.setFullscreen(true);

        window.setVisible(true);
    }

    public static void main(String[] args) {
        init();
    }
}
