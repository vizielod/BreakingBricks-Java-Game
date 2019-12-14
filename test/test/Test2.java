package test;

import Main.*;
import Main.Records;
import org.junit.Test;
import org.junit.Assert;
import java.io.*;

/**
 * Created by Előd on 2016.11.29..
 */
public class Test2 {

    /**
     * A Wizard osztály setX(), illetve getX() függvényeinek tesztelése.
     */
    @Test
    public void TestWizardSetAndGetX(){
        Wizard wizard = new Wizard();
        wizard.setX(5);
        Assert.assertEquals(5, wizard.getX());
        int x = wizard.getX();
        Assert.assertEquals(5, x);

    }


    /**
     * A Wizard osztály setY(), illetve getY() függvényeinek tesztelése.
     */
    @Test
    public void TestWizardSetAndGetY(){
        Wizard wizard = new Wizard();
        wizard.setY(13);
        Assert.assertEquals(13, wizard.getY());
        int y = wizard.getY();
        Assert.assertEquals(13, y);
    }


    /**
     * A Ball osztály setXDirection(), illetve getXDirection() függvényeinek tesztelése.
     */
    @Test
    public void TestBallSetAndGetXDirection(){
        Ball ball = new Ball();
        ball.setXDirection(10);
        int x = ball.getXDirection();
        Assert.assertEquals(10, x);
    }

    /**
     * A Ball osztály setYDirection(), illetve getYDirection() függvényeinek tesztelése.
     */
    @Test
    public void TestBallSetAndGetYDirection(){
        Ball ball = new Ball();
        ball.setYDirection(10);
        int y = ball.getYDirection();
        Assert.assertEquals(10, y);
    }

    /**
     * A Record osztály readObject() függcényének a tesztelése, amely egy fájlból olvas be adatokat,
     * amennyiben a fájl létezik. Viszotn ha nem létezik a megadott nevű fájl, IOExeptiont dob.
     * @throws IOException - IO kivételt dob hiba esetén.
     */
    @Test(expected = IOException.class)
    public void RecordReadTest() throws IOException {
        Records record = new Records("Elod", 13);
        record.readObject(new ObjectInputStream(new FileInputStream("sss.txt")));

    }

    /**
     * A Brick osztály isDestroyed(), illetve setDestroyed() függvényeinek tesztelése.
     */
    @Test
    public void TestBrickIsDestroyedAndsetDestroyed(){
        Brick brick = new Brick(10, 10); //destroyed értéke alapvetően false
        Assert.assertFalse(brick.isDestroyed());
        brick.setDestroyed(true);
        Assert.assertTrue(brick.isDestroyed());
    }



}
