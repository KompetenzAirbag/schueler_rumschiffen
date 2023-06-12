import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Benutzeroberflaeche extends JFrame {
    // Anfang Attribute
    public static DBManagerSQLite myDBManager = new DBManagerSQLite("schule_gag"); //Datenbank wird geladen
    private JButton bSpeichern = new JButton();
    private JButton bGespeichert = new JButton();
    private JButton bNaechste = new JButton();
    private JTextArea jTextArea1ScrollPane = new JTextArea();
    private Optionsfeld bgOrientierung;
    private JLabel lFigur = new JLabel("Figur:");
    private JComboBox cbFigur = new JComboBox(Hilfsklasse.figuren); //Figurenpräferenz
    private JLabel lFachgebiet = new JLabel("Schulisches Fachgebiet:");
    private JComboBox cbFachgebiet = new JComboBox(Hilfsklasse.profile); //Figurenpräferenz
    private JSlider slUeberaschungsfaktor = new JSlider(JSlider.HORIZONTAL, 0, 100, 20); //siehe Überraschungsfaktor in Kapitel "Was kann unser Programm?"
    private JLabel lUeberaschungsfaktor = new JLabel("Überaschungsfaktor:");
    private JLabel ich_suche_nach = new JLabel("Ich suche nach...");
    private String benutzer_id = "1";
    // Ende Attribute
    public Benutzeroberflaeche() {
        // Frame-Initialisierung
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 1280;//1000
        int frameHeight = 720;//650
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Parsnip");
        setResizable(false);
        int zufallszahl = (int) (Math.random() * ((5-1)+ 1)) + 1;
        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/hintergrund/hintergrund_" + zufallszahl + ".jpg")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Container cp = getContentPane();
        cp.setLayout(null);
        // Ende Komponenten

        bgOrientierung = new Optionsfeld(this, "Sex. Orientierung:", Arrays.asList("Mann", "Frau", "keine Prf."), 20, 30);
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

    /*public static void main(String[] args) {
        new Anmeldung();
    }*/
    //public static void main(String[] args) { new Registrierung(new Benutzeroberflaeche()); } // end of main
    public static void main(String[] args) { new Benutzeroberflaeche(); } // end of main

    public void angemeldet(Benutzeroberflaeche bo, String id) {
        benutzer_id = id;
        /*ArrayList<Image> bilder = new ArrayList<Image>();
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
        }).start();*/
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
        finde_partner_algo(orientierung, ueberaschungsfaktor, figur, fachgebiet);
    }

    public String[][] finde_partner_algo(int orientierung, int ueberaschungsfaktor, String figur, String fachgebiet) {
        /*
        Hier wird "ausgerechnet", welcher Partner der Beste ist. Dazu muss zum einen der BMI berechnet werden, um aussagen über die "Figur" treffen zu können.
        Es wird ein Array von IDs erstellt, welche (Schüler/Benutzer) am besten passen. Diese Liste wird genutzt, um von den obersten 20% (Mittelfeld, unteres Feld) eine zufällige Person auszuwählen.
        Zurückgegeben wird dann die Schüler_ID, bzw ID des Benutzers
         */

        //Dieser Block gehört zur Schülerabfrage und Benutzerabfrage; da die Tabelle anders strukturiert ist, muss die Abfrage der Benutzertabelle separat verlaufen, daher zwei unterschiedliche Variablen
        String schueler_sql = "SELECT *, gewicht/(groesse*groesse) as BMI FROM schueler WHERE"; //Anfang des SQL-Befehls
        String benutzer_sql = "SELECT * FROM benutzer WHERE";
        String _sql = "";
        switch (orientierung) {
            case 0: _sql += " Geschlecht = 'm'"; break;
            case 1: _sql += " Geschlecht = 'w'"; break;
            case 2: _sql += " (Geschlecht = 'm' OR Geschlecht = 'w')"; break; //macht weiteren Code einfacher, wenn diese Bedingung immer gefüllt wird
        }
        schueler_sql += _sql;
        benutzer_sql += _sql;
        if (!figur.equals("keine Angabe")) {
            int gesuchter_BMI = Hilfsklasse.string_zu_bmi(figur);
            schueler_sql += " AND BMI<" + gesuchter_BMI;
            benutzer_sql += " AND figur = '" + figur + "'";
        }

        //Ab hier beginnt die Schülerabfrage, wobei geschlecht und Figur (bzw. BMI) bedacht werden
        String[][] ergebnis_schueler = myDBManager.sqlAnfrageAusfuehren(schueler_sql);
        List<List<String>> passende_schueler = new ArrayList<>();

        for (int i = 1; i < ergebnis_schueler.length; i++) {
            if (Hilfsklasse.fachgebiet(ergebnis_schueler[i][14]).equals(fachgebiet) || fachgebiet.equals("keine Angabe")) {
                passende_schueler.add(new ArrayList<String>(Arrays.asList(ergebnis_schueler[i][0], ergebnis_schueler[i][2], ergebnis_schueler[i][1], ergebnis_schueler[i][3], ergebnis_schueler[i][11], "true", "")));
            }
        }
        //Hier beginnt die Benutzerabfrage
        String[][] ergebnis_benutzer = myDBManager.sqlAnfrageAusfuehren(benutzer_sql);
        //int geschlechtsindex = Arrays.asList(ergebnis_benutzer[0]).indexOf("Lieblingsfach");
        for (int i = 1; i < ergebnis_benutzer.length; i++) {
            if (Hilfsklasse.fachgebiet(ergebnis_benutzer[i][9]).equals(fachgebiet) || fachgebiet.equals("keine Angabe")) {
                String ist_schueler = "false";
                String id = ergebnis_benutzer[i][0];
                if (!ergebnis_benutzer[i][4].equals("null")) {
                    ist_schueler = "true";
                    id = ergebnis_benutzer[i][4];
                }
                passende_schueler.add(new ArrayList<String>(Arrays.asList(id, ergebnis_benutzer[i][1], "", ergebnis_benutzer[i][6], ergebnis_benutzer[i][5], ist_schueler, "")));
                //passende_schueler.add(Arrays.stream(new String[]{id , ergebnis_benutzer[i][1], "", ergebnis_benutzer[i][6], ergebnis_benutzer[i][5], ist_schueler, ""}).toList());
            }
        }
        /*
        Ab hier beginnt die "richtige" Wertung des Algorithmus, basierend auf der Größe und dem Alter.
        Alle bis jetzt nach den Daten übereinstimmende Schüler (bei individueller Suchanfrage z.B. nur Männer oder nur Naturwissenschaftler) sind in der List passende_schueler gespeichert.
        Im Folgenden wird nun der Algorithmus die Suche weiter eingrenzen, indem er Punkte vergibt. Je näher das Alter, desto mehr Punkte. Je ähnlicher die Größe, desto mehr Punkte.
         */
        String sql = "SELECT * FROM benutzer WHERE ID = " + benutzer_id + "";
        String[] benutzer = myDBManager.sqlAnfrageAusfuehren(sql)[1];
        String[][] sortierte_passende_schueler = new String[passende_schueler.size()][7];
        for (int i = 0; i < passende_schueler.size(); i++) {
            if (!benutzer_id.equals(passende_schueler.get(i).get(0))) {
                int altersunterschied = Hilfsklasse.altersunterschied(benutzer[6], passende_schueler.get(i).get(3));
                int groessenunterschied = (int) Math.abs((Double.parseDouble(benutzer[5]) - Double.parseDouble(passende_schueler.get(i).get(4))) * 4000);
                int punkte = altersunterschied+groessenunterschied;
                passende_schueler.get(i).set(6, punkte + ""); // je kleiner die Punkte, desto besser passt die Person
                //hier wird der Schüler sortiert in ein Array eingefügt


                for(int j = sortierte_passende_schueler.length - 1; j > 0; j--) {
                    int alte_punkte;
                    if (sortierte_passende_schueler[j][6] == null) alte_punkte = 0;
                    else alte_punkte = Integer.parseInt(sortierte_passende_schueler[j][6]);
                    if (alte_punkte == 0) continue; // skip last elements to avoid array index out of bound
                    sortierte_passende_schueler[j + 1] = sortierte_passende_schueler[j];       // shift elements forward
                    if (alte_punkte <= punkte) {       // if we found the right spot
                        String[] neues_array = new String[passende_schueler.get(i).size()];
                        sortierte_passende_schueler[j] = passende_schueler.get(i).toArray(neues_array);        // place the new element and
                        System.out.println(Arrays.toString(passende_schueler.get(i).toArray(neues_array)));
                        break;                 // break out the loop
                    }
                }

                /*for (int j = 0; j < sortierte_passende_schueler.length; j++) {
                    System.out.println(Arrays.toString(sortierte_passende_schueler[j]));
                }*/

                /*int j;
                for(j = 0; i < sortierte_passende_schueler.length; i++){
                    int alte_punkte;
                    if (sortierte_passende_schueler[j][6] == null) alte_punkte = 0;
                    else alte_punkte = Integer.parseInt(sortierte_passende_schueler[j][6]);
                    if(alte_punkte > punkte) break;
                }
                for(int k = sortierte_passende_schueler.length-2; k >= j; k--){
                    sortierte_passende_schueler[k+1] = sortierte_passende_schueler[k];
                }
                System.out.println(Arrays.toString(passende_schueler.get(i).toArray(new String[0])));
                //sortierte_passende_schueler[j] = passende_schueler.get(i).toArray(new String[0]);
                System.out.println(Arrays.toString(sortierte_passende_schueler[j]));*/
            }
        }

        //Hier wird die Liste nun sortiert, damit wir später die besten 20% herausfiltern können.

        String[][] passende_schuelers = new String[1][2];
        return passende_schuelers; //boolean ueberaschung = Hilfsklasse.prozentuelle_auswahl(ueberaschungsfaktor); wird genutzt, wo finde_partner_algo genutzt wird
    }

    public void setze_border_zurueck() {
        Hilfsklasse.markiere_label(this.getContentPane(), "Geschlecht:", null);
        Hilfsklasse.markiere_label(this.getContentPane(), "Sex. Orientierung:", null);
    }
}
