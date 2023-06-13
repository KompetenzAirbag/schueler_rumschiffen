import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
            if (Arrays.stream(k).collect(Collectors.toList()).get(i).getName() == name && Arrays.stream(k).collect(Collectors.toList()).get(i) instanceof JLabel) {
                ((JLabel) Arrays.stream(k).collect(Collectors.toList()).get(i)).setBorder(new LineBorder(farbe));
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

    public static Date geburtstag_aus_datum(String datum) {
        SimpleDateFormat datumsformat = new SimpleDateFormat("yyyy-MM-dd");
        Date geburtstag = null;
        try {
            geburtstag = datumsformat.parse(datum);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return geburtstag;
    }

    public static int altersunterschied(String geburtstag_1, String geburtstag_2) {
        //Fremdcode inspiration bei https://www.baeldung.com/java-date-difference
        return (int) TimeUnit.DAYS.convert(Math.abs(geburtstag_aus_datum(geburtstag_1).getTime() - geburtstag_aus_datum(geburtstag_2).getTime()), TimeUnit.MILLISECONDS);
    }

    public static boolean prozentuelle_auswahl(int prozent) {
        //bei jedem Methodenaufruf wird zu prozent% true zurückgegeben
        int zufallszahl = (int) (Math.random() * ((100-1)+ 1)) + 1; //erzeugt zufallszahl zwischen 1 und 100
        if (zufallszahl <= prozent) return true; //zu prozent% der Fälle ist dies Wahr
        return false;
    }

    public static int generiere_neue_id(String tabelle) {
        //generiert laufende Nummer für ID (wenn es eine vorhandene ID gibt, wird die höchste ID+1 ausgegeben)
        //falls die ID kein Integer ist, wird eine eigene ID erzeugt, welche der Anzahl der Datensätze entspricht
        int max_id = 0;

        //hier werden Tabellennamen und Anzahl der Datensätze der gesuchten Tabelle abgefragt
        String sql = "SELECT * FROM " + tabelle;
        String[][] ergebnis = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql);
        String[] tabellennamen = ergebnis[0];
        int laenge = ergebnis.length-1; //Länge der Tabelle wird später genutzt, falls die ID kein Int ist.

        //hier werden die Tabellennamen durchgegangen und geguckt, ob sie das Stichwort "ID" enthalten, da einige Tabellen nicht nur "ID" als Tabellennamen haben, sondern vielmehr eine Kombination
        for (int i=0; i<tabellennamen.length; i++) {
            if (tabellennamen[i].contains("ID") || tabellennamen[i].contains("id") || tabellennamen[i].contains("Id")) {
                sql = "SELECT MAX(" + tabellennamen[i] + ") FROM " + tabelle;
                ergebnis = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql);
                if (ergebnis.length > 1) {
                    try { //try-catch wird benutzt, um zu gucken, ob es sich wirklich um einen Int handelt (Integer.parseInt wirft eine Fehlermeldung anderenfalls)
                        max_id = Integer.parseInt(ergebnis[1][0]);
                    }
                    catch (Exception e) {
                        System.out.println("Warning: " + e);
                        max_id = laenge; //falls es keinen Int als ID gibt, wird provisorisch die Länge der Tabelle genommen
                    }
                }
                else {
                    max_id = -1; //falls die Datenbank leer ist, wird die erste ID 0 sein. Da am Ende +1 gerechnet wird, muss hier die ID -1 sein
                }
                break;
            }
        }
        return max_id+1;
    }

    public static String benutzerdaten_als_text(String id, boolean ist_schueler) {
        String ausgangstext = "";
        if (ist_schueler) {
            String sql = "SELECT * FROM schueler WHERE ID_Nummer = '" + id + "'";
            String[][] ergebnis = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql);
            String alter = Period.between(Hilfsklasse.geburtstag_aus_datum(ergebnis[1][3]).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears() + "";
            String groesse = ergebnis[1][11];

            String buchstabe = "";
            String geschlecht = "";
            String pronomen = "";
            switch (ergebnis[1][6]) {
                case "m": pronomen = "Er"; geschlecht = "mann"; break;
                case "w": pronomen = "Sie"; geschlecht = "frau"; buchstabe = "e"; break;
            }
            ausgangstext = "Dein" + buchstabe + " Traum" + geschlecht + " ist " + ergebnis[1][2] + " " + ergebnis[1][1] + ".\n" + pronomen + " ist " + alter + " Jahre alt und " + groesse + "m groß!";
        }
        else {
            String sql = "SELECT * FROM benutzer WHERE ID = '" + id + "'";
            String[][] ergebnis = Benutzeroberflaeche.myDBManager.sqlAnfrageAusfuehren(sql);
            String alter = Period.between(Hilfsklasse.geburtstag_aus_datum(ergebnis[1][6]).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears() + "";
            String groesse = ergebnis[1][5];

            String buchstabe = "";
            String geschlecht = "";
            String pronomen = "";
            switch (ergebnis[1][8]) {
                case "m": pronomen = "Er"; geschlecht = "mann"; break;
                case "w": pronomen = "Sie"; geschlecht = "frau"; buchstabe = "e"; break;
            }
            ausgangstext = "Dein" + buchstabe + " Traum" + geschlecht + " ist " + ergebnis[1][1] + ".\n" + pronomen + " ist " + alter + " Jahre alt und " + groesse + "m groß!";
        }
        return ausgangstext;
    }
}