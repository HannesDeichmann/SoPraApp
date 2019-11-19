# Product Backlog

## Epic 1 Wächter-Modus

> Als Wächter möchte ich den Wächtermodus nutzen können, um Routen ablaufen zu können.

##### Ausführliche Beschreibung:
Der Wächter-Modus ist wie der Name schon sagt, für die Wächter gedacht. Um ihn zu starten muss der Wachmann sich mit einer Benutzerid und seinem Passwort anmelden.
Die Wachmänner nutzen ihn um auf ihrer App die nächsten Wegpunkte anzeigen zu lassen.
Dabei läuft ein Countdown ab, der anzeigt, wie viel Zeit ihnen noch bleibt um zum nächsten Wegpunkt zu kommen. Sobald ein Wachmann einen Wegpunkt erreicht hat,
muss er einen NFC Tag abscannen, bei dem sein Passwort abgefragt wird. Um den Überblick zu behalten, kann er sich eine Routenübersicht anschauen
und auf seinen Standort zugreifen. Falls der Wachmann sein Passwort drei Mal falsch eingibt, der Countdown abgelaufen ist, oder ein Wegpunkt übersprungen wurde
wird der stille Alarm aktiviert, der durch ein Textfenster visuell unterstützt wird.

### Feature 1.1 Route abarbeiten

> Als Wächter möchte ich meine Route abarbeiten, um meine Arbeit zu erfüllen.

- Aufwandsschätzung: L
- Akzeptanztests:
  - Wegpunkt abarbeiten:
  > Falls der Wächter sein Passwort richtig eingegeben hat, der Countdown nicht abgelaufen ist und er den NFC-Tag/QR-Code abgescannt hat wird der Wegpuntk als abgearbeitet angezeigt.
  - Alarm:
  > Das Textfeld, dass den Alarm visuell unterstützt, wird beim Aktivieren des Alarms angezeigt, wobei der Alarm wie beim Testfall "Wegpunkt abarbeiten" aktiviert werden soll.
  

#### Implementable Story 1.1.1 Wegpunkt abarbeiten

> Als Wächter möchte ich Wegpunkte abarbeiten können, um meine Route zu dokumentieren. Jeder Wegpunkt muss zu einer bestimmten Zeit 
abgearbeitet werden, wobei mich ein Countdown auf die verbliebene Zeit hinweist. Falls ich nicht innerhalb der vorgegebene Zeit beim Wegpunkt bin,
habe ich eine Toleranzzeit, in der der Wegpunkt immer noch abgearbeitet werden kann.

- Aufwandsschätzung: [90] Story Points
- Akzeptanztests:
  - Passwortabfrage:
  > Bei jedem Wegpunkt und bei jedem App-Start wird das Passwort drei Mal abgefragt, falls es falsch eingegeben wurde. Beim Fehlgeschlagenen dritten Versuch wird der Alarm aktiviert.
  - Countdown:
  > Der vom Admin eingestellte Countdown wird beim App-Start gestartet und bei jedem Wegpunkt aktualisiert.
  - Pausenknopf:
  > Für das Testobjekt wird eine Pausenzeit eingestellt und getestet, ob diese eingehalten wird und der Countdown um genau diese Zeit angehalten wird. Dadurch wird der stille Alarm verzögert.
  - Route starten/beenden:
  > Eine Route starten und wieder beenden und überprüfen, ob sie im Protokoll vermerkt ist
  

##### Task 1.1.1.1 Passwortabfrage

> Beim App-Start und bei jedem Wegpunkt wird das Passwort abgefragt um sicherzustellen, dass der Wachmann persönlich am Wegpunkt ist. Dafür hat er 3 Versuche, sonst wird der stille Alarm aktiviert.

- Aufwandsschätzung: [5] Stunden

##### Task 1.1.1.2 Countdown

> Der Admin kann für jeden Streckenabschnitt einen anderen Countdownlänge festlegen. Sobald der Wachmann seine Route startet, wird der Countdown gestartet. Dieser wird zurückgesetzt und aktualisiert, sobald er den nächsten Wegpunkt erreicht. Falls er den Wegpunkt zuspät erreichr wird der stille Alarm aktiviert.

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.1.3 Pausenknopf

> Beim aktivieren des Pausenknopfs wird der Countdown gestoppt, um den stillen Alarm bei einer Pause nicht zu aktivieren. Dies wird jedoch im Protokoll dokumentiert.

- Aufwandschätzung: [1] Stunde

##### Task 1.1.1.4 Wegpunkt abhaken

> Greift auf Scan-Funktion des Features 1.4 zu, um Wegpunkt abzuarbeiten

- Aufwandsschätzung: [1] Stunde

##### Task 1.1.1.5 Route starten 

> Ein Knopf startet die Route, wodurch der Timer startet sowie die Funktion "Wegpunkt-Anzeige"

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.1.6 Route beenden

> Nach dem letzten Wegpunkt beendet der Wächter aktiv über einen Knopf die Route und die Route wird ins Protokoll geschrieben(mit Hilfe der Funktion "Altroute eintragen" 1.3.2.1)

- Aufwandsschätzung: [1] Stunde

#### Implementable Story 1.1.2 Visualisierung

> Als Wächter möchte ich mir meine Route auf einer Karte sowie als Tabelle anzeigen lassen sowie meine Position anhand des zuletzt abgearbeiteten Wegpunkts.

- Aufwandsschätzung: [90] Story Points
- Akzeptanztests:
 - Wegpunkt-Anzeige:
 > Im Wächter-Home Screen wird mir nach Start einer Route der erste Wegpunkt angezeigt
 - Routenanzeige: 
 > Die Route mit allen abzulaufenden Wegpunkten in einer Tabelle anzeigen lassen
 - Kartenanzeige:
 > Der Wächter kann sich seine Wegpunkte sowie seine Position auf der Karte anzeigen lassen

##### Task 1.1.2.1 Wegpunktanzeige

> Der Wachmann kann auf seinem Handy den nächsten Wegpunkt anzeigen lassen, dieser wird immer aktualisiert, wenn der angezeigte Wegpunkt abgescannt wurde.

- Aufwandsschätzung: [1] Stunden

##### Task 1.1.2.2 Routenanzeige

> Die Routenanzeige ist als Übersicht für den Wächter gedacht. In dieser sind alle abzulaufenden Wegpunkte in einer Tabelle aufgelistet.

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.2.3 Kartenanzeige

> Die Kartenanzeige zeigt dem Wächter die Positionen der Wegpunkte sowie seine eigene Position anhand des letzten Wegpunkts.

### Feature 1.2 Alarm

> Als Wächter möchte ich einen Alarm auslösen sowie einen automatischen stillen Alarm senden, falls ich einen Wegpunkt nicht innerhalb der vorgeschrieben Zeit erreiche.

- Aufwandsschätzung: M
- Akzeptanztests:
  - Alarmmeldung:
  > Ein stiller Alarm wird simuliert und es wird getestet, ob der Zeitpunkt und der Ort richtig dokumentieren wurden.
  - Alarmauslösung:
  > Ein Alarm wird manuell per Knopf und Code ausgelöst und das Textfeld erscheint

#### Implementable Story 1.2.1 Stiller Alarm

> Der Stille Alarm wird ausgelöst, wenn der Wächter einen Wegpunkt nicht innerhalb der vorgegebenen Zeit auslöst

- Aufwandsschätzung: [30] Story Points
- Akzeptanztests:
 > Der Alarm wird automatisch nach abgelaufener Zeit ausgelöst

##### Task 1.2.1.1 Alarmmeldung 

> Bei nicht abgearbeiteten Wegpunkt wird automatisch ein stiller Alarm ausgelöst. Da ohne Server gearbeitet wird, ist es lediglich eine Alarmmeldung.

- Aufwandsschätzung: [1] Stunden

#### Implementable Story 1.2.2 Alarmauslösung 

> Als Wächter möchte ich manuell einen Alarm auslösen können.

- Aufwandsschätzung: [30] Story Points
- Akzeptanztests:
  > Der Alarm wurde per Knopf ausgelöst 
  > Der Alarm wurde per Code ausgelöst

##### Task 1.2.1.1 Alarmknopf 

> Ein Knopf, mit dem der Wächter aktiv einen Alarm auslösen kann.

- Aufwandsschätzung: [2] Stunden

##### Task 1.2.1.2 Alarmcode

> Bei der Wegpunktscannung kann der Wachmann einen "versteckten" Alarm auslösen, indem er statt seinem 
richtigen Code einen Code zur Alarmauslösung eingeben kann.

- Aufwandsschätzung: [2] Stunden

### Feature 1.3 Dokumentation

> Die Dokumentation arbeitet mit logEntry-Objekte der Routen, um diese nach Beendigung ins Protokoll zu überschreiben

- Aufwandsschätzung: S
- Akzeptanztests:
 > Ein Alarm wurde ins logEntry-Objekt überschrieben
 > Eine Ist-Zeit wurde ins logEntry-Objekt überschrieben 
 > Das logEntry-Objekt wurde nach Ablauf der Routenzeit vollständig ins Protokoll überschrieben

#### Implementable Story 1.3.1 Datenerfassung

> Die Ist-Zeiten und Alarme sowie die Alarmorte werden ins logEntry-Objekt eingetragen

- Aufwandsschätzung: [20] Story Points
- Akzeptanztests: 
 > Ein Alarm wurde ins logEntry-Objekt überschrieben
 > Eine Ist-Zeit wurde ins logEntry-Objekt überschrieben 

##### Task 1.3.1.1 Ist-Zeit speichern

> Wird bei erfolgreichen Scannen aufgerufen und schreibt die Zeit ins logEntry-Objekt

- Aufwandsschätzung: [2] Stunden

##### Task 1.3.1.2 Alarm/Alarmort speichern

> Wird vom Alarm aufgerufen und schreibt den Alarm sowie den Alarmort (der nächste Wegpunkt) ins logEntry-Objekt

- Aufwandsschätzung: [2] Stunden

#### Implementable Story 1.3.2 Protokolleintragung

> Das logEntry-Objekt wird nach Abschluss ins Protokoll übertragen

- Aufwandsschätzung: [20] Story Points
- Akzeptanztests:
 > Nach abgelaufener Zeit wird das logEntry-Objekt im Protokoll angezeigt

##### Task 1.3.2.1 logEntry eintragen

> Das logEntry-Objekt wird nach abgelaufener Zeit ins Protokoll überschrieben

- Aufwandsschätzung: [1] Stunde

### Feature 1.4 NFC

> Der NFC-Tag bindet die Wegpunkte an reale physische Orte

- Aufwandsschätzung: M
-Akzeptanztests: 
 > Ein Wegpunkt wird mit Hilfe eines NFC-Tags erfolgreich abgearbeitet

#### Implementable Story 1.4.1 Scanfunktion

> Jeder Wegpunkt hat einen eigenen NFC-Tag. Bei Betätigen eines Knopfes wird der Tag gescannt und die Zeit dokumentiert.

- Aufwandsschätzung: [50] Story Points
- Akzeptanztests:
 > Ein Wegpunkt wird mit Hilfe eines NFC-Tags erfolgreich abgearbeitet

##### Task 1.4.1.1 Wegpunkt scannen

> Ein Knopf öffnet ein Scanfenster um den Wegpunkt abzuhaken und gleicht den erwarteten NFC-Tag des Wegpunkts mit dem gescannten NFC-Tag ab. Bei erfolreichem Scannen wird die Funktion "Ist-Zeit speichern" aufgerufen.

- Aufwandsschätzung: [5] Stunden


## Epic 2 Admin-Modus

> Als Admin möchte ich Routen und Wächter verwalten können, um die Aufgabenverteilung zu planen.

##### Ausführliche Beschreibung: 
Der Admin-Modus erlaubt die unabhängige Erstellung von Routen und Wächterprofilen, wobei er diese beliebig bearbeiten und verbinden kann.
Routen können dabei auch ohne einen Wächter der sie ausführt erstellt werden und andersherum. Die Routen werden mit Hilfe von Wegpunkten
erstellt, die der Admin einzeln initialisieren muss. Als Additional Feature kann er sie auf einem hochgeladenen Bild oder einer Karte des Gebiets
einordnen, um dem Wächter visuelle Unterstützung zu geben. 
Der Admin kann sich ein Protokoll anzeigen lassen, in dem abgelaufene Routen verzeichnet werden (mit Soll-Zeit und tatsächlicher Zeit) und 
kann einzelne löschen, jedoch keine Bearbeitung der Zeiten vornehmen, wodurch Manipulation vorgebeugt wird. Die Routen, Wegpunkte sowie Wächterprofile werden
in csv-Dateien gespeichert, wobei sie verschlüsselt sind.

### Feature 2.1 Route

> Als Admin möchte ich individuelle Routen planen und erstellen.

- Aufwandsschätzung: XL
- Akzeptanztests:
   > Eine Route mit Wegpunkt-Objekten erstellen

#### Implementable Story 2.1.1 Routeneditor 

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
  - Route suchen:
  > Eine bestimme Route anahnd ihres Namens suchen
  - Zufällige Route:
  > Zufällige Route erstellen und überprüfen, ob die maximale Anzahl an Wegpunkten verwendet wurde sowie die Zeiten passen

##### Task 2.1.1.1 Route erstellen

- Aufwandschätzung: [3] Stunden

##### Task 2.1.1.2 Route bearbeiten

- Aufwandschätzung: [1] Stunden

##### Task 2.1.1.3 Route löschen

- Aufwandschätzung: [1] Stunden

##### Task 2.1.1.4 Zufällige Route

- Aufwandschätzung: [3] Stunden

##### Task 2.1.1.5 Route kopieren

- Aufwandschätzung: [1] Stunden

##### Task 2.1.1.6 Route suchen

- Aufwandsschätzung: [3] Stunden 


#### Feature 2.2 Wegpunkt

> Als Admin möchte ich Wegpunkte erstellen können, um sie zu einer Route zusammenzufassen.
- Attribute:
- Name
- NFC Tag
- Verbindung zu anderen Wegpunkten
- Zeiten zu anderen Wegpunkten
- Lokalisation auf Karte

- Aufwandsschätzung: M
- Akzeptanztests: 
 > Fertiges Wegpunkt-Objekt mit allen Attributen erstellen

#### Implementable Story 2.2.1 Wegpunkteditor

> Als Admin möchte Wegpunkte erstellen und konfigurieren können

- Aufwandsschätzung: [50] Story Points
- Akzeptanztests:
  - Wegpunkt erstellen: 
  > Testobjekt vom Typ Wegpunkt erstellen und Attribute ausgeben 
  - Wegpunkt bearbeiten: 
  > Ändern aller Attribute des Testobjekts und erneute Ausgabe der neuen Attribute
  - Wegpunkt löschen: 
  > Löschen des Wegpunkts und Ausgabe der Attribute. Wenn ein Error auftritt bzw. das Objekt nicht gefunden wird, ist das 
  > Löschen erfolgreich
  > NFC Tag zuordnen

##### Task 2.2.1.1 Wegpunkt erstellen

- Aufwandschätzung: [2] Stunden

##### Task 2.2.1.2 Wegpunkt bearbeiten

- Aufwandschätzung: [1] Stunden

##### Task 2.2.1.3 NFC Tags zuordnen

> Jeder Wegpunkt soll seinen individuellen NFC-Tag haben 

- Aufwandschätzung: [5] Stunden

###### Task 2.2.1.4 Wegpunkt löschen

- Aufwandsschätzung: [1] Stunde

### Feature 2.3 Wächter

> Als Admin möchte ich meine Wächter organisieren können, indem ich Wächterprofile anlege(Vor- und Nachname, Benutzer-ID und Passwort)

- Aufwandsschätzung: L
- Akzeptanztests:
  > die Gleichen wie die Testfälle der Implementable Story "Wächtereditor"

#### Implementable Story 2.3.1 Wächtereditor

> Als Admin möchte ich Wächter erstellen und verwalten können

- Aufwandsschätzung: [50] Story Points
- Akzeptanztests:
  - Wächter erstellen: 
  > Testobjekt vom Typ Wächter erstellen und Attribute ausgeben 
  - Wächter bearbeiten: 
  > Ändern aller Attribute des Testobjekts und erneute Ausgabe der neuen Attribute
  - Wächter löschen: 
  > Löschen des Wegpunkts und Ausgabe der Attribute. Wenn ein Error auftritt bzw. das Objekt nicht gefunden wird, ist das 
  > Löschen erfolgreich
  - Wächter suchen:
  > Ein angelegtes Wächterprofil anhand seines Namens finden

##### Task 2.3.1.1 Wächter erstellen

- Aufwandsschätzung: [2] Stunden

##### Task 2.3.1.2 Wächter bearbeiten

- Aufwandsschätzung: [1] Stunden

##### Task 2.3.1.3 Wächter löschen

- Aufwandsschätzung: [1] Stunden

##### Task 2.3.1.4 Wächter suchen

- Aufwandsschätzung: [2] Stunden

### Feature 2.4 Karte 

> Als Admin möchte ich Wegpunkte auf einer Karte eintragen können

- Aufwandsschätzung: M
- Akzeptanztests:
  > Mehrere Wegpunkte auf einer Karte anzeigen lassen

#### Implementable Story 2.4.1 Lokalisierung

> Als Admin kann ich Wegpunkte auf einer Karte markieren

- Aufwandsschätzung: [30] Story Points
- Akzeptanztests: 
 >Einen Wegpunkt auf der Karte eintragen und im Wächter-Modus anzeigen lassen
 

##### Task 2.4.1.1 Wegpunkt einzeichen	

- Aufwandsschätzung: [3] Stunden


#### Implementable Story 2.4.2 Karte erstellen

> Als Admin kann ich ein Bild hochladen, um eine Karte der Wegpunkte anzulegen

- Aufwandsschätzung: [30] Story Points
- Akzeptanztests:
 > Ein Bild hochladen und als Karte im Wächter-Modus aufrufen

##### Task 2.4.2.1 Karte einfügen

- Aufwandsschätzung: [3] Stunden 


### Feature 2.5 Organisation 

> Als Admin möchte ich erstellte Wächter und Routen einander zuweisen (indem ich sie in einer Tabelle auswähle)
und mir in einer Übersicht anzeigen lassen.

- Aufwandsschätzung: M
- Akzeptanztests:
 > Mehrere Routen Wächtern zuweisen und anzeigen lassen 

#### Implementable Story 2.5.1 Kalender

> Im Kalender möchte ich als Admin Routen und Wächter in einem zeitlichen Rahmen organisieren

- Aufwandsschätzung: [40] Story Points
- Akzeptantztests:  
 > Mehrere Routen Wächtern zuweisen und zu bestimmten Uhrzeiten und Wochentagen einorden
 

##### Task 2.5.1.1 Route zuweisen 

 - Aufwandsschätzung: [2] Stunden

##### Task 2.5.1.2 Wächter zuweisen 

 - Aufwandsschätzung: [2] Stunden

##### Task 2.5.1.3 Kalender anzeigen 

 - Aufwandsschätzung: [2] Stunden

### Feature 2.6 Protokoll

> Als Admin möchte ich das Protokoll einsehen und logEntrys, also abgelaufene Routen, die im Protokoll stehen,
löschen können. Das Protokoll speichert die Routen in chronologischer Reihenfolge.

- Aufwandsschätzung: M
- Akzeptanztests:
  > Eine abgeschlossene Route im Protokoll abspeichern und anzeigen lassen
  > Eine Route aus dem Protokoll löschen
  > Das gesamte Protokoll als Tabelle exportieren

#### Implementable Story 2.6.1 Protokoll verwalten

> Als Admin möchte ich das Protokoll verwalten können

- Aufwandsschätzung: [50] Story Points
- Akzeptantztests: 
 > Eine abgeschlossene Route im Protokoll abspeichern und anzeigen lassen
 > Eine Route aus dem Protokoll löschen
 > Das gesamte Protokoll als Tabelle exportieren

##### Task 2.6.1.1 Protokoll anzeigen

- Aufwandsschätzung: [2] Stunden

##### Task 2.6.1.2 logEntry löschen 

- Aufwandsschätzung: [1] Stunden

##### Task 2.6.1.3 Eportieren

- Aufwandsschätzung: [3] Stunden