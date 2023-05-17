import java.awt.*;
import javax.swing.*;
public class Benutzeroberflaeche extends JFrame {
    // Anfang Attribute
    private static DBManagerSQLite myDBManager = new DBManagerSQLite("schule_gag"); //Datenbank wird geladen
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

        setVisible(true);
    }

    public static void main(String[] args) {
        new Anmeldung(myDBManager);
    } // end of main

    public void angemeldet() {
        System.out.println("nice!");
    }
}
