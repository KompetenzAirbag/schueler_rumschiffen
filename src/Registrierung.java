import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Registrierung extends JFrame {
    // Anfang Attribute
    private JButton bRegistrieren = new JButton(); //Registrierbutton
    private Neues_Textfeld tfGroesse = new Neues_Textfeld(150, 10, 179, 33, "Größe"); //Eingabe der Größe
    private Neues_Textfeld tfGeburtstag = new Neues_Textfeld(150, 50, 179, 33, "Geburtstag"); //Eingabe des Geburtstages
    private Neues_Textfeld tfOrientierung = new Neues_Textfeld(150, 90, 179, 33, "Sex. Orientierung"); //Eingabe der orientierung
    private Neues_Textfeld tfGeschlecht = new Neues_Textfeld(150, 130, 179, 33, "Geschlecht"); //Eingabe des Geschlechtes
    private Neues_Textfeld tfLiebingsfach = new Neues_Textfeld(150, 170, 179, 33, "Lieblingsfach"); //Eingabe des Lieblingsfaches
    private Neues_Textfeld tfAugenfarbe = new Neues_Textfeld(150, 210, 179, 33, "Augenfarbe"); //Eingabe der Augenfarbe
    private Neues_Textfeld tfHaarfarbe = new Neues_Textfeld(150, 250, 179, 33, "Haarfarbe"); //Eingabe der Haarfarbe
    private Neues_Textfeld tfFigur = new Neues_Textfeld(150, 290, 179, 33, "Figur"); //Eingabe der Figur
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
        bRegistrieren.setText("Daten übermitteln");
        bRegistrieren.setMargin(new Insets(2, 2, 2, 2));
        bRegistrieren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bRegistrieren_ActionPerformed(evt);
            }
        });
        cp.add(bRegistrieren);
        cp.add(tfGroesse);
        cp.add(tfGeburtstag);
        cp.add(tfOrientierung);
        cp.add(tfGeschlecht);
        cp.add(tfLiebingsfach);
        cp.add(tfAugenfarbe);
        cp.add(tfHaarfarbe);
        cp.add(tfFigur);
        setVisible(true);
    }

    public void bRegistrieren_ActionPerformed(ActionEvent evt) {

        //Registrierung abgeschlossen
        dispose();
        ui.show(true);
        ui.angemeldet();
    }
}