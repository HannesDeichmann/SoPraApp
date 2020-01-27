# App-Name Sopra Team 8

| Screenshot of  of the Admin Mode                                      | Screenshot of  of the Guard Mode                                      |
| ------                                                                | ------                                                                |
| ![Screenshot of the Admin Mode](doc/images/AdminMode_Screenshot.jpg)  | ![Screenshot of the Guard Mode](doc/images/GuardMode_Screenshot.jpg)  |

    
                                        




Mit dieser App kann man eigene Wachrundgänge erstellen, den Wärtern Routen zuweisen und die gemachten Routen überprüfen.

## Features

**TODO:** Additional Features

## Installation

1. Repository klonen: `git clone`
2. Android Studio Projekt öffnen
3. Android Studio Projekt bauen

## Verwendung der App

Um den Guarde Mode zu starten: im Admin Modus unter 'Guards' einen Neuen Guard erstellen, wo man das Passwort setzten kann. 
Die ID des neuen Guards zur Anmeldung steht oben in der Guard erstellung.

Um den Admin Mode zu starten: Login mit Daten:  
*   Benutzername:   admin
*   Passwort:       admin
                         
"Standard ablauf":
- Guards erstellen um den Guard Mode nutzen zu können und Routen zuweisen zu können.
- Waypoints erstellen:
    - Daten eingeben
    - NFC Tag dran halten und auf assign Waypoint clicken um Tag zu beschreiben
    - auf add Location wenn man will eine Karte hochladen aus der Galerie und Ort des Wp. anklicken
    - mit accept speichern
- Route erstellen:
    - alle gewüschten Wegpunkte hinzufügen und speichern
    - ggf. durch Wp in der list anklicken den Punkt ersetzten oder über cancel löschen
- über Schedule die Routen den Guards zuweisen, alle zugewiesenen Routen erscheinen in der liste von dem Guard

- Guard mode starten indem man sich mit den in der Guard creation gewählten Daten einloggt
- Route auswählen und Tags der Reihe nach scannen bis man fertig ist

- erneut in den Admin modus gehen und über Protcoll das Protokoll einsehen und ggf. exportieren
- (wird in den eigenen Dateinen des Geräts gespeichert... über Toast wird Ort zusätzlich angezeit)

### Wichtiger Anwendungsfall 1

Routen können geplant werden, indem man verfügbare Wegpunkte sieht und diese 
untereinander verbinden kann mit der jeweils benötigten Zeit, um von A nach B
zu kommen.

### Wichtiger Anwendungsfall 2

Der Wächter bekommt Informationen darüber, welche Routen er wann ablaufen 
soll und kann seine geleistete Arbeit dokumentieren

## Changelog

Die Entwicklungsgeschichte befindet sich in [CHANGELOG.md](CHANGELOG.md).

## Verwendete Bibliotheken

Bisher keine.

## Lizenz

Genaue Bedingungen der Lizenz können in [LICENSE](LICENSE) nachgelesen werden.