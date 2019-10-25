# Product Backlog

Hier werden **alle** Anforderungen in Form von **User Stories** geordnet aufgelistet.

## Epic 1 Wächter-Modus

> Als Wächter möchte ich den Wächtermodus nutzen können, um Routen ablaufen zu können.

Ausführliche Beschreibung: 

### Feature 1.1 Route abarbeiten

> Als Wächter möchte ich meine Route abarbeiten, um meine Arbeit zu erfüllen.

- Aufwandsschätzung: L
- Akzeptanztests:
  - *TODO (Beschreibung von Testfällen die das erwartete Verhalten des gesamten Features überprüfen.)*
  - *TODO*
  - *TODO*

#### Implementable Story 1.1.1 Wegpunkt abarbeiten

> Als Wächter möchte ich Wegpunkte abarbeiten können, um meine Route zu dokumentieren.

- Aufwandsschätzung: [90] Story Points
- Akzeptanztests:
  - *TODO (Beschreibung von Testfällen die das erwartete Verhalten der gesamten Implementable Story überprüfen.)*
  - *TODO*
  - *TODO*

##### Task 1.1.1.1 Passwortabfrage

- Aufwandsschätzung: [5] Stunden

##### Task 1.1.1.2 Scan-Funktion (QR-Code oder NFC)

- Aufwandsschätzung: [3] Stunden

##### Task 1.1.1.3 Aktuelle Wegpunkt Anzeige

- Aufwandsschätzung: [1] Stunden

##### Task 1.1.1.4 Countdown

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.1.5 Pausenknopf um die Routenzeit zu pausieren, damit kein Alarm ausgelöst wird

- Aufwandschätzung: [1] Stunden

##### Task 1.1.1.6 Routenanzeige um alle Folgenden Wegpunkte zu sehen

- Aufwandschätzung: [2] Stunden

##### Task 1.1.1.7 Dokumentation um abgearbeiteten Wegpunkt in einen Routenprotokoll zu vermerken

- Aufwandschätzung: [2] Stunden 

#### Implementable Story 1.1.2 Alarm

> Als Wächter möchte ich einen ALarm auslösen, um Hilfe zu holen.

- Aufwandsschätzung: [50] Story Points
- Akzeptanztests:
  - *TODO (Beschreibung von Testfällen die das erwartete Verhalten der gesamten Implementable Story überprüfen.)*
  - *TODO*
  - *TODO*

##### Task 1.1.2.1 Vibration um den Wächter selbst zu alamieren, falls er einen Wegpunkt nicht in der gegebene Zeit erricht hat

- Aufwandsschätzung: [2] Stunden

##### Task 1.1.2.2 Fehlermeldung (Ersatzfunktion für Servernachricht)

- Aufwandsschätzung: [1] Stunden

##### Task 1.1.2.3 Dokumentation um einen Alarm im Routenprotokoll vermerken zu können

- Aufwandsschätzung: [2] Stunden

## Epic 2 Admin-Modus

> Als Admin möchte ich im Admin Modus Routen und Wächter verwalten können, um die Aufgaben zu planen und auszulesen.

Ausführliche Beschreibung: 
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
  - Eine Route mit Wegpunkt-Objekten erstellen und einem Wächter zuweisen

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

> Als Admin möchte ich das Routenprotokoll erstellen und verwalten können, um den Verlauf der Routen einsehen zu können.

- Aufwandschätzung: [60] Story Points
- Akzeptanztests:
  - Eine Route im Protokoll abspeicher lasen und anzeigen lassen

##### Task 2.2.2.1 Routenprotokoll anzeigen

- Aufwandschätzung: [2] Stunden

##### Task 2.2.2.2 Routeprotokoll bearbeiten

- Aufwandschätzung: [1] Stunden

## Epic 3 Sensoren

> Als System möchte ich die benötigenten Sensoren ansprechen und auslesen können.

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
