# What is this?

Dieses Dokument befasst sich mit den wesentlichen Informationen über das Ausführen von Tests innerhalb 
dieser Anwendung.

## Testen

Dieses Projekt enthält eine 
[Sammlung](https://gitlab.com/FP_Group/winfuhrhub/-/tree/master/app/src/test/java?ref_type=heads) 
von Tests(E2E, Functional, Integration, Unit) in Zusammenspiel 
mit der [JUnit](https://junit.org/) Bibliothek.

## Automatische Tests beim Kompiliieren überspringen

Standardmäßig führt Maven beim Kompiliieren der Release-Version alle Tests aus. Um dieses ungewünschte
Verhalten zu unterbinden ist in der pom.xml auf Paket-Rootebende folgendes hinterlegt:

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.12.4</version>
    <configuration>
        <skipTests>true</skipTests>
    </configuration>
</plugin>
```

## Systemumgebung und Profile beim Testen

Alle Tests sind mit den hinterlegten Maven-Profilen ausführbar. Das ist besonders relevant, wenn man 
Migrationstests ausführt, denn diese haben je nach Systemumgebung/Profil unterschiedliche 
Datenbankanbindungen.  

## Datenbank
Unabhängig von der Systemumgebung(Entwicklung, Produktion...) ist jede Datenbank im lokalen
Benutzerverzeichnis unter `.fpsoft/cache` gespeichert.  
Um diverse Abfragen zu testen, stehen externe als auch integrierte Lösungen zur Verfügung.  
Siehe https://www.datensen.com/blog/sqlite/top-5-tools-for-sqlite/ wobei sich bei der integrierten
Lösung(bei Verwendung der IntelliJ Idea) das Plugin ``Database Navigator`` anbietet.  
Hierfür ist jedoch zusätzlich der Treiber [sqlite-jdbc-crypt
](https://github.com/willena/sqlite-jdbc-crypt/releases) herunterzuladen und
anzugeben(`jdbc:sqlite:path\to\your\db?cipher=chacha20&key=your key`).

Hinweis: Im Produktionsbetrieb ist die Datenbank via sqlcipher(chacha20) verschlüsselt. Dies ist 
bei der Konfiguration der Datenbankverbindung zu berücksichtigen.  
Die Passwörter sind als Klartext im Quellcode hinterlegt und werden mit einem bestimmten
Algorithmus beim Verbindungsaufbau verschlüsselt.