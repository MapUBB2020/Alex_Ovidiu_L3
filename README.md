# Hausaufgabe_3  Deadline 7-te Woche - 18 November 2020

Eine Universität braucht eine kleine Anwendung, an der sich Studierende für einen bestimmten Kurs anmelden können. Jeder Kurs hat einen Namen, einen Lehrer, eine maximale Anzahl von Schülern, eine Liste der eingeschriebenen Schüler und die Anzahl der Credits. Jeder Student ist eine Person, die auch einen Studentenausweis, eine Anzahl von eingeschriebenen Credits und eine Liste von eingeschriebenen Kurse hat. 
 
Erstellen Sie eine Anwendung, mit der Sie Informationen zum Kurs und zu den Teilnehmern hinzufügen, löschen, ändern und abrufen können.
Die Anwendung ermöglicht es den Studenten, sich für einen bestimmten Kurs anzumelden. Wenn für den Kurs keine Plätze verfügbar sind, erhält der Student eine Warnmeldung und muss einen anderen Kurs aus der Liste der verfügbaren auswählen.
Mit der Anwendung können Sie die Kurse, die verfügbaren Plätze haben und deren Anzahl anzeigen;
Die Anwendung ermöglicht die Anzeige von Studenten, die sich für einen bestimmten Kurs angemeldet haben;
Mit der Anwendung können Sie alle verfügbaren Kurse anzeigen;
Jeder Student kann Kurse mit bis zu 30 Credits wählen. Wenn der Student einen Kurs auswählen möchte und die Anzahl der Credits überschritten wird, erhält er eine Fehlermeldung und der Kurs wird nicht zu seiner Kursliste hinzugefügt.
Jede Änderung der Anzahl der Credits eines Kurses wirkt sich implizit auf die Gesamtanzahl der Credits aus, die ein Student hat, wenn er in diesem Kurs eingeschrieben ist.
Der Lehrer kann den Kurs, den er unterrichtet, löschen, und die Studenten werden aus der Liste dieses Kurses gelöscht.
 
Die Anwendung berücksichtigt die ModelViewController-Muster. Die erforderlichen Repositories befinden sich in Memory (Listen, die zuerst direkt aus dem Code geladen wurden, bevor das Programm ausgeführt wird) und implementieren die generische IRepository-Schnittstelle (siehe Anhang).
Das Modelldiagramm stellt nur einen Ausgangspunkt dar, das Modell kann nach Bedarf umgebaut werden. 

![uml diagram](https://github.com/MapUBB2020/Hausaufgabe_3/blob/main/UML_Diagram.png?raw=true)

Hausaufgabe:

Mit der Verwendung von lab3Basic.zip Projekt, lösen Sie folgendes:

Modellieren Sie diese Anwendung und implementieren Sie die Logik bis Repository Layer: Implementierung Model, Implementierung InMemoryRepository von ICrudRepository für alle notwendige Klassen;

Funktionalität testen durch JUnits;

Javadocs generieren;


----------------------------------------------------------------------------------------------------------------
Herausforderungen:
----------------------------------------------------------------------------------------------------------------

Getters und Setters für alle model Klassen

Collections Framework

Implementieren des MVC-Musters

JUnits
