import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.security.MessageDigest;
import java.util.Arrays;

public class Hilfsklasse {
    public static boolean ist_numerisch(String c) {
        //Guckt nach, ob der Charakter in eine Zahl umgewandelt werden kann
        try { //Integer.parseInt wirft eine Fehlermeldung, falls c kein Int ist, diese wird hiermit abgefangen
            Integer.parseInt(c);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean string_ist_dtformat(String datetime) {
        if (datetime.length() != 10) return false; //falls das Datum länger als 10 Buchstaben ist, muss etwas falsch sein
        for (int i = 0; i < datetime.length(); i++) {
            if ((i<4 || i==5 || i==6 || i>7) && !ist_numerisch(datetime.charAt(i) + "")) return false; //falls an Stelle 1-4, 6, 7, 9, 10 keine Zahl ist, ist das Datum ungültig
            if ((i==4 || i==7) && datetime.charAt(i) != '-') return false; //falls an Stelle 5, 8 kein Bindestrich ist, ist das Datum ungültig
        }
        return true; //falls keine der obigen Bedingungen zutraf, ist das Datum korrekt eingegeben
    }

    public static void markiere_label(Container cp, String name, Color farbe) {
        Component[] k = cp.getComponents();
        for (int i = 0; i < k.length; i++) {
            if (Arrays.stream(k).toList().get(i).getName() == name && Arrays.stream(k).toList().get(i) instanceof JLabel) {
                ((JLabel) Arrays.stream(k).toList().get(i)).setBorder(new LineBorder(farbe));
            }
        }
    }
}