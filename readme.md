# Schülerprojekt "Dating Plattform"
## Funktionen
## Änderungen in der Datenbank
- Tabelle "benutzer" hinzugefügt
  - hat eigene ID (für die Dating-App) und verknüpft mit Fremdschlüssel SchülerID (falls der neue Benutzer ein Schüler ist)
## Methoden
- Registrierung.registrierdaten_korrekt() - Jorin
- Anmeldung.string_ist_dtformat() - Luka
- Anmeldung.zeige_fehler() - Luka
- Anmeldung.existiert_benutzer() - Renke
- Anmeldung.verschluessel_passwort() - Renke
- (Klasse) Neues_Textfeld - Renke



## Notizen

registrierung slider mit wie alt plus oder minus dein maximum ist
- wenn suchende ü18 ist, nur ü18 leute angezeigt werden


- beschreibungstext, der angezeigt wird
  (- wohnort)


+1 wenn einzelne werte matchen, ab gewissem wert ist die person es wert gedatet zu werden.
-1 wenn einzelne werte nicht matchen

festlegen, was matched:
tabelle in datenbank mit einzelnen attribute

passwort nach zeichen undso checken

in datenbank wurde neue tabelle für benutzer angelegt, gründe:
- benutzer und schüler sind getrennt
- auch nicht schüler können daten (macht das überhaupt sinn?)
  benutzer enthält ID für identifikation als primärschlüssel, benutzername email und passwort für die anmeldung, des weiteren gibt es eine spalte für eine mögliche schülerID, falls der registrierer ein schüler ist, muss er dadurch keine registriedaten angeben
  des weiteren wurde eine tabelle mit den nötigen registrierdaten hinzugefügt
  die registrierdaten bestehen aus größe, geburtstag, sex. orientierung, geschlecht, lieblingsfach, augenfarbe, haarefarbe und körp. statur, diese helfen dabei, den richtigen partner zu finden


reflexion
a) In unserer app kann man sich mit email, benutzername und passwort registrieren. Die anmeldedaten werden sicher und verschlüsselt in einer datenbank gespeichert, wodurch man sich beim späteren aufrufen der app anmelden kann und dann die registrierdaten nicht mehr eingeben muss. Man kann sich nur registrieren, wenn man die notwendigen daten (richtig) eingegeben hat. Also kann man auch kein falsches geburtsdatum angeben, was die handhabung mit der datenbank erleichtert.
nach der registrierung werden einem dann profile vorgeschlagen (auch nicht angemeldete schüler werden vorgeschlagen). man kann sie entweder wegklicken, oder sich das profil speichern. gespeicherte profile können über ein knopfdruck abgerufen werden. der suchalgorithmus, nach dem profile ausgesucht werden, basiert darauf, dass wir eine tabelle haben, in der passende attribute drin gespeichert sind, die zueinander passen. pro passendes attribut gibt es entweder +1 oder -1, je höher der "score", desto besser passen die partner zusammen. bei jedem buttonclick wird von den obersten 20% der scores ein zufälliges profil ausgewählt, wenn jeder der obersten 20% bereits vorkam, werden die nächsten 20% durchgegangen. bereits durchgegangene profile werden nur pro "session" gespeichert.

b) Probleme gab es wenige, da wir uns bereits von anfang an stark organisiert haben. wir haben das projekt in einzelne teile zerlegt und jeder konnte "anfragen" stellen, welche methoden er brauchte, um seine momentane aufgabe zu erfüllen. jeder konnte dann eine anfrage annehmen und diese methode schreiben. da einzelne methoden unabhängig vom rest des projektes sind, gelang es uns sehr einfach, die arbeit aufzuteilen. Die arbeitsteile wurden dann in einem github-projekt zusammengeführt, sodass jeder die neuste version des aktuellen projektes hatte. dennoch tauchten wenige programmiertechnische probleme auf. zum beispiel fiel es uns zunächst schwer, den suchalgorithmus umzusetzen. wir wollten hard-coding vermeinden und haben dadurch einiges an zeit verloren, in der wir uns überlegt hatten, wie wir dieses problem am besten lösen.

c) [hier wohnort-problem einfügen]
ebenfalls wollten wir einen deutlich ausgefeilten suchalgorithmus implementieren, welcher deutlich mehr suchoptionen hat und auch noch anpassungen vornehmen kann.

d) eine Verbesserung unseres projektes wäre, das Design etwas ausgefeilter zu gestalten
eine gedachte Änderung ist noch,


suchalgorithmus
ursprüngliche idee:
- alle parameter irgendwie verbinden, "hard-coden" z.b. welche hobbies undso zusammenpassen und dann prozentual den besten partner raussuchen
- viel zu komplex
- nicht mit momentanen mitteln umsetzbar

Wir wollten auch den Wohnort in die Partnersuche mit einbeziehen, da die Nähe eine wichtige Rolle spielt, weil eine weite Entfernung die Beziehung einschränken könnte.
Jedoch ist dies sehr schwer in Java umzusetzen, was den zeitlichen Rahmen des Projektes gesprengt hätte, da man die Entfernungen zwischen den Wohnorten herausfinden müsste.
Ebenfalls ginge auch ein wenig Privatsphäre verloren, da man, für alle sichtbar, seinen Wohnort preisgibt.
Deshalb haben wir uns entschieden diesen Teil auszulassen.


new class FadeImage(Image[]), takes in Image array and fades between them. (randomly)

resize background to window size

Die Klasse WechsleBild wechselt zwischen Bildern mit einem weichen Übergang. Dies wird bei uns genutzt, um das Hintergrundbild im Hauptfenster zu ändern. Die Klasse hat ein Image[] als Eingangsparameter. Zwischen allen in diesem Array enthaltenen Bildern wird dann (zufällig ja/nein als weiterer Parameter) gewechselt. Ein besonderes Merkmal der Klasse ist die EventQueue.invokeLater(new Runnable() [...] ) Methode. Diese wird benötigt, da der Prozess im Hintergrund seperat laufen muss. Normalerweise kann nur eine Operation gleichzeitig durchgeführt werden und wenn konstant der Alphawert eines Bildes geändert wird, kann keine andere Operation (ein Knopfdruck etc) durchgeführt werden. Mit der Methode erzeugt man einen neuen Thread, welcher nebenbei gleichzeitig laufen kann. public void run() ist dabei die Methode, die der Thread ausführt.

Überraschungsfaktor und (optional=keine Präferenz, suchparameter) Figurtyp, Haarfarbe, Augenfarbe, Orientierung, Fachbereich




INFO:
cp.getComponents(); -> array of components

WechsleHintergrund(comp cp) -> repaint all components