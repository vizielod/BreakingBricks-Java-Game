package Main;

import javax.swing.*;
import java.awt.image.BufferedImage;


/**
 * A main függvényt tartalmazó osztály, ez indítja el az alkalmazás működését.
 */
public class Game{

    /**
     * meghívja az initUI() függvényt.
     */
    public Game(){
        initUI();
    }

    /**
     * Ez a metódus meghatározza azt az ablakot, amelyikben a játék futni fog, és meghívja a Panel osztály konstruktorát.
     * Tehát lényegében ennek a függvénynek a hívásával tudjuk elindítani a játékot.
     */
    public static void initUI (){
        JFrame frame = new JFrame(Constants.TITLE);

        frame.add(new Panel());


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Csak az aktuálsi ablakot zárja be és nem az egész alkalmazást.
        frame.setSize(Constants.WIDTH, Constants.HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);


    }

    /**
     * A main függvény. Elindítja az alkalmazás működését, egy Menu típusú változó létrehozásának a segítségével.
     * @param args - main függvény default paramétere
     */
    public static void main(String[] args) {
        Menu menu = new Menu();

    }
}
