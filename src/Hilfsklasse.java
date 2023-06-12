import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Hilfsklasse {
    public static String[] faecher = {"Mathematik", "Physik", "Informatik", "Chemie", "Biologie", "Politik", "Geschichte", "Erdkunde", "Religion", "Deutsch", "Englisch", "Latein", "Französisch", "Spanisch", "Kunst", "Musik", "Sport"};
    public static String[] augenfarben = {"Blau", "Grün", "Braun"};
    public static String[] haarfarben = {"Braun", "Blond", "Rot", "Bunt", "Blau", "Grün", "Immer anders"};
    public static String[] figuren = {"Sportlich", "Gesund", "Übergewichtig", "keine Angabe"};
    public static String[] profile = {"Naturwissenschaftlich", "Gesellschaftswissenschaftlich", "Sprachlich", "Sportlich", "keine Angabe"};//0-4, 5-8, 9-15, 16; Stellen der zugehörigen Fächer
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

    public static String fachgebiet(String fach) {//gibt Fachgebiet (Profil), in welches das Fach fällt aus
        for (int i = 0; i < faecher.length; i++) {
            if (fach.equals(faecher[i])) {
                if (i<5) return profile[0];
                else if (i<9) return profile[1];
                else if (i<16) return profile[2];
                else return profile[3];
            }
        }
        return "kein Fachgebiet"; //falls "kein Fachgebiet" zurückgegeben wird, kann das Fach nicht zugeordnet werden
    }

    public static int string_zu_bmi(String figur) {
        if (figur.equals("Sportlich")) return 20;
        else if (figur.equals("Gesund")) return 25;
        return 100;
    }

    public static int altersunterschied(String _geburtstag_1, String _geburtstag_2) {
        //Fremdcode inspiration bei https://www.baeldung.com/java-date-difference
        int abstand = 0;
        SimpleDateFormat datumsformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date geburtstag_1 = datumsformat.parse(_geburtstag_1);
            Date geburtstag_2 = datumsformat.parse(_geburtstag_2);
            abstand = (int) TimeUnit.DAYS.convert(Math.abs(geburtstag_1.getTime() - geburtstag_2.getTime()), TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return abstand;
    }
    public static boolean prozentuelle_auswahl(int prozent) {
        //bei jedem Methodenaufruf wird zu prozent% true zurückgegeben
        int zufallszahl = (int) (Math.random() * ((100-1)+ 1)) + 1; //erzeugt zufallszahl zwischen 1 und 100
        if (zufallszahl <= prozent) return true; //zu prozent% der Fälle ist dies Wahr
        return false;
    }
}