import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Optionsfeld extends ButtonGroup {
    //Klasse wodurch sich eine Buttongroup erstellen lässt, die n Auswahlmöglichkeiten anzeigt
    public Optionsfeld(Container cp, String name, List<String> optionen, int x, int y) {
        super();
        JLabel l = new JLabel(name);
        l.setName(name);
        l.setBounds(x, y, 90*optionen.size(), 20);
        cp.add(l);
        for (int i = 0; i < optionen.size(); i++) { //hier wird jede Auswahlmöglichkeit durchiteriert und dementsprechend viele RadioButtons erzeugt und der ButtonGroup hinzugefügt
            JRadioButton rb = new JRadioButton(optionen.get(i));
            rb.setBounds(x+i*80, y+20, 80, 20);
            rb.setMnemonic(i);
            add(rb);
            cp.add(rb);
        }
    }
}
