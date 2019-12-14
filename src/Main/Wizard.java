package Main;

import java.awt.*; //Image, Rectangle


/**
 * A Wizard(magyarul: varázsló, későbbiekben is fogok rá így hivatkozni) osztály egy segédosztály, amit felhasználnak az elemek osztályai.
 * Itt vannak azok az változók és metódusok, amiket a Brick, Ball, Paddle osztályok, valamint objektumok egyaránt felhasználnak.
 * Úgymond egy segédosztály ami megkönnyti a többi osztály működését, és nem kell ezeketa metódusokat minden osztályba külön definiálni,
 * megkönnyítve a program átláthatóságát és fejleszthetőségét.
 */
public class Wizard {

    protected int x;
    protected int y;
    protected int iwidth;
    protected int iheight;
    public Image image;

    /**
     * Beállítja az x paramétert a megadott értékre.
     * @param x az x koordináta értéke
     */
    public void setX(int x){
        this.x=x;
    }

    /**
     * Beállítja az y paramétert a megadott értékre.
     * @param y az y koordináta értéke
     */
    public void setY(int y){
        this.y=y;
    }

    /**
     * Visszatér az aktuális x paraméterrel.
     * @return x koordináta értéke
     */
    public int getX(){
        return x;
    }

    /**
     * Visszatér az aktuális y paraméterrel.
     * @return y koordináta értéke
     */
    public int getY(){
        return y;
    }

    /**
     * Visszatér az iwidth értékkel, ami a hozzá tartozó kép szélességét határozza meg.
     * @return iwidth
     */
    public int getWidth(){
        return iwidth;
    }

    /**
     * A hozzá tartozó kép magasságát határozza meg.
     * @return iheight  Visszatér az iheight értékkel
     */
    public int getHeight(){
        return iheight;
    }

    /**
     * @return image Visszatér az elemhez rendelt képpel.
     */
    Image getImage(){
        return image;
    }

    /**
     * Meghatározza az aktuális kép által elfoglalt helyet.
     * @return Rectangle(x, y, width, height) Egy Rectangle típussal tér vissza.
     */
    Rectangle getRect(){
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

}
