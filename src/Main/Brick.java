package Main;

import javax.swing.*;

/**
 * A tégla (Brick) osztály, amely beállítja az adott elemhez rendelt képet, valamint a megjelenítési pozícióját.
 */
public class Brick extends Wizard{

    private boolean destroyed;

    /**
     * A Brick osztály konstruktora, meghatárpzza az adott tégla koordinátáit.
     * @param x koordináta értéke
     * @param y koordináta értéke
     */
    public Brick(int x, int y) {

        this.x = x;
        this.y = y;

        ImageIcon i = new ImageIcon("brick.png");
        image = i.getImage();

        iwidth = image.getWidth(null);
        iheight = image.getHeight(null);

        destroyed = false;
    }

    /**
     * @return destroyed Visszatér a destroyed logikai változó igazságértékével (true/false).
     */
    public boolean isDestroyed() {

        return destroyed;
    }

    /**
     * Beállitha a destroyed logikai változót a megadott igazságérték szerint.
     * @param val logikai változó
     */
    public void setDestroyed(boolean val) {

        destroyed = val;
    }

}
