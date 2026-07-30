# POV

Dieses Projekt enthält eine Sammlung mehrerer, oft unabhängiger voneinander
entwickelten Projekte, mit einer gemeinsamen Java-Codebasis. 

Für weitere Deails siehe: https://www.miragon.io/blog/was-ist-ein-monorepo/

### Dokumenten und Ressourcen

- [Installation](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/doc/INSTALLATION.md?ref_type=heads)
- [Lizenzierung](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/doc/LICENSING.md?ref_type=heads)
- [Tests](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/doc/TESTING.md?ref_type=heads)

## Lokale Build-Einrichtung (Maven Settings)

Um sensible API-Keys und Deployment-Passwörter lokal zu schützen, nutzt dieses Projekt eine entkoppelte Einstellungsdatei.

1. Kopieren Sie die Datei `maven-settings.xml.template` im Hauptverzeichnis.
2. Benennen Sie die Kopie um in `maven-settings.xml`.
3. Öffnen Sie die neue Datei und tragen Sie Ihre SFTP-Zugangsdaten sowie die API-Keys für `dev` und `prod` ein.  

### Build ausführen
Übergeben Sie beim Maven-Aufruf die lokale Einstellungsdatei mit dem `-s` Parameter:

* **Dev-Build:** `mvn clean package -Pdev -s maven-settings.xml`
* **Prod-Deployment:** `mvn clean deploy -Pprod -s maven-settings.xml`

### Integration in IntelliJ IDEA
Damit Sie den `-s`-Parameter nicht manuell eintippen müssen:
1. Öffnen Sie **Settings** (`Strg + Alt + S`) -> **Build, Execution, Deployment** -> **Build Tools** -> **Maven**.
2. Aktivieren Sie bei **User settings file** die Option **Override**.
3. Wählen Sie den Pfad zur neu erstellten `maven-settings.xml` in Ihrem Projektordner aus.

## Lokale Build-Einrichtung (Windows)

Maven erstellt im Rahmen des Build-Prozesses ein MSI-Installationspaket, das anschließend an Endanwender verteilt
werden kann. Für eine vertrauenswürdige Installation unter Windows ist es erforderlich, das erzeugte MSI digital zu signieren.  
Dies erfolgt mit dem Tool `signtool.exe`, das Bestandteil des Windows SDK ist.  

Das Tool `signtool.exe` ist mit dem Windows SDK vorinstalliert und befindet sich typischerweise unter:
`C:\Program Files (x86)\Windows Kits\10\bin\<SDK-Version>\x64`.  
Damit `signtool.exe` systemweit verfügbar ist, sollte man dieses Verzeichnis als PATH-Umgebungsvariable bereitstellen.  
Dadurch kann das Tool ohne vollständige Pfadangabe aus Build-Systemen wie Maven aufrufen.

