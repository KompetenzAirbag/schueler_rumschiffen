import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Optionsfeld extends ButtonGroup {
    public Optionsfeld(Container cp, String name, List<String> optionen, int x, int y) {
        super();
        JLabel l = new JLabel(name);
        l.setName(name);
        l.setBounds(x, y, 90*optionen.size(), 20);
        cp.add(l);
        for (int i = 0; i < optionen.size(); i++) {
            JRadioButton rb = new JRadioButton(optionen.get(i));
            rb.setBounds(x+i*80, y+20, 80, 20);//optionen.get(i).length()*2
            rb.setMnemonic(i);
            add(rb);
            cp.add(rb);
        }
    }
}
