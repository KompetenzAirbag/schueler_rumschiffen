import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Neues_Textfeld extends JTextField {
    //hier wird ein Textfeld nach dem Schema erstellt, wobei es immer einen Beispieltext gibt (leer), welcher bei draufklicken verschwindet
    public Neues_Textfeld(int x, int y, int breite, int hoehe, String leer) {
        super();
        setBounds(x, y, breite, hoehe);
        setText(leer);
        setForeground(new Color(128,128,128));
        setFont(new Font("Serif", Font.ITALIC, 14));
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { //falls das Textfeld im Fokus ist und das Textfeld den blanken Text anzeigt, wird dieser gelöscht
                if (getText().equals(leer)) {
                    setText("");
                    setForeground(new Color(0,0,0));
                    setFont(new Font("Serif", Font.PLAIN, 14));
                }
            }
            @Override
            public void focusLost(FocusEvent e) { //falls das Textfeld nicht mehr im Fokus ist, wird der blanke Text wieder eingefügt; ein Löschen eingegebener Daten wird verhindert durch Überprüfung, ob das Feld leer ist.
                if (getText().equals("")) {
                    setText(leer);
                    setForeground(new Color(128,128,128));
                    setFont(new Font("Serif", Font.ITALIC, 14));
                }
            }
        });
    }
}