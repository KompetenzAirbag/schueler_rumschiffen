import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest; //umbedingt knak fragen
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 16.05.2023
 * @author
 */

public class Anmeldung extends JFrame {
    // Anfang Attribute
    private JButton bAnmelden = new JButton(); //Anmeldebutton
    private JButton bRegistrieren = new JButton(); //Registrierbutton
    private Neues_Textfeld tfEmail = new Neues_Textfeld(95, 134, 302, 20, "E-Mail-Adresse"); //E-Mail-Eingabe
    private Neues_Textfeld tfEmailoderBenutzer = new Neues_Textfeld(95, 134, 302, 20, "E-Mail-Adresse oder Benutzername"); //E-Mail-Eingabe (oder Benutzername) für Anmeldung
    private Neues_Textfeld tfBenutzername = new Neues_Textfeld(95, 83, 302, 20, "Benutzername"); //Benutzername-Eingabe
    private JPasswordField tfPasswort = new JPasswordField(); //Password-Eingabe
    private JLabel lBereits_registriert = new JLabel(); //Ob der Nutzer schon ein Konto hat
    private JLabel lFehlerdisplay = new JLabel(); //Zeigt Fehler wie "Benutzername bereits vergeben!" an
    private JLabel lIstSchueler = new JLabel(); //Ob der Anzumeldende ein Schüler ist (benötigten Daten werden aus Datenbank bezogen)
    boolean bereits_konto = false; //wichtig, um eine "Flip-Flop"-Logik beim Wechsel von Registrieren/Anmelden zu haben
    //Debug/Dev-option
    boolean zeige_fehler_an = true;
    private String id_nummer = "NULL";
    // Ende Attribute

    public Anmeldung() {
        // Frame-Initialisierung
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 512;
        int frameHeight = 400;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("schueler_rumschiffen");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        cp.setFocusCycleRoot(true);
        // Anfang Komponenten

        bAnmelden.setBounds(154, 306, 179, 33);
        bAnmelden.setText("Anmelden");
        bAnmelden.setMargin(new Insets(2, 2, 2, 2));
        bAnmelden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bAnmelden_ActionPerformed(evt);
            }
        });
        bAnmelden.show(false);
        cp.add(bAnmelden);
        bRegistrieren.setBounds(154, 306, 179, 33);
        bRegistrieren.setText("Registrieren");
        bRegistrieren.setMargin(new Insets(2, 2, 2, 2));
        bRegistrieren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bRegistrieren_ActionPerformed(evt);
            }
        });
        cp.add(bRegistrieren);
        cp.add(tfBenutzername);
        cp.add(tfEmail);
        cp.add(tfEmailoderBenutzer);
        tfEmailoderBenutzer.show(false);
        tfPasswort.setBounds(95, 185, 302, 20);
        tfPasswort.setText("Passwort");
        tfPasswort.setForeground(new Color(128,128,128));
        tfPasswort.setFont(new Font("Serif", Font.ITALIC, 14));
        tfPasswort.setEchoChar((char)0);
        tfPasswort.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tfPasswort.setEchoChar('*'); //Aufgrund dieser Tatsache wurde das Passwort nicht in die Klasse Neues_Textfeld ausgelagert.
                if (tfPasswort.getText().equals("Passwort") || tfPasswort.getText().equals("")) {
                    tfPasswort.setText("");
                    tfPasswort.setForeground(new Color(0,0,0));
                    tfPasswort.setFont(new Font("Serif", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfPasswort.getText().equals("Passwort") || tfPasswort.getText().equals("")) {
                    tfPasswort.setEchoChar((char)0);
                    tfPasswort.setText("Passwort");
                    tfPasswort.setForeground(new Color(128,128,128));
                    tfPasswort.setFont(new Font("Serif", Font.ITALIC, 14));
                }
            }
        });
        cp.add(tfPasswort);
        lBereits_registriert.setBounds(95, 220, 302, 20);
        lBereits_registriert.setHorizontalAlignment(SwingConstants.CENTER);
        lBereits_registriert.setText("Bereits ein Konto?");
        lBereits_registriert.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!bereits_konto) { //if-Abfrage um oben benannte "Flip-Flop"-Logik zu erzeugen
                    bereits_konto = true; //der Benutzer hat nach diesem Buttonclick also bereits ein Konto
                    tfBenutzername.show(false); //das Benutzername-Eingabefeld kann nun versteckt werden, da sich der Benutzer sowohl über E-Mail, als auch über den Benutzernamen anmelden kann (gleiches Feld)
                    tfEmail.show(false);
                    tfEmailoderBenutzer.show(true);
                    //tfEmail.setText("E-Mail oder Benutzername");
                    lBereits_registriert.setText("Noch kein Konto?");
                    bRegistrieren.show(false);
                    bAnmelden.show(true);
                }
                else {
                    bereits_konto = false; //der Benutzer hat nach diesem Buttonclick also kein Konto
                    lBereits_registriert.setText("Bereits ein Konto?");
                    tfEmail.show(true);
                    tfEmailoderBenutzer.show(false);
                    tfBenutzername.show(true);
                    bRegistrieren.show(true);
                    bAnmelden.show(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        cp.add(lBereits_registriert);
        lIstSchueler.setBounds(95, 205, 302, 20);
        lIstSchueler.setHorizontalAlignment(SwingConstants.CENTER);
        lIstSchueler.setText("Schüler?");
        lIstSchueler.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouse_ist_schueler(x, y);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        cp.add(lIstSchueler);
        lFehlerdisplay.setBounds(95, 260, 302, 50);
        lFehlerdisplay.setHorizontalAlignment(SwingConstants.CENTER);
        lFehlerdisplay.setText("");
        lFehlerdisplay.show(false);
        cp.add(lFehlerdisplay);
        // Ende Komponenten

        setVisible(true);
    } // end of public Anmeldung

    // Anfang Methoden
    public void mouse_ist_schueler(int x, int y) {
        JFrame istschueler = new JFrame(); //neues Fenster für Eingabe der Schüler-Suchdaten
        //Attribte des JFrames
        istschueler.setTitle("Schüleranmeldung");
        istschueler.setResizable(false);
        istschueler.setLayout(null);
        istschueler.setSize(300, 300);
        istschueler.setLocation(x+100, y+50); //zentriert das Fenster
        istschueler.show(true);

        //Komponente des Frames
        //Vorname
        Neues_Textfeld tfVorname = new Neues_Textfeld(25, 20, 120, 20, "Vorname");
        istschueler.add(tfVorname);
        //Nachname
        Neues_Textfeld tfNachname = new Neues_Textfeld(155, 20, 120, 20, "Nachname");
        istschueler.add(tfNachname);
        //Geburtstag
        Neues_Textfeld tfGeburtstag = new Neues_Textfeld(25, 50, 250, 20, "Geburtstag yyyy-mm-tt");
        istschueler.add(tfGeburtstag);
        //Fehlerdisplay
        JLabel lFehler = new JLabel();
        lFehler.setBounds(30, 110, 240, 20);
        lFehler.setText("");
        istschueler.add(lFehler);
        //Button Schülerdaten finden
        JButton bFinden = new JButton();
        bFinden.setBounds(60, 80, 180, 20);
        bFinden.setText("Daten importieren");
        bFinden.setMargin(new Insets(2, 2, 2, 2));
        bFinden.addActionListener(new ActionListener() { //Button Schüler finden
            @Override
            public void actionPerformed(ActionEvent e) {
                //hier wird nun der Schüler in der Datenbank gesucht
                String vorname = tfVorname.getText();
                String nachname = tfNachname.getText();
                String geburtstag = tfGeburtstag.getText();
                if (!Hilfsklasse.string_ist_dtformat(geburtstag) || vorname == "Vorname" || vorname == "" || nachname == "" || nachname == "Nachname") {
                    lFehler.setText("Anmeldedaten ungültig!");
                    lFehler.show(true);
                    return; //falls der Schüler keinen Namen oder ein falsches Geburtsdatum angegeben hat, wird dies hier ausgegeben
                }
                String sql = "SELECT * FROM schueler WHERE Vorname='" + vorname + "' AND Name='" + nachname + "' AND Geburtstag='" + geburtstag + "'";
                String[][] antwort = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql); //hier wird nach dem Schüler gesucht
                if (antwort.length != 2) {
                    lFehler.setText("Schüler nicht gefunden! Bitte gib deine Anmeldedaten manuell ein.");
                    lFehler.show(true);
                    return; //falls der Schüler nicht (eindeutig) gefunden werden konnte, wird ein Fehler ausgegeben
                }
                //hier ist der Schüler erfolgreich gefunden worden
                lFehler.setText("Deine Daten werden übernommen!");
                lFehler.show(true);
                tfBenutzername.setText(vorname + " " + nachname);
                tfBenutzername.setForeground(new Color(0,0,0));
                tfBenutzername.setFont(new Font("Serif", Font.PLAIN, 14));
                id_nummer = antwort[1][0];
                istschueler.dispose();
            }
        });
        istschueler.add(bFinden);
    }

    public void bAnmelden_ActionPerformed(ActionEvent evt) {
        String email = tfEmailoderBenutzer.getText(); //E-Mail oder Benutzername wird abgefragt
        String passwort = verschluessel_passwort(tfPasswort.getText()); //Passwort wird abgefragt

        //nur, wenn der Benutzer alle Felder ausgeführt hat, geht es weiter
        if (email.equals("E-Mail oder Benutzername") || email.equals("") || passwort.equals("Passwort") || passwort.equals("")) {
            zeige_fehler("Bitte fülle alle Felder aus!");
            return; //Methode wird beendet
        }
        if (existiert_benutzer(email, email)) {
            String sql = "SELECT ID, Passwort FROM benutzer WHERE Benutzername = '" + email + "' OR EMail='" + email + "'";
            String[][] ergebnis = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql); //die ID und das Passwort für diesen Benutzer wird abgefragt
            String db_ID = ergebnis[1][0];
            String db_passwort = ergebnis[1][1];
            if (passwort.equals(db_passwort)) {
                //Ab dieser Stelle war die Anmeldung erfolgreich
                zeige_fehler("Anmeldung erfolgreich!");
                Benutzeroberflaeche ui = new Benutzeroberflaeche();
                dispose();
                ui.angemeldet(ui, db_ID);
            } else {
                zeige_fehler("Anmeldedaten falsch!");
            }
        } else { //falls der Benutzer nicht existiert gibt es einen Fehler
            zeige_fehler("<html>Bitte registriere dich erst oder<br/>kontrolliere deine Anmeldedaten!</html>");
        }
    } // end of bAnmelden_ActionPerformed

    public void bRegistrieren_ActionPerformed(ActionEvent evt) {
        String benutzername = tfBenutzername.getText(); //Benutzername wird abgefragt
        String email = tfEmail.getText(); //E-Mail wird abgefragt
        String passwort = verschluessel_passwort(tfPasswort.getText()); //Passwort wird abgefragt

        //nur, wenn der Benutzer alle Felder ausgeführt hat, geht es weiter
        if (benutzername.equals("Benutzername") || benutzername.equals("") || email.equals("E-Mail") || email.equals("") || passwort.equals("Passwort") || passwort.equals("")) {
            zeige_fehler("Bitte fülle alle Felder aus!");
            return; //Methode wird beendet
        }
        if (!existiert_benutzer(benutzername, email)) {
            int neue_id = Hilfsklasse.generiere_neue_id("benutzer"); //falls der Benutzer noch nicht existiert, werden seine Anmeldedaten in der Datenbank gespeichert
            new Registrierung(neue_id, benutzername, email, passwort, id_nummer);
            dispose();
        } else {
            zeige_fehler("<html>Benutzername oder E-Mail-Adresse bereits vergeben!<br/>Bitte versuche einen Anderen oder melde dich an!</html>");
        }
    } // end of bAnmelden_ActionPerformed

    public boolean existiert_benutzer(String benutzername, String email) {
        //ab hier wird geguckt, ob der Benutzername, oder die E-Mail-Adresse bereits im System ist
        String sql = "SELECT * FROM benutzer WHERE Benutzername='" + benutzername + "' OR EMail = '" + email + "'";
        String[][] ergebnis = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql);
        if (ergebnis.length == 1) return false; //falls der Benutzer neu ist
        return true;
    }

    public String verschluessel_passwort(String passwort) {
        //hier wird das passwort mit MD5 verschlüsselt, hierbei handelt es sich weder um eine sichere Verschlüsselung, noch ist es überhaupt eine "richtige" Verschlüsselung (es "scheint" nur so)
        String encryptedpassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); //Hashfunktion MD5 wird geladen
            md.update(passwort.getBytes()); //MD5 gibt Bytes zurück
            byte[] bytes = md.digest(); //Bytes werden ausgelesen
            for(int i=0; i< bytes.length ;i++)
            {
                encryptedpassword += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1); //diese Bytes müssen hier zu lesbaren Buchstaben umgewandelt werden
            }
        } catch (Exception e) {
            if (zeige_fehler_an) System.out.println(e + ""); //gegebenenfalls werden Fehler angezeigt
        }
        return encryptedpassword;
    }

    public void zeige_fehler(String fehler) {
        lFehlerdisplay.show(true);
        lFehlerdisplay.setText(fehler);
    }
    // Ende Methoden
} // end of class Anmeldung
