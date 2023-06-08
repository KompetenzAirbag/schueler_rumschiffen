import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; //knak frage
import java.util.ArrayList; //knak fragen
import java.util.Arrays;

//https://stackoverflow.com/questions/20346661/java-fade-in-and-out-of-images
public class WechsleBild extends JPanel{
    private double alpha = 0.0;
    public WechsleBild(Benutzeroberflaeche bo, ArrayList<Image> bilder, boolean zufall, int interval, int uebergang) {
        //System.out.println(Arrays.toString(komponente));
        //interval in ms, wenn Zufall, dann zufällig aus Liste bilder auswählen, Bilder werden als Hintergrund auf der Benutzeroberfläche angezeigt
        try {
            Thread.sleep(interval);
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
        //bo.setContentPane(new JLabel(new ImageIcon(bilder.get(0))));
        bo.getContentPane().add(new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(bilder.get(0), 0, 0, null);
            }
        });
        while (alpha < 1.0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            alpha += (double) 1 / ((double) uebergang / 100);
            System.out.println(alpha + "");
        }
    }
}
