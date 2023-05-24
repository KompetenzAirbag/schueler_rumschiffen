import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class Registrierung extends JFrame {
    // Anfang Attribute
    private JButton bRegistrieren = new JButton(); //Registrierbutton
    private Neues_Textfeld tfGroesse = new Neues_Textfeld(150, 10, 179, 33, "Größe (in cm)"); //Eingabe der Größe
    private Neues_Textfeld tfGeburtstag = new Neues_Textfeld(150, 50, 179, 33, "Geburtstag (yyyy-mm-dd)"); //Eingabe des Geburtstages
    ButtonGroup orientierungsgruppe = new ButtonGroup();
    JLabel orientierung = new JLabel("Sex. Orientierung:");
    private JRadioButton orientierung_1 = new JRadioButton("Mann"); //Eingabe der Orientierung
    private JRadioButton orientierung_2 = new JRadioButton("Frau"); //Eingabe der Orientierung
    private ButtonGroup geschlechtssgruppe = new ButtonGroup();
    JLabel geschlecht = new JLabel("Geschlecht:");
    private JRadioButton geschlecht_1 = new JRadioButton("Mann"); //Eingabe des Geschlechtes
    private JRadioButton geschlecht_2 = new JRadioButton("Frau"); //Eingabe des Geschlechtes
    private Neues_Textfeld tfLiebingsfach = new Neues_Textfeld(150, 170, 179, 33, "Lieblingsfach"); //Eingabe des Lieblingsfaches
    private Neues_Textfeld tfAugenfarbe = new Neues_Textfeld(150, 210, 179, 33, "Augenfarbe"); //Eingabe der Augenfarbe
    private Neues_Textfeld tfHaarfarbe = new Neues_Textfeld(150, 250, 179, 33, "Haarfarbe"); //Eingabe der Haarfarbe
    private Neues_Textfeld tfFigur = new Neues_Textfeld(150, 290, 179, 33, "Figur"); //Eingabe der Figur
    private JLabel lFehleranzeige = new JLabel();
    private Benutzeroberflaeche ui;
    // Ende Attribute
    public Registrierung(Benutzeroberflaeche bo) {
        // Frame-Initialisierung
        super();
        ui = bo;
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
        orientierung.setBounds(150, 83, 179, 20);
        cp.add(orientierung);
        orientierung_1.setBounds(150, 104, 89, 25);
        orientierung_2.setBounds(239, 104, 89, 25);
        orientierungsgruppe.add(orientierung_1);
        orientierungsgruppe.add(orientierung_2);
        cp.add(orientierung_1);
        cp.add(orientierung_2);
        geschlecht.setBounds(150, 130, 179, 20);
        cp.add(geschlecht);
        geschlecht_1.setBounds(150, 145, 89, 25);
        geschlecht_2.setBounds(239, 145, 89, 25);
        geschlechtssgruppe.add(geschlecht_1);
        geschlechtssgruppe.add(geschlecht_2);
        cp.add(geschlecht_1);
        cp.add(geschlecht_2);
        cp.add(tfLiebingsfach);
        cp.add(tfAugenfarbe);
        cp.add(tfHaarfarbe);
        cp.add(tfFigur);
        lFehleranzeige.setText("");
        lFehleranzeige.setBounds(150, 370, 179, 33);
        lFehleranzeige.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(lFehleranzeige);
        setVisible(true);
    }

    public void bRegistrieren_ActionPerformed(ActionEvent evt) {
        setze_border_zurueck();
        String geburtstag = tfGeburtstag.getText();
        String groesse = tfGroesse.getText();
        if (!anmeldedaten_korrekt()) return;
        //Registrierung abgeschlossen
        dispose();
        ui.show(true);
        ui.angemeldet();
    }

    public void setze_border_zurueck() {
        tfGeburtstag.setBorder(null);
        tfGroesse.setBorder(null);
        geschlecht.setBorder(null);
        orientierung.setBorder(null);
        tfLiebingsfach.setBorder(null);
    }

    public boolean anmeldedaten_korrekt() {
        /*
        cp.add(tfLiebingsfach);
        cp.add(tfAugenfarbe);
        cp.add(tfHaarfarbe);
        cp.add(tfFigur);*/
        boolean korrekt = true;
        if (!Anmeldung.string_ist_dtformat(tfGeburtstag.getText())) {//falls das Datum falsch eingegeben wurde, oder die Größe keine Zahl ist
            tfGeburtstag.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (!Anmeldung.ist_numerisch(tfGroesse.getText())) {
            tfGroesse.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (geschlechtssgruppe.getSelection() == null) {
            geschlecht.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (orientierungsgruppe.getSelection() == null) {
            orientierung.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (tfLiebingsfach.getText() == "" || tfLiebingsfach.getText() == "Lieblingsfach") {
            tfLiebingsfach.setBorder(new LineBorder(Color.RED));
            korrekt = false;
        }
        if (korrekt) return true;
        lFehleranzeige.setText("Überprüfe deine Anmeldedaten!");
        return false;
    }
}