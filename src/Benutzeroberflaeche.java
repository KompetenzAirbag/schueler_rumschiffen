import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Benutzeroberflaeche extends JFrame {
    // Anfang Attribute
    public static DBManagerSQLite myDBManager = new DBManagerSQLite("schule_gag"); //Datenbank wird geladen
    private JButton bSpeichern = new JButton();
    private JButton bGespeichert = new JButton();
    private JButton bNaechste = new JButton();
    private JTextArea jTextArea1ScrollPane = new JTextArea();
    private Optionsfeld bgOrientierung = new Optionsfeld(this, "Sex. Orientierung:", Arrays.asList("Mann", "Frau", "keine Prf."), 20, 30);
    private JLabel lFigur = new JLabel("Figur:");
    private JComboBox cbFigur = new JComboBox(Hilfsklasse.figuren); //Figurenpräferenz
    private JLabel lFachgebiet = new JLabel("Schulisches Fachgebiet:");
    private JComboBox cbFachgebiet = new JComboBox(Hilfsklasse.profile); //Figurenpräferenz
    private JSlider slUeberaschungsfaktor = new JSlider(JSlider.HORIZONTAL, 0, 100, 20); //siehe Überraschungsfaktor in Kapitel "Was kann unser Programm?"
    private JLabel lUeberaschungsfaktor = new JLabel("Überaschungsfaktor:");
    private JLabel ich_suche_nach = new JLabel("Ich suche nach...");
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

        ich_suche_nach.setBounds(20, 0, 200, 20);
        cp.add(ich_suche_nach);
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
        String uefaktor_text = "In %, wie oft nicht dem Algorithmus entsprechende Profile angezeigt werden.";
        lUeberaschungsfaktor.setBounds(20, 75, 200, 20);
        lUeberaschungsfaktor.setToolTipText(uefaktor_text);
        cp.add(lUeberaschungsfaktor);
        slUeberaschungsfaktor.setToolTipText(uefaktor_text);
        slUeberaschungsfaktor.setBounds(20, 100, 200, 40);
        slUeberaschungsfaktor.setMajorTickSpacing(10);
        slUeberaschungsfaktor.setMinorTickSpacing(1);
        slUeberaschungsfaktor.setPaintTicks(true);
        slUeberaschungsfaktor.setPaintLabels(true);
        cp.add(slUeberaschungsfaktor);
        lFigur.setBounds(20, 150, 200, 20);
        cp.add(lFigur);
        cbFigur.setBounds(20, 170, 200, 20);
        cp.add(cbFigur);
        lFachgebiet.setBounds(20, 200, 200, 20);
        cp.add(lFachgebiet);
        cbFachgebiet.setBounds(20, 220, 200, 20);
        cp.add(cbFachgebiet);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Anmeldung();
    }
    //public static void main(String[] args) { new Registrierung(new Benutzeroberflaeche()); } // end of main
    //ublic static void main(String[] args) { new Benutzeroberflaeche(); } // end of main

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
        /*
        Hier wird immer ein neues Profil angezeigt, was laut dem Algorithmus gut zu dem Benutzer passt.
        Zunächst wird dafür geguckt, ob Präferenzen links im Bedienfeld gesetzt wurden, des Weiteren wird der Überraschungsfaktor ausgelesen.
        */
        //präferenzen: orientierung, überraschungsfaktor, figur, fachgebiet
        int orientierung;
        if (bgOrientierung.getSelection() == null) orientierung = 2;
        else orientierung = bgOrientierung.getSelection().getMnemonic();
        int ueberaschungsfaktor = (int)slUeberaschungsfaktor.getValue();
        String figur = cbFigur.getSelectedItem().toString();
        String fachgebiet = cbFachgebiet.getSelectedItem().toString();
    }

    public String[] finde_partner_algo(int orientierung, int ueberaschungsfaktor, String figur, String fachgebiet) {
        /*
        Hier wird "ausgerechnet", welcher Partner der Beste ist. Dazu muss zum einen der BMI berechnet werden, um aussagen über die "Figur" treffen zu können.
        Es wird ein Array von IDs erstellt, welche (Schüler/Benutzer) am besten passen. Diese Liste wird genutzt, um von den obersten 20% (Mittelfeld, unteres Feld) eine zufällige Person auszuwählen.
        Zurückgegeben wird dann die Schüler_ID, bzw ID des Benutzers
         */
        String[] a = new String[1];
        return a;
    }

    public void setze_border_zurueck() {
        Hilfsklasse.markiere_label(this.getContentPane(), "Geschlecht:", null);
        Hilfsklasse.markiere_label(this.getContentPane(), "Sex. Orientierung:", null);
    }
}
