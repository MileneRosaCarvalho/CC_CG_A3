package game;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

/**
 *
 * @author Milene Rosa Carvalho
 */
public abstract class KeyBoard implements KeyListener {
    private Cena cena;
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;

            case KeyEvent.VK_RIGHT:
                if (cena.barMovement < 0.7 && !cena.pause) {
                    cena.barMovement = cena.barMovement + 0.2f;
                }
                break;

            case KeyEvent.VK_LEFT:
                if (cena.barMovement > -0.7 && !cena.pause) {
                    cena.barMovement = cena.barMovement - 0.2f;
                }
                break;

            case KeyEvent.VK_M:
                if (cena.pause || cena.screen == 4 || cena.screen == 3 || cena.screen == 5) {
                    cena.resetData();
                    cena.screen = 0;
                }
                break;

            case KeyEvent.VK_S:
                if (cena.screen != 4 && cena.screen != 3) {
                    cena.screen = 1;
                }
                break;

            case KeyEvent.VK_R:
                if (cena.screen !=0 && cena.screen != 4 && cena.screen != 5 && cena.screen != 6) {
                    cena.resetData();
                    cena.screen = 1;
                }
                break;

            case KeyEvent.VK_1:
                if (cena.screen == 0) {
                cena.screen = 4;
            }
            break;

            case KeyEvent.VK_2:
                if (cena.screen == 0 || cena.screen == 6) {
                    cena.screen = 5;
                }

            case KeyEvent.VK_P:
                if (cena.screen !=4 && cena.screen !=0 ){
                    cena.pause = !cena.pause;
                }
        }
    }

    // Método para limitar um valor dentro de um intervalo
    private float clamp(float valor, float minimo, float maximo) {
        return Math.max(minimo, Math.min(valor, maximo));
    }
}
