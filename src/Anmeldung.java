import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.security.MessageDigest; //umbedingt knak fragen

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 16.05.2023
 * @author
 */

public class Anmeldung extends JFrame {
    // Anfang Attribute
    private DBManagerSQLite myDBManager; //Datenbank wird geladen
    private static Benutzeroberflaeche ui;
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
    boolean zeige_warnungen_an = false;
    boolean zeige_fehler_an = true;
    String id_nummer = "NULL";
    // Ende Attribute

    public Anmeldung(DBManagerSQLite Datenbank) {
        // Frame-Initialisierung
        super();
        myDBManager = Datenbank;
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
                tfPasswort.setEchoChar('*');
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
                Neues_Textfeld tfGeburtstag = new Neues_Textfeld(20, 50, 240, 20, "Geburtstag yyyy-mm-tt");
                istschueler.add(tfGeburtstag);
                //Fehlerdisplay
                JLabel lFehler = new JLabel();
                lFehler.setBounds(30, 110, 240, 20);
                lFehler.setText("");
                //lFehler.show(false);
                istschueler.add(lFehler);
                //Button Schülerdaten finden
                JButton bFinden = new JButton();
                bFinden.setBounds(60, 80, 180, 20);
                bFinden.setText("Daten importieren");
                bFinden.setMargin(new Insets(2, 2, 2, 2));
                bFinden.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //hier wird nun der Schüler gesucht
                        String vorname = tfVorname.getText();
                        String nachname = tfNachname.getText();
                        String geburtstag = tfGeburtstag.getText();
                        if (!string_ist_dtformat(geburtstag) || vorname == "Vorname" || vorname == "" || nachname == "" || nachname == "Nachname") {
                            lFehler.setText("Anmeldedaten ungültig!");
                            lFehler.show(true);
                            return; //falls der Schüler keinen Namen oder ein falsches Geburtsdatum angegeben hat, wird dies hier ausgegeben
                        }
                        String sql = "SELECT * FROM schueler WHERE Vorname='" + vorname + "' AND Name='" + nachname + "' AND Geburtstag='" + geburtstag + "'";
                        String[][] antwort = myDBManager.sqlAnfrageAusfuehren(sql); //hier wird nach dem Schüler gesucht
                        if (antwort.length != 2) {
                            lFehler.setText("Schüler nicht gefunden! Bitte gib deine Anmeldedaten manuell ein.");
                            lFehler.show(true);
                            return; //falls der Schüler nicht (eindeutig) gefunden werden konnte, wird ein Fehler ausgegeben
                        }
                        //hier ist der Schüler erfolgreich gefunden worden
                        //TODO bla bla daten übernehmen
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

    public void bAnmelden_ActionPerformed(ActionEvent evt) {
        String email = tfEmailoderBenutzer.getText(); //E-Mail oder Benutzername wird abgefragt
        String passwort = verschluessel_passwort(tfPasswort.getText()); //Passwort wird abgefragt

        //nur, wenn der Benutzer alle Felder ausgeführt hat, geht es weiter
        if (email.equals("E-Mail oder Benutzername") || email.equals("") || passwort.equals("Passwort") || passwort.equals("")) {
            zeige_fehler("Bitte fülle alle Felder aus!");
            return; //Methode wird beendet
        }
        if (existiert_benutzer(email, email)) {
            String sql = "SELECT Passwort FROM benutzer WHERE Benutzername = '" + email + "' OR EMail='" + email + "'";
            String db_passwort = myDBManager.sqlAnfrageAusfuehren(sql)[1][0]; //das Passwort für diesen Benutzer wird abgefragt
            if (passwort.equals(db_passwort)) {
                //Ab dieser Stelle war die Anmeldung erfolgreich
                zeige_fehler("Anmeldung erfolgreich!");
                ui = new Benutzeroberflaeche();
                dispose();
                ui.angemeldet();
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
            int neue_id = generiere_neue_id("benutzer"); //falls der Benutzer noch nicht existiert, werden seine Anmeldedaten in der Datenbank gespeichert
            String sql = "INSERT INTO benutzer VALUES (" + neue_id + ", '" + benutzername + "', '" + email + "', '" + passwort + "', ";
            if (id_nummer == "NULL") {
                sql += id_nummer + ")";
            } else {
                sql += "'" + id_nummer + "')";
            }
            myDBManager.datensatzEinfuegen(sql); //Datenbank wird aktualisiert
            //Ab dieser Stelle war die Registrierung erfolgreich
            zeige_fehler("Registrierung erfolgreich!");
            ui = new Benutzeroberflaeche();
            ui.show(false);
            Registrierung reg = new Registrierung(ui);
            dispose();
        } else {
            zeige_fehler("<html>Benutzername oder E-Mail-Adresse bereits vergeben!<br/>Bitte versuche einen Anderen oder melde dich an!</html>");
        }
    } // end of bAnmelden_ActionPerformed

    public boolean existiert_benutzer(String benutzername, String email) {
        //ab hier wird geguckt, ob der Benutzername, oder die E-Mail-Adresse bereits im System ist
        String sql = "SELECT * FROM benutzer WHERE Benutzername='" + benutzername + "' OR EMail = '" + email + "'";
        String[][] ergebnis = myDBManager.sqlAnfrageAusfuehren(sql);
        if (ergebnis.length == 1) return false; //falls der Benutzer neu ist
        return true;
    }

    public String verschluessel_passwort(String passwort) {
        //hier wird das passwort mit MD5 verschlüsselt, hierbei handelt es sich weder um eine sichere Verschlüsselung, noch ist es überhaupt eine "richtige" Verschlüsselung (es "scheint" nur so)
        String encryptedpassword = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(passwort.getBytes()); //MD5 gibt Bytes zurück
            byte[] bytes = m.digest(); //Bytes werden ausgelesen
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

    public int generiere_neue_id(String tabelle) {
        //generiert laufende Nummer für ID (wenn es eine vorhandene ID gibt, wird die höchste ID+1 ausgegeben)
        //falls die ID kein Integer ist, wird eine eigene ID erzeugt, welche der Anzahl der Datensätze entspricht
        int max_id = 0;

        //hier werden Tabellennamen und Anzahl der Datensätze der gesuchten Tabelle abgefragt
        String sql = "SELECT * FROM " + tabelle;
        String[][] ergebnis = myDBManager.sqlAnfrageAusfuehren(sql);
        String[] tabellennamen = ergebnis[0];
        int laenge = ergebnis.length-1; //Länge der Tabelle wird später genutzt, falls die ID kein Int ist.

        //hier werden die Tabellennamen durchgegangen und geguckt, ob sie das Stichwort "ID" enthalten, da einige Tabellen nicht nur "ID" als Tabellennamen haben, sondern vielmehr eine Kombination
        for (int i=0; i<tabellennamen.length; i++) {
            if (tabellennamen[i].contains("ID") || tabellennamen[i].contains("id") || tabellennamen[i].contains("Id")) {
                sql = "SELECT MAX(" + tabellennamen[i] + ") FROM " + tabelle;
                ergebnis = myDBManager.sqlAnfrageAusfuehren(sql);
                if (ergebnis.length > 1) {
                    try { //try-catch wird benutzt, um zu gucken, ob es sich wirklich um einen Int handelt (Integer.parseInt wirft eine Fehlermeldung anderenfalls)
                        max_id = Integer.parseInt(ergebnis[1][0]);
                    }
                    catch (Exception e) {
                        if (zeige_warnungen_an) System.out.println("Warning: " + e);
                        max_id = laenge; //falls es keinen Int als ID gibt, wird provisorisch die Länge der Tabelle genommen
                    }
                }
                else {
                    max_id = -1; //falls die Datenbank leer ist, wird die erste ID 0 sein. Da am Ende +1 gerechnet wird, muss hier die ID -1 sein
                }
                break;
            }
        }
        return max_id+1;
    };

    public static boolean string_ist_dtformat(String datetime) {
        if (datetime.length() != 10) return false; //falls das Datum länger als 10 Buchstaben ist, muss etwas falsch sein
        for (int i = 0; i < datetime.length(); i++) {
            if ((i<4 || i==5 || i==6 || i>7) && !ist_numerisch(datetime.charAt(i) + "")) return false; //falls an Stelle 1-4, 6, 7, 9, 10 keine Zahl ist, ist das Datum ungültig
            if ((i==4 || i==7) && datetime.charAt(i) != '-') return false; //falls an Stelle 5, 8 kein Bindestrich ist, ist das Datum ungültig
        }
        return true; //falls keine der obigen Bedingungen zutraf, ist das Datum korrekt eingegeben
    };

    public static boolean ist_numerisch(String c) {
        //Guckt nach, ob der Charakter in eine Zahl umgewandelt werden kann
        try { //Integer.parseInt wirft eine Fehlermeldung, falls c kein Int ist, diese wird hiermit abgefangen
            Integer.parseInt(c);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    };

    // Ende Methoden
} // end of class Anmeldung
