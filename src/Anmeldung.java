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
    private JTextField tfEmail = new JTextField(); //E-Mail-Eingabe
    private JTextField tfBenutzername = new JTextField(); //Benutzername-Eingabe
    private JPasswordField tfPasswort = new JPasswordField(); //Password-Eingabe
    private JLabel lBereits_registriert = new JLabel(); //Ob der Nutzer schon ein Konto hat
    private JLabel lFehlerdisplay = new JLabel(); //Zeigt Fehler wie "Benutzername bereits vergeben!" an
    private JLabel lIstSchueler = new JLabel(); //Ob der Anzumeldende ein Schüler ist (benötigten Daten werden aus Datenbank bezogen)
    boolean bereits_konto = false; //wichtig, um eine "Flip-Flop"-Logik beim Wechsel von Registrieren/Anmelden zu haben
    //Debug/Dev-option
    boolean zeige_warnungen_an = false;
    boolean zeige_fehler_an = true;
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
        tfBenutzername.setBounds(95, 83, 302, 20);
        tfBenutzername.setText("Benutzername");
        tfBenutzername.setForeground(new Color(128,128,128));
        tfBenutzername.setFont(new Font("Serif", Font.ITALIC, 14));
        tfBenutzername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfBenutzername.getText().equals("Benutzername")) {
                    tfBenutzername.setText("");
                    tfBenutzername.setForeground(new Color(0,0,0));
                    tfBenutzername.setFont(new Font("Serif", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfBenutzername.getText().equals("")) {
                    tfBenutzername.setText("Benutzername");
                    tfBenutzername.setForeground(new Color(128,128,128));
                    tfBenutzername.setFont(new Font("Serif", Font.ITALIC, 14));
                }
            }
        });
        cp.add(tfBenutzername);
        tfEmail.setBounds(95, 134, 302, 20);
        tfEmail.setText("E-Mail");
        tfEmail.setForeground(new Color(128,128,128));
        tfEmail.setFont(new Font("Serif", Font.ITALIC, 14));
        tfEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfEmail.getText().equals("E-Mail oder Benutzername") || tfEmail.getText().equals("E-Mail")) {
                    tfEmail.setText("");
                    tfEmail.setForeground(new Color(0,0,0));
                    tfEmail.setFont(new Font("Serif", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfEmail.getText().equals("")) {
                    if (bereits_konto) {
                        tfEmail.setText("E-Mail oder Benutzername");
                        tfEmail.setForeground(new Color(128,128,128));
                        tfEmail.setFont(new Font("Serif", Font.ITALIC, 14));
                    }
                    else {
                        tfEmail.setText("E-Mail");
                        tfEmail.setForeground(new Color(128,128,128));
                        tfEmail.setFont(new Font("Serif", Font.ITALIC, 14));
                    }
                }
            }
        });
        cp.add(tfEmail);
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
                    tfEmail.setText("E-Mail oder Benutzername");
                    lBereits_registriert.setText("Noch kein Konto?");
                    bRegistrieren.show(false);
                    bAnmelden.show(true);
                }
                else {
                    bereits_konto = false; //der Benutzer hat nach diesem Buttonclick also kein Konto
                    lBereits_registriert.setText("Bereits ein Konto?");
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
                JTextField tfVorname = new JTextField();
                tfVorname.setBounds(25, 20, 120, 20);
                tfVorname.setHorizontalAlignment(SwingConstants.CENTER);
                tfVorname.setText("Vorname");
                tfVorname.setForeground(new Color(128,128,128));
                tfVorname.setFont(new Font("Serif", Font.ITALIC, 14));
                tfVorname.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (tfVorname.getText().equals("Vorname")) {
                            tfVorname.setText("");
                            tfVorname.setForeground(new Color(0,0,0));
                            tfVorname.setFont(new Font("Serif", Font.PLAIN, 14));
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (tfVorname.getText().equals("")) {
                            tfVorname.setText("Vorname");
                            tfVorname.setForeground(new Color(128,128,128));
                            tfVorname.setFont(new Font("Serif", Font.ITALIC, 14));
                        }
                    }
                });
                istschueler.add(tfVorname);
                //Nachname
                JTextField tfNachname = new JTextField();
                tfNachname.setBounds(155, 20, 120, 20);
                tfNachname.setHorizontalAlignment(SwingConstants.CENTER);
                tfNachname.setText("Nachname");
                tfNachname.setForeground(new Color(128,128,128));
                tfNachname.setFont(new Font("Serif", Font.ITALIC, 14));
                tfNachname.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (tfNachname.getText().equals("Nachname")) {
                            tfNachname.setText("");
                            tfNachname.setForeground(new Color(0,0,0));
                            tfNachname.setFont(new Font("Serif", Font.PLAIN, 14));
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (tfNachname.getText().equals("")) {
                            tfNachname.setText("Nachname");
                            tfNachname.setForeground(new Color(128,128,128));
                            tfNachname.setFont(new Font("Serif", Font.ITALIC, 14));
                        }
                    }
                });
                istschueler.add(tfNachname);
                //Geburtstag
                JTextField tfGeburtstag = new JTextField();
                tfGeburtstag.setBounds(20, 50, 240, 20);
                tfGeburtstag.setHorizontalAlignment(SwingConstants.CENTER);
                tfGeburtstag.setText("Geburtstag yyyy-mm-tt");
                tfGeburtstag.setForeground(new Color(128,128,128));
                tfGeburtstag.setFont(new Font("Serif", Font.ITALIC, 14));
                tfGeburtstag.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (tfGeburtstag.getText().equals("Geburtstag yyyy-mm-tt")) {
                            tfGeburtstag.setText("");
                            tfGeburtstag.setForeground(new Color(0,0,0));
                            tfGeburtstag.setFont(new Font("Serif", Font.PLAIN, 14));
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (tfGeburtstag.getText().equals("")) {
                            tfGeburtstag.setText("Geburtstag yyyy-mm-tt");
                            tfGeburtstag.setForeground(new Color(128,128,128));
                            tfGeburtstag.setFont(new Font("Serif", Font.ITALIC, 14));
                        }
                    }
                });
                istschueler.add(tfGeburtstag);
                //Fehlerdisplay
                JLabel lFehler = new JLabel();
                lFehler.setBounds(30, 110, 240, 20);
                lFehler.setText("höhö ich bin ein fehler");
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
                        System.out.println("Suche gestartet!");
                        //hier wird nun der Schüler gesucht
                        String vorname = tfVorname.getText();
                        String nachname = tfNachname.getText();
                        String geburtstag = tfGeburtstag.getText();
                        System.out.println(vorname + " " + nachname + " " + geburtstag + " " + string_ist_dtformat(geburtstag));
                        if (!string_ist_dtformat(geburtstag) || vorname == "Vorname" || vorname == "" || nachname == "" || nachname == "Nachname") {
                            lFehler.setText("Anmeldedaten ungültig!");
                            lFehler.show(true);
                            return; //falls der Schüler keinen Namen oder ein falsches Geburtsdatum angegeben hat, wird dies hier ausgegeben
                        }
                        String sql = "SELECT * FROM schueler WHERE Vorname='" + vorname + "' AND Name='" + nachname + "' AND Geburtstag='" + geburtstag + "'";
                        String[][] antwort = myDBManager.sqlAnfrageAusfuehren(sql); //hier wird nach dem Schüler gesucht
                        System.out.println(antwort.length + "");
                        if (antwort.length != 2) {
                            lFehler.setText("Schüler nicht gefunden! Bitte gib deine Anmeldedaten manuell ein.");
                            lFehler.show(true);
                            return; //falls der Schüler nicht (eindeutig) gefunden werden konnte, wird ein Fehler ausgegeben
                        }
                        //hier ist der Schüler erfolgreich gefunden worden
                        //TODO bla bla daten übernehmen
                        lFehler.setText("<html>Deine Daten werden übernommen!</br>Dein Benutzername ist " + vorname + " " + nachname);
                        lFehler.show(true);
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
        String email = tfEmail.getText(); //E-Mail oder Benutzername wird abgefragt
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
            int neue_id = generiere_neue_id("benutzer");
            String sql = "INSERT INTO benutzer VALUES (" + neue_id + ", '" + benutzername + "', '" + email + "', '" + passwort + "')"; //Datenbank wird aktualisiert
            myDBManager.datensatzEinfuegen(sql);
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
        if (ergebnis.length == 1) {//falls der Benutzer neu ist
            return false;
        }
        return true;
    }

    public String verschluessel_passwort(String passwort) {
        //hier wird das passwort mit MD5 verschlüsselt
        String encryptedpassword = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(passwort.getBytes()); //MD5 gibt Bytes zurück
            byte[] bytes = m.digest();
            for(int i=0; i< bytes.length ;i++)
            {
                encryptedpassword += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1); //diese Bytes müssen hier zu lesbaren Buchstaben umgewandelt werden
            }
        } catch (Exception e) {
            if (zeige_fehler_an) System.out.println(e + "");
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
        int max_id = 0; //neue ID wird festgelegt

        //hier werden Tabellennamen und Anzahl der Datensätze der gesuchten Tabelle abgefragt
        String sql = "SELECT * FROM " + tabelle;
        String[][] ergebnis = myDBManager.sqlAnfrageAusfuehren(sql);
        String[] tabellennamen = ergebnis[0];
        int laenge = ergebnis.length-1;

        //hier werden die Tabellennamen durchgegangen und geguckt, ob sie das Stichwort "ID" enthalten, da einige Tabellen nicht nur "ID" als Tabellennamen haben, sondern vielmehr eine Kombination
        for (int i=0; i<tabellennamen.length; i++) {
            if (tabellennamen[i].contains("ID") || tabellennamen[i].contains("id") || tabellennamen[i].contains("Id")) {
                sql = "SELECT MAX(" + tabellennamen[i] + ") FROM " + tabelle;
                ergebnis = myDBManager.sqlAnfrageAusfuehren(sql);
                if (ergebnis.length > 1) {
                    try {
                        max_id = Integer.parseInt(ergebnis[1][0]);
                    }
                    catch (Exception e) { //hiermit wird überprüft, ob es sich bei der ID um einen Integer handelt, oder nicht
                        if (zeige_warnungen_an) System.out.println("Warning: " + e);
                        max_id = laenge;
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

    public boolean string_ist_dtformat(String datetime) {
        if (datetime.length() != 10) return false; //falls das Datum länger als 10 Buchstaben ist, muss etwas falsch sein
        for (int i = 0; i < datetime.length(); i++) {
            if ((i<4 || i==5 || i==6 || i>7) && !ist_numerisch(datetime.charAt(i) + "")) return false; //falls an Stelle 1-4, 6, 7, 9, 10 keine Zahl ist, ist das Datum ungültig
            if ((i==4 || i==7) && datetime.charAt(i) != '-') return false; //falls an Stelle 5, 8 kein Bindestrich ist, ist das Datum ungültig
        }
        return true; //falls keine der obigen Bedingungen zutraf, ist das Datum korrekt eingegeben
    };

    public boolean ist_numerisch(String c) {
        //Guckt nach, ob der Charakter in eine Zahl umgewandelt werden kann
        try {
            Integer.parseInt(c);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    };

    // Ende Methoden
} // end of class schueler_rumschiffen
