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
            public void focusGained(FocusEvent e) {
                if (getText().equals(leer)) {
                    setText("");
                    setForeground(new Color(0,0,0));
                    setFont(new Font("Serif", Font.PLAIN, 14));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(leer);
                    setForeground(new Color(128,128,128));
                    setFont(new Font("Serif", Font.ITALIC, 14));
                }
            }
        });
    }
}