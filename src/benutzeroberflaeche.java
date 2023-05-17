import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class benutzeroberflaeche extends JFrame {
    // Anfang Attribute
    private DBManagerSQLite myDBManager = new DBManagerSQLite("schule_gag"); //Datenbank wird geladen

    boolean angemeldet = false;
    // Ende Attribute
    public benutzeroberflaeche() {
        // Frame-Initialisierung
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 512;
        int frameHeight = 800;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("schueler_rumschiffen");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
    }

    public static void main(String[] args) {
        new benutzeroberflaeche();
    } // end of main

    anmeldung a = new anmeldung();

}
