# Wachmann APP
## Grundidee
Ein Smartphone soll dazu genutzt werden, einen Wachmann auf seinen Patrouillen zu unterstützen, bzw. sicherzustellen, dass der Wachmann den vorgesehenen Weg zur geplanten Zeit auch abläuft. 
Abweichungen vom erwarteten Weg sollen aufgedeckt werden, und ggf. Alarm ausgelöst werden.
Zur Überwachung des Weges werden auf diesem „Wegmarken“ festgelegt, die von den Sensoren des Smartphones des Wachmanns erfasst werden können (NFC-Tags, oder Barcodes zum Abscannen).
Die Patrouillen sollen auch auf einfache Weise ausgewertet werden können (über Patrouillen-Protokolle).

Es gibt also zwei Modi in der App:

### Wachmann-Modus
Der Wachmann nutzt die App, um die Patrouillen, Wegmarken und Zeiten zu erfahren, und dann zum richtigen Zeitpunkt dort zu sein und die jeweilige Wegmarke abzuscannen.

### Admin-Modus
Der Admin nutzt die App, um Wegmarken und Patrouillen (Liste von Wegmarken) sowie Zeitfenster zu erfassen. Außerdem kann er hier Protokolle der Patrouillen einsehen. Idealerweise wird er auch alarmiert, wenn ein Wachmann vom erwarteten Weg abweicht oder etwas Unvorhergesehenes passiert.

## Critical Features
### Erfassung von Wegmarken und Bilden von Patrouillen (Admin-Modus)
> Als Admin möchte ich Wegmarken verwalten, und diese wiederverwendbar in verschiedenen Kombinationen zu Patrouillen gruppieren.

- Aufwandsschätzung: *TODO*
- Akzeptanztests:
  - [X] Wegmarken können erstellt werden.
  - [ ] Wegmarken haben Namen, Position, ID/Barcode, Bemerkung
  - [ ] Wegmarken können bearbeitet und gelöscht werden. 
  - [ ] Patrouillen können aus beliebig vielen Wegmarken erstellt werden. 
  - [ ] Patrouillen können bearbeitet und gelöscht werden. 
  - [ ] Nach vorhandenen Wegmarken und Patrouillen kann gesucht werden. 


### Planen von Zeitfenster für Patrouillen/Wegmarken (Admin-Modus)
> Als Admin möchte ich Zeiten/Zeitfenster für Patrouillen festlegen können.

* Patrouille 1: 14:00 Wegmarke A, 14:03 Wegmarke B, 14:07 Wegmarke C
* Patrouille 2: 14:37 Wegmarke C, 14:40 Wegmarke B, 14:43 Wegmarke A

- Aufwandsschätzung: *TODO*
- Akzeptanztests:
  - [ ] Zeitfenster/Zeiten können erstellt werden.
  - [ ] Zeitfenster/Zeiten können bearbeitet und gelöscht werden. 
  - [ ] Nach vorhandenen Zeitfenstern/Zeiten kann gesucht werden. 
  - [ ] Zeitfenster/Zeiten können in einer Patrouille den Wegpunkten zugeordnet werden. 
 

### Auswertung von Patrouillen-Protokollen (Admin-Modus)
> Als Admin möchte ich das Protokoll jeder Patrouille einsehen können, mit Soll- und Ist-Zeiten der Scans der Wegmarken.

- Aufwandsschätzung: *TODO*
- Akzeptanztests:
  - [ ] Es gibt eine Übersicht über abgelaufene Patrouillen.
  - [ ] Für jede Patrouille gibt es eine Übersicht (chronologisch) über die Gesamtdauer und die abgelaufenen (und verpassten) Wegmarken (mit Soll- und ist Zeit).
  - [ ] Komplette Patrouillen-Parotokolle können gelöscht werden (ggf. mit Mehrfach-Selektion).
  - [ ] Einzelne Daten, insbesondere Ist-Zeiten der Scans, können nicht gelöscht werden. 
  

### Ablaufen und Abhaken von Patrouillen (Wachmann-Modus)
> Als Wachmann möchte ich eine einfach zu bedienende App, die mir auf meiner Patrouille die nächste Wegmarke anzeigt.

- Aufwandsschätzung: *TODO*
- Akzeptanztests:
  - [ ] Nach Beginn eine Patrouille wird mir die nächste Wegmarke angezeigt: Name, Position, Bemerkung sowie den Zeitpunkt, an dem ich dort scannen sollte.
  - [ ] Nach Erreichen einer Wegmarke wird nach dem Scannen automatisch die nächste Wegmarke angezeigt.
  - [ ] Es kann jederzeit die gesamte Patrouille angezeigt werden, um Pausen besser einplanen zu können. 
 

