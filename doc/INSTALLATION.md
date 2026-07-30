# What is this?

Dieses Dokument befasst sich mit der Verteilung und Installation der Software.

* Table of Contents
    * 1.Requirements
    * 2.Repository klonen
    * 3.Projekt erzeugen

## 1. Requirements

- IntelliJ IDEA Download: https://www.jetbrains.com/idea/download/?section=windows
- Java 23 oder höher: Corretto-23 Releases: https://github.com/corretto/corretto-23/releases
- WiX Toolset v3: Das jpackage-Tool benötigt für die Erstellung von .msi- und .exe-Dateien zwingend das WiX Toolset 
(Version 3.x). Ohne dieses Tool bricht der Maven-Build mit einem Fehler ab. https://github.com/wixtoolset/wix3/releases
- Damit Maven und jpackage das Toolset automatisch finden, muss man den bin-Ordner von WiX in den Windows-Systempfad eintragen 


## 2. Repository klonen

Das Projekt klont man idealerweise direkt über die IDE unter der Url via https:
https://gitlab.com/FP_Group/winfuhrhub.git

## 3. Projekt erzeugen
Für das kompiliieren des Projektes bzw. das Bereitstellen der Artefakte verwendet man das
Build-Werkzeug Maven.  
Dieser Prozess unterliegt dabei dem Maven-Lifecycle, welcher vollständig in die IntelliJ IDEA integriert
und unter dem Maven-Tool Fenster erreichbar ist.
