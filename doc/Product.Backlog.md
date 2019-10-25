# Product Backlog

Hier werden **alle** Anforderungen in Form von **User Stories** geordnet aufgelistet.

## Epic 1 Wächter-Modus

> Als Wächter möchte ich den Wächtermodus nutzen können, um Routen ablaufen zu können.

Ausführliche Beschreibung:
Der Wächter-Modus ist wie der Name schon sagt, für die Wächter gedacht. Um ihn zu starten muss der Wachmann sich mit einer Benutzerid und seinem Passwort anmelden.
Die Wachmänner nutzen ihn um auf ihrer App die nächsten Wegpunkte anzeigen zu lassen.
Dabei läuft ein Countdown ab, der anzeigt, wie viel Zeit ihnen noch bleibt um zum nächsten Wegpunkt zu erreichen. Sobald ein Wachmann einen Wegpunkt erreicht hat,
muss er einen NFC Tag oder QR-Code abscannen, bei dem sein Passwort abgefragt wird. Um den Überblick zu behalten, kann er sich eine Routenübersicht anschauen
und auf seinen Standort zugreifen. Falls der Wachmann sein Passwort drei Mal falsch eingibt, der Countdown abgelaufen ist, oder ein Wegpunkt übersprungen wurde
wird der stille Alarm aktiviert, der durch ein Textfenster visuell unterstützt wird.

### Feature 1.1 Route abarbeiten

> Als Wächter möchte ich meine Route abarbeiten, um meine Arbeit zu erfüllen.

- Aufwandsschätzung: L
- Akzeptanztests:
  - Wegpunkt abarbeiten:
  > Falls der Wächter sein Passwort richtig eingegeben hat, der Countdown nicht abgelaufen ist und er den NFC-Tag/QR-Code abgescannt hat wird der Wegpuntk als abgearbeitet angezeigt.
  - Alarm:
  > Das Textfeld, dass den Alarm visuell unterstützt, wird beim aktivieren des Alarms angezeigt. Wobei der Alarm wie beim Testfall "Wegpunkt abarbeiten" aktiviert werden soll, zusätzlich sollten die Wegpunkte in der richtigen Reihenfolge abgearbeitet werden.
  
#### Implementable Story 1.1.1 Wegpunkt abarbeiten

> Als Wächter möchte ich Wegpunkte abarbeiten können, um meine Route zu dokumentieren.

- Aufwandsschätzung: [90] Story Points
- Akzeptanztests:
  - Passwortabfrage:
  > Bei jedem Wegpunkt und bei jedem App-Start wird das Passwort drei Mal abgefragt, falls es falsch eingegeben wurde. Beim Fehlgeschlagenen dritten Versuch wird der Alarm aktiviert.
  - Scan-Funktion:
  > Die Scan-Funktion sollte durch einen Knopf erreichbar sein. Der bei jedem Wegpunkt ausgeführt werden muss. Der Scan muss erfolgreich durchgeführt worden sein und falls nötig sollte auch die Kamera geöffnet werden.
  - Wegpunkt Anzeige:
  > Es wird solange der nächste Wegpunkt angezeigt, bis dieser abgescannt wurde und somit aktualisiert wurde. Dann muss der nächste Wegpunkt richtig angezeigt werden.
  - Countdown:
  > Der vom Admin eingestellte Countdown wird beim App-Start gestartet und bei jedem Wegpunkt aktualisiert.
  - Pausenknopf:
  > Für das Testobjekt wird eine Pausenzeit eingestellt und getestet, ob diese eingehalten wird und  der Countdown um genau diese Zeit angehalten wird. Dadurch wird der stille Alarm verzögert.
  - Routenanzeige
  > Zuerst wird eine Route erstellt mit verschiedenen Wegpunkten. Diese soll richtig angezeigt werden und durch einen Button  geöffnet und geschlossen werden können. Außerdem wird der Standort des Wächters richtig angezeigt.
  - Dokumentation:
  > Es wird ein Wachmann simuliert, der gerade seine Route abgearbeitet hat. Dabei wird getestet, ob alle Pausen, Countdowns und Wegpunkte richtig dokumentiert wurden.  
 
##### Task 1.1.1.1 Passwortabfrage

>Beim App-Start und bei jedem Wegpunkt wird das Passwort abgefragt um sicherzustellen, dass der Wachmann persönlich am Wegpunkt ist. Dafür hat er 3 Versuche, sonst wird der stille Alarm aktiviert.

- Aufwandsschätzung: [5] Stunden

##### Task 1.1.1.2 Scan-Funktion (QR-Code oder NFC)

> Jeder Wegpunkt hat einen eigenen NFC-Tag oder QR-Coder, bei betätigen eines Knopfes wird das Element gescannt und die Zeit dokumentiert.

- Aufwandsschätzung: [3] Stunden

##### Task 1.1.1.3 Wegpunkt Anzeige

> Der Wachmann kann auf seinem Handy den nächsten Wegpunkt anzeigen lassen, dieser wird immer aktualisiert, wenn der angezeigte Wegpunkt abgescannt wurde.

- Aufwandsschätzung: [1] Stunden

##### Task 1.1.1.4 Countdown

> Der Admin kann für jeden Streckenabschnitt einen anderen Countdownlänge festlegen. Sobald der Wachmann seine Route startet, wird der Countdown gestartet. Dieser wird zurückgesetzt und aktualisiert, sobald er den nächsten Wegpunkt erreicht. Falls er den Wegpunkt zuspät erreichr wird der stille Alarm aktiviert.

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.1.5 Pausenknopf

> Beim aktivieren des Pausenknopfs wird der Countdown gestoppt, um den stillen Alarm bei einer Pause nicht zu aktivieren. Dies wird jedoch im Protokoll dokumentiert.

- Aufwandschätzung: [1] Stunden

##### Task 1.1.1.6 Routenanzeige

> Die Routenanzeige ist als Übersicht für den Wächter gedacht. Auf dieser ist sein Standort, abgearbeitete Wegpunkte und möglicherweise ein schon ausgelöster stiller Alarm markiert.

- Aufwandschätzung: [2] Stunden

##### Task 1.1.1.7 Dokumentation

> Alle abgearbeiteten Wegpunkte werden mit der zugehörigen Zeit in einer CSV-Datei gespeichert.

- Aufwandschätzung: [2] Stunden 

#### Implementable Story 1.1.2 Alarm

> Als Wächter möchte ich einen ALarm auslösen, um Hilfe zu holen.

- Aufwandsschätzung: [50] Story Points
- Akzeptanztests:
  - Vibration:
  > Eine Route aus zwei Wegpunkten wird erstellt, inklusive Zeitdifferenz, und eine Zeit ab der die Vibration starten soll. Diese wird dann verglichen mit der Zeit bei der das Handy vibriert.
  - Fehlermeldung:
  > Eine Simulation mit Null Sekunden Countdown wird durch geführt und getestet, ob das Textfeld erscheint.
  - Dokumentation:
  > Ein stiller Alarm wird simuliert und es wird getestet, ob der Zeitpunkt und der Ort richtig dokumentieren wurden.
  
##### Task 1.1.2.1 Vibration 

> Um den Wächter selbst zu alamieren, falls er einen Wegpunkt nicht in der gegebene Zeit erreicht hat.

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.2.2 Fehlermeldung 

> Damit der Kunde erkennt, wann ein stiller Alarm aktiviert wurde, da die Servernachricht nicht implementiert wird.

- Aufwandsschätzung: [1] Stunden

##### Task 1.1.2.3 Dokumentation

> Um einen Alarm im Routenprotokoll vermerken zu können, mit seinem Zugehörigen Standort und seiner Zeit.

- Aufwandsschätzung: [2] Stunden

## Epic 2 Admin-Modus

> Als Admin möchte ich Routen und Wächter verwalten können, um die Aufgabenverteilung zu planen.

##### Ausführliche Beschreibung: 
Der Admin-Modus erlaubt die unabhängige Erstellung von Routen und Wächterprofilen, wobei er diese beliebig bearbeiten kann.
Routen können dabei auch ohne einen Wächter der sie ausführt erstellt werden und andersherum. Die Routen werden mit Hilfe von Wegpunkten
erstellt, die der Admin einzeln initialisieren muss. Als Additional Feature kann er sie auf einem hochgeladenen Bild oder einer Karte des Gebiets
einordnen, um dem Wächter visuelle Unterstützung zu geben. 
Der Admin kann sich ein Protokoll anzeigen lassen, in dem abgelaufene Routen verzeichnet werden (mit Soll-Zeit und tatsächlicher Zeit) und 
kann einzelne löschen, jedoch keine Bearbeitung der Zeiten vornehmen, wodurch Manipulation vorgebeugt wird.

### Feature 2.1 Routenplaner

> Als Admin möchte ich meine Routen planen, um das Wachpersonal zu organisieren.

- Aufwandsschätzung: XL
- Akzeptanztests:
  - Eine Route mit Wegpunkt-Objekten erstellen und einer Wächter-Instanz zuweisen

#### Implementable Story 2.1.1 Wegpunkte

> Als Admin möchte ich Wegpunkte erstellen können, um sie zu einer Route zusammenzufassen.

- Aufwandsschätzung: [50] Story Points
- Akzeptanztests:
  - Wegpunkt erstellen: 
  > Testobjekt vom Typ Wegpunkt erstellen und Attribute ausgeben 
  - Wegpunkt bearbeiten: 
  > Ändern aller Attribute des Testobjekts und erneute Ausgabe der neuen Attribute
  - Wegpunkt löschen: 
  > Löschen des Wegpunkts und Ausgabe der Attribute. Wenn ein Error auftritt bzw. das Objekt nicht gefunden wird, ist das 
  > Löschen erfolgreich

##### Task 2.1.1.1 Wegpunkt erstellen

- Aufwandschätzung: [2] Stunden

##### Task 2.1.1.2 Wegpunkt bearbeiten

- Aufwandschätzung: [1] Stunden

##### Task 2.1.1.3 Karte/Bild einfügen

- Aufwandschätzung: [2] Stunden

##### Task 2.1.1.4 Wegpunkt lokalisieren (Karte/Bild)

- Aufwandschätzung: [1] Stunden

#### Implementable Story 2.1.2 Route

> Als Admin möchte ich Routen erstellen und verwalten können, um die Aufgaben der Wächter zu definieren.

- Aufwandschätzung: [90] Story Points
- Akzeptanztests:
   - Route erstellen: 
  > Testobjekt vom Typ Route erstellen und Attribute ausgeben 
  - Route bearbeiten: 
  > Ändern aller Attribute des Testobjekts und erneute Ausgabe der neuen Attribute
  - Route löschen: 
  > Löschen des Wegpunkts und Ausgabe der Attribute. Wenn ein Error auftritt bzw. das Objekt nicht gefunden wird, war das 
  > Löschen erfolgreich
  - Route kopieren:
  > Kopie eines Routenobjekts erstellen und Attribute beider Objekte ausgeben: Wenn beide Attribute gleich sind war das Kopieren erfolgreich
  - Zufällige Route:
  > Zufällige Route erstellen und überprüfen, ob die maximale Anzahl an Wegpunkten verwendet wurde sowie die Zeiten passen
  - Wächter zuweisen:
  > Das Attribut "Wächter" eines Routenobjekts auf ein Objekt des Typs Wächter ändern und diesen ausgeben

##### Task 2.1.2.1 Route erstellen

- Aufwandschätzung: [3] Stunden

##### Task 2.1.2.2 Route bearbeiten

- Aufwandschätzung: [1] Stunden

##### Task 2.1.2.3 Route löschen

- Aufwandschätzung: [1] Stunden

##### Task 2.1.2.4 Zufällige Route

- Aufwandschätzung: [3] Stunden

##### Task 2.1.2.5 Route kopieren

- Aufwandschätzung: [1] Stunden

##### Task 2.1.2.6 Wächter zuweisen

- Aufwandschätzung: [1] Stunden

### Feature 2.2 Wächterplaner

> Als Admin möchte ich meine Wächter organisieren können.

- Aufwandsschätzung: L
- Akzeptanztests:
  - die Gleichen wie die Testfälle der Implementable Story "Wächter"

#### Implementable Story 2.2.1 Wächter

> Als Admin möchte ich Wächter erstellen und verwalten können, um sie den Routen zuweisen zu können

- Aufwandschätzung: [50] Story Points
- Akzeptanztests:
  - Wächter erstellen: 
  > Testobjekt vom Typ Wächter erstellen und Attribute ausgeben 
  - Wächter bearbeiten: 
  > Ändern aller Attribute des Testobjekts und erneute Ausgabe der neuen Attribute
  - Wächter löschen: 
  > Löschen des Wegpunkts und Ausgabe der Attribute. Wenn ein Error auftritt bzw. das Objekt nicht gefunden wird, ist das 
  > Löschen erfolgreich

##### Task 2.2.1.1 Wächter erstellen

- Aufwandschätzung: [2] Stunden

##### Task 2.2.1.2 Wächter bearbeiten

- Aufwandschätzung: [1] Stunden

##### Task 2.2.1.3 Wächter löschen

- Aufwandschätzung: [1] Stunden

#### Implementable Story 2.2.2 Routenprotokoll

> Als Admin möchte ich das Routenprotokoll erstellen und verwalten können, um den Verlauf der Routen einzusehen.

- Aufwandschätzung: [60] Story Points
- Akzeptanztests:
  - Eine abgeschlossene Route im Protokoll abspeichern und anzeigen lassen

##### Task 2.2.2.1 Routenprotokoll anzeigen

- Aufwandschätzung: [2] Stunden

##### Task 2.2.2.2 Routeprotokoll bearbeiten

- Aufwandschätzung: [1] Stunden

## Epic 3 Sensoren

> Als System möchte ich die benötigten Sensoren ansprechen und auslesen können.

Ausführliche Beschreibung:

### Feature 3.1 GPS und QR-Code

> Als System möchte ich die GPS Daten auslesen können und QR-Codes (mit der Kamera) scannen können.

- Aufwandsschätzung: M
- Akzeptanztests:
  - *TODO (Beschreibung von Testfällen die das erwartete Verhalten des gesamten Features überprüfen.)*
  - *TODO*
  - *TODO*

### Feature 3.2 NFC

> Als System möchte ich den NFC Sensor ansprechen und auslesen können.

- Aufwandsschätzung: S
- Akzeptanztests:
  - *TODO (Beschreibung von Testfällen die das erwartete Verhalten des gesamten Features überprüfen.)*
  - *TODO*
  - *TODO*

### Feature 3.3 Vibration

> Als System möchte ich die Vibrations Funktion des Handys ansprechen können.

- Aufwandsschätzung: XS
- Akzeptanztests:
  - *TODO (Beschreibung von Testfällen die das erwartete Verhalten des gesamten Features überprüfen.)*
  - *TODO*
  - *TODO*
