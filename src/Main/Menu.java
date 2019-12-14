package Main;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.TimerTask;


/**
 * Ez a Menu osztályunk, amely a User Interface megvalósítását támogatja.
 */
public class Menu extends Canvas{

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 *9;
    public static final int SCALE = 2;

    protected Image image;

    JFrame frame;
    JFrame recordframe;
    JLabel title;
    ImageIcon backButtonIcon = new ImageIcon("leftarrow.png");

    /**
     * Segédosztály, a Record gomb megnyomásával kiváltott történések támogatására.
     */
    public class RecordListener implements ActionListener{

        JButton backButton = new JButton("Back", backButtonIcon);
        Font fnt1 = new Font("calibri", Font.BOLD, 30);

        /**
         * Ha a menüben rákattintottunk a Records gombra, akkor betölti az annak megfelelő ablakot,
         * így láthatjuk és kiovlashatjuk a toplistát.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            JLabel subtitle = new JLabel();
            JLabel description = new JLabel();

            subtitle.setText("<html><h1><font color = 'red'>Records</font></h1></html>");
            subtitle.setFont(fnt1);
            subtitle.setBounds(283, 40, 400, 50);


            JTextArea tarea = new JTextArea();

            ArrayList<Records> records2 = new ArrayList<>();
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("records.txt"));
                records2 = (ArrayList<Records>) ois.readObject();
                int i = 1;
                for(Records r : records2){
                    if(i>10)break;
                    tarea.append(i+ ". " + r.getName() + " " + r.getScore() + "\n\n");
                    i++;
                }
            } catch (IOException ev) {
                ev.printStackTrace();
            } catch (ClassNotFoundException ev) {
                ev.printStackTrace();
            }

            Border border = BorderFactory.createLineBorder(Color.BLACK);
            tarea.setBorder(BorderFactory.createCompoundBorder(border,
                    BorderFactory.createEmptyBorder(10, 20, 10, 10))); //top, left bottom, right
            tarea.setBounds(150, 100, 340, 350);
            tarea.setEditable(false);
            Font fnt2 = new Font("arial", Font.BOLD, 14);
            tarea.setFont(fnt2);

            backButton.setBounds(20, 420, 100, 50);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(true);
                    recordframe.setVisible(false);
                }
            });

            Container recordContentPane = recordframe.getContentPane();
            recordContentPane.add(backButton);
            recordContentPane.add(title);
            recordContentPane.add(subtitle);
            recordContentPane.add(tarea);

            recordframe.setVisible(true);
            frame.setVisible(false);
        }
    }


    /**
     * A Menu osztályunk konstruktora, amely kirajzolja a menünket a képernyőre.
     * Támogatja a menüpontok közötti váltázokat, valamint egy gomb megnyomása segítségével innen indítható a játékonk.
     * Alapvetően 4 opciót tartalmaz: Play, Help, Records, Quit.
     * A Quit gomb megnyomásával ugyan azt a funkciót érhetjük el mint a jobb felső sarokban található x kattintásával,
     * vagyis bezáródik az alkalmazásunk.
     * A Records menüpont alatt megtekinthető az aktuális top 10-es ranglista, amennyiben már van 10 személy a ranglistán.
     * Ha nincs 10 személy, akkor mindenkit megjelenít, aki addig felkerült a listára.
     * A Help menüpont alatt egy rövid útmutató/leírás található a játékhoz.
     * A Play gomb megnyomásával pedig értelemszerűen maga a játék indítható el.
     */
    public Menu() {

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));     //640 * 480
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        //Label label = new Label();
        frame = new JFrame(Constants.TITLE);
        CardLayout c1 = new CardLayout();
        frame.add(this);
        frame.pack();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(800, 508, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        frame.setContentPane(new JLabel(imageIcon));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        //ImageIcon backButtonIcon = new ImageIcon("leftarrow.png");

        JButton playButton = new JButton("Play");
        JButton helpButton = new JButton("Help");
        JButton recordButton = new JButton("Records");
        JButton quitButton = new JButton("Quit");
        JButton backButton = new JButton("Back", backButtonIcon);
        JPanel pnlButton = new JPanel();
        title = new JLabel();
        JLabel mainMenuSubtitle = new JLabel();

        mainMenuSubtitle.setText("<html><h1><font color = 'red'>Main Menu</font></h1></html>");
        Font fnt1 = new Font("calibri", Font.BOLD, 30);
        mainMenuSubtitle.setFont(fnt1);
        mainMenuSubtitle.setBounds(265, 40, 400, 50);

        Container contentPane = frame.getContentPane();

        title.setText("Break Bricks");
        Font fnt0 = new Font("calibri", Font.BOLD, 50);
        title.setFont(fnt0);
        title.setBounds(200, 5, 300, 50);
        contentPane.add(title);
        contentPane.add(mainMenuSubtitle);


        //Play Button

        playButton.setBounds(245, 100, 150, 50);
        contentPane.add(playButton);
        playButton.addActionListener(new ActionListener() {
            /**
             *  A Play gombra való kattintás haátásra elindul a játék egy új ablakban.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                Game.initUI();

            }
        });

        //Help Button

        JFrame helpframe = new JFrame(Constants.TITLE);
        helpframe.add(this);
        helpframe.pack();

        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException ev) {
            ev.printStackTrace();
        }
        helpframe.setContentPane(new JLabel(imageIcon));

        helpframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helpframe.setResizable(false);
        helpframe.setLocationRelativeTo(null);


        helpButton.setBounds(245, 200, 150, 50);
        contentPane.add(helpButton);
        helpButton.addActionListener(new ActionListener(){
            /**
             * A Help gombra való kattintás hatására megjelenik a játék rövid leírása.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                JLabel subtitle = new JLabel();
                JLabel description = new JLabel();

                subtitle.setText("<html><h1><font color = 'red'>Description</font></h1></html>");
                subtitle.setFont(fnt1);
                subtitle.setBounds(265, 40, 400, 50);
                description.setText("<html><body><font color = 'white'>There are no specific rules in this game. " +
                        "<br>Your only goal is to destroy the bricks with the ball, using the paddle. You can move the paddle with the right and left arrows on your keyboard." +
                        "<br>You can also check the records, using the record button in the Main Menu. If you were able to finish the game successfuly, then you can add your name to the record list, " +
                        "but only the top 10 players are shown.</font></body></html>");
                Font fnt2 = new Font("arial", Font.BOLD, 20);
                description.setFont(fnt2);
                description.setBounds(20, 100, 600, 200);

                backButton.setBounds(20, 420, 150, 50);
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setVisible(true);
                        helpframe.setVisible(false);
                    }
                });

                Container helpContentPane = helpframe.getContentPane();
                helpContentPane.add(backButton);
                helpContentPane.add(title);
                helpContentPane.add(subtitle);
                helpContentPane.add(description);

                helpframe.setVisible(true);
                frame.setVisible(false);

            }
        });

        recordframe = new JFrame(Constants.TITLE);
        recordframe.add(this);
        recordframe.pack();

        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException ev) {
            ev.printStackTrace();
        }
        recordframe.setContentPane(new JLabel(imageIcon));

        recordframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recordframe.setResizable(false);
        recordframe.setLocationRelativeTo(null);

        //Records Button
        recordButton.setBounds(245, 300, 150, 50);
        contentPane.add(recordButton);
        recordButton.addActionListener(new RecordListener());

        //Quit Button
        quitButton.setBounds(245, 400, 150, 50);
        contentPane.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            /**
             * A Quit gombra kattintva bezárja az alkalmazásunkat.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        frame.setVisible(true);

    }


}
