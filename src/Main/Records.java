package Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Lényegében egy segédosztály, amely a ranglistánk fájlba írását, illetve fájlból történő beolvasását támogatja.
 */
public class Records implements Serializable, Comparable<Records> {
    private String name;
    private int score;

    /**
     * Az osztályunk konstruktora, meghatározza a paramétereit, a megadott értékek alapján.
     * @param name A játékos nevét tárolja.
     * @param score A játékoshoz és az adott játékhoz tartozó időt méri
     */
    public Records(String name, int score){
        this.name = name;
        this.score = score;
    }

    /**
     * A kapott elemhez tartozó adatok fájlba írását valósítja meg ez a metódus.
     * @param oos ObjectOutputStream paraméter, a fájlba írást támogatja.
     */
    public void writeObject(ObjectOutputStream oos){
        try {
            oos.writeObject(name);
            oos.writeObject(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Egy adott rekord (mint egyed/elem) fájlból történő beolvasását valósítja meg.
     * @param ois ObjectInputStream paraméter, a fájlból olvasást támogatja.
     */
    public void readObject(ObjectInputStream ois){
        try {
            name = (String) ois.readObject();
            score = (int) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter: a name paraméter értékével tér vissza.
     * @return name - Visszatér a játékos nevével.
     */
    public String getName(){
        return name;
    }

    /**
     * Getter: a score paraméter értékével tér vissza.
     * @return score - Visszatér a játékos idejével.
     */
    public int getScore(){
        return score;
    }

    /**
     * Az adatok count paramétere szerinti sorrendezett tárolást támogató metódus.
     * @param r Egy Record típusú változó
     * @return -1, ha az aktuális idő nagyobb mint amihez épp hasonlítjuk
     *          1, ha kisebb
     *          0, különben.
     */
    @Override
    public int compareTo(Records r) {
        if(this.score < r.getScore()) return -1;
        else if(this.score > r.getScore()) return 1;
        else return 0;
    }
}
