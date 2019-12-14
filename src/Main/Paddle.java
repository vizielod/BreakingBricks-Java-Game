package Main;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * A játékos által irányított Paddle (ütő) osztály, az irányításához és beállításához szükséges metódusokkal.
 */
public class Paddle extends Wizard implements Constants {

    private int dx;

    /**
     * A Paddle (utő) osztály konstruktora. az egyedhez rendeli a képet, valamint beállítja a kiindulási pozicióját.
     */
    public Paddle(){

        ImageIcon i = new ImageIcon("paddle.png");
        image = i.getImage();

        iwidth = image.getWidth(null);
        iheight = image.getHeight(null);
        resetState();
    }

    /**
     * Az utő mozgását szabályozza. Kizárólag x tengely szerint tud működni, és nem tud kimenni az ablakból. A szélénél megütközik.
     */
    public void move(){
        x+=dx;
        if(x<=0){
            x=0;
        }
        if(x >= WIDTH - iwidth){
            x=WIDTH - iwidth;
        }
    }

    /**
     * Figyeli, hogy melyik gomb lett lenyomva. Balra nyíl esetén balra irányítja az ütőt.
     * Jobbra nyíl lenyomása esetén pedig jobbra.
     * @param event KeyEvent beépített osztály paramétere
     */
    public void keyPressed(KeyEvent event){

        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }
    }

    /**
     * Figyeli a lenyomott billenytyű felengedését.
     * Ha felengedtük a lenyomott gombot, akkor megáll az ütő egyhelyben.
     * @param event KeyEvent beépített osztály paramétere
     */
    public void keyReleased(KeyEvent event) {

        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    /**
     * Visszaállítja az ütő  koordinátáit a kezdőpozícióba.
     */
    private void resetState() {

        x = START_POS_PADDLE_X;
        y = START_POS_PADDLE_Y;

    }
}
