import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrierung extends JFrame {
    // Anfang Attribute
    private JButton bRegistrieren = new JButton(); //Registrierbutton
    private Benutzeroberflaeche ui;
    // Ende Attribute
    public Registrierung(Benutzeroberflaeche bo) {
        // Frame-Initialisierung
        super();
        ui = bo;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 1000;
        int frameHeight = 650;
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

        bRegistrieren.setBounds(154, 306, 179, 33);
        bRegistrieren.setText("Registrieren");
        bRegistrieren.setMargin(new Insets(2, 2, 2, 2));
        bRegistrieren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bRegistrieren_ActionPerformed(evt);
            }
        });
        cp.add(bRegistrieren);

        setVisible(true);
    }

    public void bRegistrieren_ActionPerformed(ActionEvent evt) {
        //Benötigte Dinge:
        //Größe, Geburtstag, orientierung, geschlecht, Lieblingsfach, Augenfarbe, Haarfarbe, Figur

        //Registrierung abgeschlossen
        dispose();
        ui.show(true);
        ui.angemeldet();
    }
}
