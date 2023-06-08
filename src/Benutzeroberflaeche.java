import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Benutzeroberflaeche extends JFrame {
    // Anfang Attribute
    private static DBManagerSQLite myDBManager = new DBManagerSQLite("schule_gag"); //Datenbank wird geladen
    private JButton bSpeichern = new JButton();
    private JButton bGespeichert = new JButton();
    private JButton bNaechste = new JButton();
    private JTextArea jTextArea1ScrollPane = new JTextArea();
    private Optionsfeld bgOrientierung = new Optionsfeld(this, "Sex. Orientierung:", Arrays.asList("Mann", "Frau", "keine Prf."), 20, 20);
    // Ende Attribute
    public Benutzeroberflaeche() {
        // Frame-Initialisierung
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 1000;
        int frameHeight = 650;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Parsnip");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Ende Komponenten


        bSpeichern.setBounds(292, 392, 171, 65);
        bSpeichern.setText("Speichern");
        bSpeichern.setMargin(new Insets(2, 2, 2, 2));
        bSpeichern.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bSpeichern_ActionPerformed(evt);
            }
        });
        cp.add(bSpeichern);
        bGespeichert.setBounds(730, 24, 171, 65);
        bGespeichert.setText("Gespeichert");
        bGespeichert.setMargin(new Insets(2, 2, 2, 2));
        bGespeichert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bGespeichert_ActionPerformed(evt);
            }
        });
        cp.add(bGespeichert);
        bNaechste.setBounds(580, 392, 171, 65);
        bNaechste.setText("Naechste");
        bNaechste.setMargin(new Insets(2, 2, 2, 2));
        bNaechste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bNaechste_ActionPerformed(evt);
            }
        });
        cp.add(bNaechste);
        jTextArea1ScrollPane.setBounds(364, 40, 313, 313);
        cp.add(jTextArea1ScrollPane);
        setVisible(true);
    }

    //public static void main(String[] args) { new Registrierung(new Benutzeroberflaeche()); } // end of main
    public static void main(String[] args) { new Benutzeroberflaeche(); } // end of main

    public void angemeldet(Benutzeroberflaeche bo) {
        ArrayList<Image> bilder = new ArrayList<Image>();
        Image img1 = null;
        Image img2 = null;
        try {
            img1 = ImageIO.read(new File("img/hintergrund/hintergrund_1.jpg"));
            img2 = ImageIO.read(new File("img/hintergrund/hintergrund_2.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bilder.add(img1);
        bilder.add(img2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new WechsleBild(bo, bilder, true, 2000, 2000);
            }
        }).start();
    }

    public void bGespeichert_ActionPerformed(ActionEvent evt) {
        setze_border_zurueck();
        if (bgOrientierung.getSelection() == null) {
            Hilfsklasse.markiere_label(getContentPane(), "Sex. Orientierung:", Color.RED);
            return;
        }
    }

    public void bSpeichern_ActionPerformed(ActionEvent evt) {

    }

    public void bNaechste_ActionPerformed(ActionEvent evt) {

    }

    public void setze_border_zurueck() {
        Hilfsklasse.markiere_label(this.getContentPane(), "Geschlecht:", null);
        Hilfsklasse.markiere_label(this.getContentPane(), "Sex. Orientierung:", null);
    }
}
