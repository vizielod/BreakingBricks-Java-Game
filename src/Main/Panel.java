package Main;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Ez a Panel osztály, amely lényegében kiíratja az objektumokat a képernyőre, figyeli azok viselkedését, és ezek alapján folyamatosan újrarajzolja őket.
 * Lényegében ez az osztály vezérli a játék működését.
 */
public class Panel extends JPanel implements Constants{

    public ArrayList<Records> records = new ArrayList<>();

    private Ball ball;
    private String message = "Game Over";
    private Timer timer;
    private Timer timer2;
    private Brick bricks[];
    private Paddle paddle;
    private boolean ingame = true;


    public int count = 0;


    /**
     * Az osztály konstruktora, meghívja az inicialisáló metódust.
     */
    public Panel(){
        initGame();
    }

    /**
     * Ez a metódus inicializálja a játékhoz szökséges változókat. Megadja a téglák számát, elindít egy belső timert,
     * vagyis esetünkben kettőt. Egyiket a játék periódusidejével, egy másikat pedig 1000ms -os periódusidővel,
     * hogy meg tudjuk határozni a játékos játékban töltött idejét.
     */
    private void initGame() {

        addKeyListener(new TAdapter());
        setFocusable(true);

        bricks = new Brick[NUMBER_OF_BRICKS];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);

        //erre azért van szükség, mert a Period befolyásolja a ciklusidőt, így nem völtétlenül másodpercenként növelné a számlálónkat. Csak akkor növeli másodpercenként, ha a periódusidő 1000ms.
        timer2 = new Timer();
        timer2.scheduleAtFixedRate(new ScheduleTask(){
            public void run(){
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("records.txt"));
                    records = (ArrayList<Records>) ois.readObject();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //System.out.println(count);
                count++;
            }

        }, DELAY, 1000);
    }

    /**
     * Meghívja a create() függvényt.
     */
    @Override
    public void addNotify() {

        super.addNotify();
        create();
    }

    /**
     * Létrehozza a játékhoz szükséges labdát, ütőt, valamint téglákat, azok helyének meghatározásával.
     */
    public void create(){

        ball = new Ball();
        paddle = new Paddle();

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }
    }

    /**
     * Ha játékban vagyunk, akkor hívja a drawThings() metódust, ha a játéknak vége akkor pedig a gameOver() metódust.
     * @param g A képernyőre történő kiírást támogatja.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (ingame) {

            drawThings(g2d);
        } else {

            gameOver(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * Kirajzolja a képernyőre a megfelelő ablakba, a megfelelő helyekre a játék objektumait.
     * @param g2d A képernyőre történő kiírást támogatja.
     */
    private void drawThings(Graphics2D g2d){
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), this);

        for (int i = 0; i < NUMBER_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getWidth(),
                        bricks[i].getHeight(), this);
            }
        }

    }

    /**
     * A játék végkimenetele alapján szabályozza, hogy mi történjen azt követően.
     * Ha a játékos veszít, akkor egy "Sajnáljuk ön veszített" felirat fogadja, majd lehetősége van új játék indítására.
     * Amennyiben a játékos sikeresen vette az akadályokat. Akkor egy "Victory" felirat után megadhatja a nevét, amelyet a
     * program egy ranglistába tárol a játékidő alapján.
     * @param g2d A képernyőre történő kiírást támogatja.
     */
    public void gameOver(Graphics2D g2d){

        Font font = new Font("Veradana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);

        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(message,
                (Constants.WIDTH - metr.stringWidth(message)) / 2,
                Constants.WIDTH / 2);
        if(message == "Victory"){

            message = "Gartulálunk, az ön ideje: ";
            g2d.drawString(message + count + " s", (Constants.WIDTH - metr.stringWidth(message)) / 2 -15, Constants.WIDTH / 2 + 25);

            JFrame frame = new JFrame("Records");
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Container container = frame.getContentPane();
            container.setLayout(new FlowLayout());
            JTextField tf = new JTextField(20);

            JLabel label = new JLabel("Adja meg a nevét: ");
            JButton goButton = new JButton("Go");
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    records.add(new Records(tf.getText(), count));
                    Collections.sort(records);
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("records.txt"));
                        oos.writeObject(records);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    frame.setVisible(false);


                }
            });

            container.add(label);
            container.add(tf);
            container.add(goButton);
            frame.setSize(300, 100);
            frame.setVisible(true);
        }
        else if(message == "Game Over"){
            message = "Sajnáljuk ön veszített!";
            g2d.drawString(message, (Constants.WIDTH - metr.stringWidth(message)) / 2 , Constants.WIDTH / 2 + 25);
        }
    }

    /**
     * Segédosztály, amely figyeli hogy mikor nyomjuk le és engedjük fel a paddle-t irányító gombot.
     */
    private class TAdapter extends KeyAdapter {

        /**
         * Meghívja a Paddle osztály keyReleased osztályát.
         * @param event KeyEvent beépített osztály paramétere
         */
        @Override
        public void keyReleased(KeyEvent event) {
            paddle.keyReleased(event);
        }

        /**
         * Meghívja a Paddle osztály keyPressed osztályát.
         * @param event KeyEvent beépített osztály paramétere
         */
        @Override
        public void keyPressed(KeyEvent event) {
            paddle.keyPressed(event);
        }
    }

    /**
     * Minden PERIOD ms-ban meghívódik. A run() metódusával mozgatjuk az ütőt és ezáltal a labdát is.
     * Mindeküzben figyeljük az esetleges ütözéseket, és újrarajzoljuk az ablak tartalmát.
     */
    private class ScheduleTask extends TimerTask {
            @Override
            public void run() {
                ball.move();
                paddle.move();
                checkCollision();
                repaint();
            }
    }

    /**
     * Leállítja a játékot.
     */
    private void stopGame() {

        ingame = false;
        timer.cancel();
    }

    /**
     * Vizsgálja a játék folyamata közben történt eseményeket. Ha téglámak ötküzik, akkor eltűnteti az adott téglát,
     * a labda pedig visszapattan a megváltott iránya szerint.
     * Ha az ütőnek ütközik, akkor is visszapattan, közben megváltoztatja irányát.
     * Amennyiben a labda eléri az ablak alját, a játék leáll, és a játékosunk veszített.
     */
    private void checkCollision() {

        if (ball.getRect().getMaxY() > Constants.BOTTOM_EDGE) {
            stopGame();
        }

        for (int i = 0, j = 0; i < NUMBER_OF_BRICKS; i++) {

            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == NUMBER_OF_BRICKS) {
                message = "Victory";

                stopGame();
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setXDirection(-1);
                ball.setYDirection(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDirection(-1);
                ball.setYDirection(-1 * ball.getYDirection());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDirection(0);
                ball.setYDirection(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDirection(1);
                ball.setYDirection(-1 * ball.getYDirection());
            }

            if (ballLPos > fourth) {
                ball.setXDirection(1);
                ball.setYDirection(-1);
            }
        }

        for (int i = 0; i < NUMBER_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getRect().contains(pointRight)) {
                        ball.setXDirection(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {
                        ball.setXDirection(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {
                        ball.setYDirection(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {
                        ball.setYDirection(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
            }
        }
    }
}
