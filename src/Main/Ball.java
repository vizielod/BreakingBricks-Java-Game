package Main;

import javax.swing.*;

/**
 * A labda (Ball) elem osztálya. Amely beállítja a labda kiindulási irányát, mozgását, valamint a megfelelő képet hozzárendeli az egyedhez.
 */
public class Ball extends Wizard implements Constants {

    private int xdirection;
    private int ydirection;

    //Konstruktor

    /**
     * A Ball osztály konstruktora, megadja a labda kiindulási irányát, valamint betölti a megfelelő képet.
     */
    public Ball() {
        xdirection = 1;
        ydirection = -1;

        ImageIcon i = new ImageIcon("ball2.png");//paints an icon from a GIF, JPEG, or PNG image (paints icons from iamges)
        image = i.getImage();

        iwidth = image.getWidth(null);
        iheight = image.getHeight(null);

        resetState();
    }

    /**
     * A labda mozgását szabályozó metódus. Ha a labda falnak ütközik, akkor visszapattan.
     */
    public void move(){
        x += xdirection;
        y += ydirection;

        if(x==0){
            setXDirection(1);
        }

        if(x == WIDTH - iwidth){ //ha a szélének ütközik, váltson irányt
            setXDirection(-1);
        }

        if(y==0){
            setYDirection(1);
        }
    }

    /**
     * Visszaállítja a labdát a kezdő pozicióba.
     */
    private void resetState(){
        x = START_POS_BALL_X;
        y = START_POS_BALL_Y;
    }

    /**
     * Beállítja a labda x tengely menti irányát az átvett paraméter szerint.
     * @param x - A paraméter ami meghatározza az xdirection értékét.
     */
    public void setXDirection(int x){
        xdirection = x;
    }

    /**
     * Az aktuális x szerinti iránnyal tér vissza.
     * @return xdirection - x tengely szerinti irány
     */
    public int getXDirection(){
        return xdirection;
    }

    /**
     * Beállítja a labda y tengely menti irányát az átvett paraméter szerint.
     * @param y A paraméter ami meghatározza az ydirection értékét.
     */
    public void setYDirection(int y){
        ydirection = y;
    }

    /**
     * Az aktuális y szerinti iránnyal tér vissza.
     * @return ydirection - y tengely szerinti irány
     */
    public int getYDirection(){
        return ydirection;
    }
}
