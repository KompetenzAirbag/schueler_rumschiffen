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
    private JButton bAktualisieren = new JButton();
    private JTextArea jTextArea1ScrollPane = new JTextArea();
    private JTextArea jtaGespeicherteProfile = new JTextArea();
    private Optionsfeld bgOrientierung;
    private JLabel lFigur = new JLabel("Figur:");
    private JComboBox cbFigur = new JComboBox(Hilfsklasse.figuren); //Figurenpräferenz
    private JLabel lFachgebiet = new JLabel("Schulisches Fachgebiet:");
    private JComboBox cbFachgebiet = new JComboBox(Hilfsklasse.profile); //Figurenpräferenz
    private JSlider slUeberaschungsfaktor = new JSlider(JSlider.HORIZONTAL, 0, 100, 20); //siehe Überraschungsfaktor in Kapitel "Was kann unser Programm?"
    private JLabel lUeberaschungsfaktor = new JLabel("Überaschungsfaktor:");
    private JLabel ich_suche_nach = new JLabel("Ich suche nach...");
    private String benutzer_id = "1";
    private List<ArrayList<String>> passende_partner;
    private List<String> momentanes_profil;
    private Container cp;
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
        cp = getContentPane();
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
        bNaechste.setText("Nächste");
        bNaechste.setMargin(new Insets(2, 2, 2, 2));
        bNaechste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bNaechste_ActionPerformed(evt);
            }
        });
        cp.add(bNaechste);
        bAktualisieren.setBounds(40, 280, 160, 30);
        bAktualisieren.setText("Suche Aktualisieren");
        bAktualisieren.setMargin(new Insets(2, 2, 2, 2));
        bAktualisieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                bAktualisieren_ActionPerformed(evt);
            }
        });
        cp.add(bAktualisieren);
        jtaGespeicherteProfile.setBounds(730, 120, 200, 100);
        cp.add(jtaGespeicherteProfile);
        jtaGespeicherteProfile.hide();
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

    public void angemeldet(Benutzeroberflaeche bo, String id) {
        benutzer_id = id;
    }

    public void bGespeichert_ActionPerformed(ActionEvent evt) {
        if (jtaGespeicherteProfile.isVisible()) {
            jtaGespeicherteProfile.hide();
            return;
        }
        String sql = "SELECT s.Vorname, s.Name, s.ID_Nummer, g.ist_schueler FROM gespeicherte_profile g, schueler s WHERE g.ist_schueler = 'true' AND g.partner_id = s.ID_Nummer AND g.benutzer_id = '" + benutzer_id + "'";
        String[][] ergebnis_schueler = myDBManager.sqlAnfrageAusfuehren(sql);
        sql = "SELECT b.Benutzername, b.ID, g.ist_schueler FROM gespeicherte_profile g, benutzer b WHERE g.partner_id = b.ID AND g.ist_schueler = 'false' AND g.benutzer_id = '" + benutzer_id + "'";
        String[][] ergebnis_benutzer = myDBManager.sqlAnfrageAusfuehren(sql);
        String[][] ergebnis = new String[ergebnis_benutzer.length+ergebnis_schueler.length-2][3];
        for (int i = 0; i < ergebnis.length; i++) {
            if (i<ergebnis_schueler.length-1) {
                ergebnis[i][0] = ergebnis_schueler[i+1][0] + " " + ergebnis_schueler[i+1][1];
                ergebnis[i][1] = ergebnis_schueler[i+1][2];
                ergebnis[i][2] = ergebnis_schueler[i+1][3];
            }
            else if (ergebnis_benutzer.length > 1){
                int korrektur = 0;
                if (ergebnis_schueler.length == 1) korrektur = 1;
                ergebnis[i][0] = ergebnis_benutzer[ergebnis_benutzer.length-i-korrektur][0];
                ergebnis[i][1] = ergebnis_benutzer[ergebnis_benutzer.length-i-korrektur][1];
                ergebnis[i][2] = ergebnis_benutzer[ergebnis_benutzer.length-i-korrektur][2];
            }
        }
        JButton[] namen = new JButton[ergebnis.length];
        for (int i = 0; i < ergebnis.length; i++) {
            namen[i] = new JButton(ergebnis[i][0]);
            int finalI = i;
            namen[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jTextArea1ScrollPane.setText(Hilfsklasse.benutzerdaten_als_text(ergebnis[finalI][1], Boolean.parseBoolean(ergebnis[finalI][2])));
                    for (int j = 0; j < namen.length; j++) {
                        namen[j].hide();
                        cp.remove(namen[j]);
                    }
                }
            });
            namen[i].setBounds(730, 120+i*20, 200, 20);
            cp.add(namen[i]);
            namen[i].show();
        }
    }

    public void bSpeichern_ActionPerformed(ActionEvent evt) {
        String sql = "SELECT * FROM gespeicherte_profile WHERE benutzer_id = " + benutzer_id + " AND partner_id = '" + momentanes_profil.get(0) + "'";
        int ergebnis = myDBManager.sqlAnfrageAusfuehren(sql).length;

        if (ergebnis == 1) {
            String ist_schueler = "true";
            if (Hilfsklasse.ist_numerisch(momentanes_profil.get(0))) ist_schueler = "false";
            int speicherung = Hilfsklasse.generiere_neue_id("gespeicherte_profile");
            sql = "INSERT INTO gespeicherte_profile  VALUES (" + speicherung + ", " + benutzer_id + ", '" + momentanes_profil.get(0) + "', '" + ist_schueler + "')";
            myDBManager.datensatzEinfuegen(sql);
        }
    }

    public void bAktualisieren_ActionPerformed(ActionEvent evt) {
        int orientierung;
        if (bgOrientierung.getSelection() == null) orientierung = 2;
        else orientierung = bgOrientierung.getSelection().getMnemonic();
        String figur = cbFigur.getSelectedItem().toString();
        String fachgebiet = cbFachgebiet.getSelectedItem().toString();
        passende_partner = finde_partner_algo(orientierung, figur, fachgebiet);
    }

    public void bNaechste_ActionPerformed(ActionEvent evt) {
        /*
        Hier wird immer ein neues Profil angezeigt, was laut dem Algorithmus gut zu dem Benutzer passt.
        Zunächst wird dafür geguckt, ob Präferenzen links im Bedienfeld gesetzt wurden, des Weiteren wird der Überraschungsfaktor ausgelesen.
        */
        int ueberaschungsfaktor = (int)slUeberaschungsfaktor.getValue(); //liest Überaschungsfaktor aus
        if (passende_partner == null) { //falls die Suchdaten noch nicht aktualisiert wurde, werden sie hier "manuell" aktualisiert
            bAktualisieren_ActionPerformed(null);
        }
        boolean ueberaschung = Hilfsklasse.prozentuelle_auswahl(ueberaschungsfaktor);
        if (!ueberaschung) {
            if (passende_partner.size() > 20) { //nur wenn die Partnerauswahl groß genug ist...
                passende_partner = passende_partner.subList(0, 19);
            }
        }
        int zufallszahl = (int) (Math.random() * passende_partner.size());
        momentanes_profil = passende_partner.get(zufallszahl);

        jTextArea1ScrollPane.setText(Hilfsklasse.benutzerdaten_als_text(momentanes_profil.get(0), Boolean.parseBoolean(momentanes_profil.get(6))));
    }

    public List<ArrayList<String>> finde_partner_algo(int orientierung, String figur, String fachgebiet) {
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
        List<ArrayList<String>> passende_schueler = new ArrayList<ArrayList<String>>();

        for (int i = 1; i < ergebnis_schueler.length; i++) {
            if (Hilfsklasse.fachgebiet(ergebnis_schueler[i][14]).equals(fachgebiet) || fachgebiet.equals("keine Angabe")) {
                passende_schueler.add(new ArrayList<String>(Arrays.asList(ergebnis_schueler[i][0], ergebnis_schueler[i][2], ergebnis_schueler[i][1], ergebnis_schueler[i][3], ergebnis_schueler[i][11], ergebnis_schueler[i][6], "true", "")));
            }
        }
        //Hier beginnt die Benutzerabfrage
        String[][] ergebnis_benutzer = myDBManager.sqlAnfrageAusfuehren(benutzer_sql);
        for (int i = 1; i < ergebnis_benutzer.length; i++) {
            if (Hilfsklasse.fachgebiet(ergebnis_benutzer[i][9]).equals(fachgebiet) || fachgebiet.equals("keine Angabe")) {
                String ist_schueler = "false";
                String id = ergebnis_benutzer[i][0];
                if (!ergebnis_benutzer[i][4].equals("null")) {
                    ist_schueler = "true";
                    id = ergebnis_benutzer[i][4];
                }
                if (!benutzer_id.equals(id)) {
                    passende_schueler.add(new ArrayList<String>(Arrays.asList(id, ergebnis_benutzer[i][1], "", ergebnis_benutzer[i][6], ergebnis_benutzer[i][5], ergebnis_benutzer[i][8], ist_schueler, "")));
                }
            }
        }
        /*
        Ab hier beginnt die "richtige" Wertung des Algorithmus, basierend auf der Größe und dem Alter.
        Alle bis jetzt nach den Daten übereinstimmende Schüler (bei individueller Suchanfrage z.B. nur Männer oder nur Naturwissenschaftler) sind in der List passende_schueler gespeichert.
        Im Folgenden wird nun der Algorithmus die Suche weiter eingrenzen, indem er Punkte vergibt. Je näher das Alter, desto mehr Punkte. Je ähnlicher die Größe, desto mehr Punkte.
         */
        String sql = "SELECT * FROM benutzer WHERE ID = " + benutzer_id + "";
        String[] benutzer = myDBManager.sqlAnfrageAusfuehren(sql)[1];
        for (int i = 0; i < passende_schueler.size(); i++) {
            int altersunterschied = Hilfsklasse.altersunterschied(benutzer[6], passende_schueler.get(i).get(3));
            int groessenunterschied = (int) Math.abs((Double.parseDouble(benutzer[5]) - Double.parseDouble(passende_schueler.get(i).get(4))) * 4000);
            int punkte = altersunterschied+groessenunterschied;
            passende_schueler.get(i).set(7, punkte + ""); // je kleiner die Punkte, desto besser passt die Person
        }

        //Hier wird die Liste nun sortiert, damit wir später die besten 20% herausfiltern können.
        passende_schueler.sort(new Comparator<ArrayList<String>>() { //Fremdcode inspiration durch https://stackoverflow.com/questions/20480723/how-to-sort-2d-arrayliststring-by-only-the-first-element
            @Override
            public int compare(ArrayList<String> l1, ArrayList<String> l2) {
                return Integer.parseInt(l1.get(7)) - Integer.parseInt(l2.get(7));
            }
        });
        return passende_schueler;
    }

    public void setze_border_zurueck() {
        Hilfsklasse.markiere_label(this.getContentPane(), "Geschlecht:", null);
        Hilfsklasse.markiere_label(this.getContentPane(), "Sex. Orientierung:", null);
    }
}
