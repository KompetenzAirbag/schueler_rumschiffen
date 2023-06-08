import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.border.LineBorder;

public class Registrierung extends JFrame {
    // Anfang Attribute
    private JButton bRegistrieren = new JButton(); //Registrierbutton
    private Neues_Textfeld tfGroesse = new Neues_Textfeld(150, 10, 179, 33, "Größe (in cm)"); //Eingabe der Größe
    private Neues_Textfeld tfGeburtstag = new Neues_Textfeld(150, 50, 179, 33, "Geburtstag (yyyy-mm-dd)"); //Eingabe des Geburtstages
    private Optionsfeld bgOrientierungsgruppe = new Optionsfeld(this, "Sex. Orientierung:", Arrays.asList("Mann", "Frau"), 150, 83);
    private Optionsfeld bgGeschlechtssgruppe = new Optionsfeld(this, "Geschlecht:", Arrays.asList("Mann", "Frau"), 150, 130);
    private JLabel lLieblingsfach = new JLabel("Lieblingsfach:");
    private JComboBox cbLiebingsfach = new JComboBox(Hilfsklasse.faecher); //Eingabe des Lieblingsfaches
    private JLabel lAugenfarbe = new JLabel("Augenfarbe:");
    private JComboBox cbAugenfarbe = new JComboBox(Hilfsklasse.augenfarben); //Eingabe der Augenfarbe
    private JLabel lHaarfarbe = new JLabel("Haarfarbe:");
    private JComboBox cbHaarfarbe = new JComboBox(Hilfsklasse.haarfarben); //Eingabe der Haarfarbe
    private JLabel lFigur = new JLabel("Haarfarbe:");
    private JComboBox cbFigur = new JComboBox(Hilfsklasse.figuren); //Eingabe der Figur
    private JLabel lFehleranzeige = new JLabel();
    private int neue_id;
    private String benutzername;
    private String email;
    private String passwort;
    private String id_nummer;
    // Ende Attribute
    public Registrierung(int arg_neue_id, String arg_benutzername, String arg_email, String arg_passwort, String arg_id_nummer) {
        // Frame-Initialisierung
        super();
        neue_id = arg_neue_id;
        benutzername = arg_benutzername;
        email = arg_email;
        passwort = arg_passwort;
        id_nummer = arg_id_nummer;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 500;
        int frameHeight = 550;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("Registrierung");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Ende Komponenten

        bRegistrieren.setBounds(150, 330, 179, 33);
        bRegistrieren.setText("Fange an zu Daten!");
        bRegistrieren.setMargin(new Insets(2, 2, 2, 2));
        bRegistrieren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bRegistrieren_ActionPerformed(evt);
            }
        });
        cp.add(bRegistrieren);
        cp.add(tfGroesse);
        cp.add(tfGeburtstag);
        lLieblingsfach.setBounds(150, 165, 179, 20);
        cp.add(lLieblingsfach);
        cbLiebingsfach.setBounds(150, 185, 179, 20);
        cp.add(cbLiebingsfach);
        lAugenfarbe.setBounds(150, 205, 179, 20);
        cp.add(lAugenfarbe);
        cbAugenfarbe.setBounds(150, 225, 179, 20);
        cp.add(cbAugenfarbe);
        lHaarfarbe.setBounds(150, 245, 179, 20);
        cp.add(lHaarfarbe);
        cbHaarfarbe.setBounds(150, 265, 179, 20);
        cp.add(cbHaarfarbe);
        lFigur.setBounds(150, 285, 179, 20);
        cp.add(lFigur);
        cbFigur.setBounds(150, 305, 179, 20);
        cp.add(cbFigur);
        lFehleranzeige.setText("");
        lFehleranzeige.setBounds(150, 370, 179, 33);
        lFehleranzeige.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(lFehleranzeige);
        setVisible(true);
    }

    public void bRegistrieren_ActionPerformed(ActionEvent evt) {
        setze_border_zurueck();
        if (!registrierdaten_korrekt()) return;
        /*größe
        geburtstag
        orientierung
        geschlecht
        fach
        augenfarbe
        haarfarbe
        figur*/
        double groesse = Double.parseDouble(tfGroesse.getText())/100;
        String geburtstag = tfGeburtstag.getText();
        String orientierung; //1: Mann, 2: Frau
        if (bgOrientierungsgruppe.getSelection().getMnemonic() == 1) orientierung = "m"; //1: Mann, 2: Frau
        else orientierung = "w";
        String geschlecht;
        if (bgGeschlechtssgruppe.getSelection().getMnemonic() == 1) geschlecht = "m"; //1: Mann, 2: Frau
        else geschlecht = "w";
        String fach = cbLiebingsfach.getSelectedItem().toString();
        String augenfarbe = cbAugenfarbe.getSelectedItem().toString();
        String haarfarbe = cbHaarfarbe.getSelectedItem().toString();
        String figur = cbFigur.getSelectedItem().toString();
        String sql = "INSERT INTO benutzer VALUES (" + neue_id + ", '" + benutzername + "', '" + email + "', '" + passwort + "', ";
        if (id_nummer == "NULL") {
            sql += id_nummer + "";
        } else {
            sql += "'" + id_nummer + "'";
        }
        sql += ", " + groesse + ", '" + geburtstag + "', '" + orientierung + "', '" + geschlecht + "', '" + fach + "', '" + augenfarbe + "', '" + haarfarbe + "', '" + figur + "')";
        Benutzeroberflaeche.myDBManager.datensatzEinfuegen(sql); //Datenbank wird aktualisiert
        //Registrierung abgeschlossen
        dispose();
        Benutzeroberflaeche ui = new Benutzeroberflaeche();
        ui.show(true);
        ui.angemeldet(ui);
    }

    public void setze_border_zurueck() {
        tfGeburtstag.setBorder(null);
        tfGroesse.setBorder(null);
        Hilfsklasse.markiere_label(this.getContentPane(), "Geschlecht:", null);
        Hilfsklasse.markiere_label(this.getContentPane(), "Sex. Orientierung:", null);
        lLieblingsfach.setBorder(null);
    }

    public boolean registrierdaten_korrekt() {
        boolean korrekt = true;
        if (!Hilfsklasse.string_ist_dtformat(tfGeburtstag.getText())) {//falls das Datum falsch eingegeben wurde, oder die Größe keine Zahl ist
            tfGeburtstag.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (!Hilfsklasse.ist_numerisch(tfGroesse.getText())) {
            tfGroesse.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (bgGeschlechtssgruppe.getSelection() == null) {
            Hilfsklasse.markiere_label(this.getContentPane(), "Geschlecht:", Color.RED);
            korrekt = false;
        }
        if (bgOrientierungsgruppe.getSelection() == null) {
            Hilfsklasse.markiere_label(this.getContentPane(), "Sex. Orientierung:", Color.RED);
            korrekt = false;
        }
        //fach
        if (korrekt) return true;
        lFehleranzeige.setText("Überprüfe deine Anmeldedaten!");
        return false;
    }
}