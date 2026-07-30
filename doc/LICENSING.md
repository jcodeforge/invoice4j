# What is this?

Dieses Dokument soll alle notwendigen Informationen enthalten, die man benötigt, um mit dieser 
Software Lizenzen erstellen und signieren zu können.  
 
## Welche Bestandteile hat eine Lizenz?

Eine [Lizenz](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/license/src/main/java/de/fuhrpark_software/licensing/schemes/License.java?ref_type=heads) 
besteht hauptsächlich aus Informationen, die den Lizenzinhaber, den Client/Mandanten und sämltiche 
Lizenzbestandteile eindeutig identifiziert.  

Lizenzbestandteile sind z.B: 

- Eine Reihe von [Zusatzmodulen](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/license/src/main/java/de/fuhrpark_software/licensing/LicenseConstants.java?ref_type=heads) welche ebenfalls Abhängigkeiten besitzen
- Gültigkeit-/Ablaufdatumswerte
- Lizenzausteller
- Produktschlüssel  

Siehe: [sample.lic](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/license/src/main/resources/sample.lic?ref_type=heads)

## Signierung? Warum signieren?

Eine digitale Signatur ist ein kryptografisches Verfahren, dass auf asymmetrischer Verschlüsselung
basiert und zur Sicherung digitaler Informationen dient.

Software-Lizenzen werden signiert, um die Authentizität, die Integrität und die
Vertrauenswürdigkeit der Software zu gewährleisten.  
Die digitale Signatur, bestätigt dem Nutzer 
und Herausgeber, dass die Software vom erwarteten Herausgeber stammt und während der Distribution 
nicht manipuliert wurde.  

## Wie funktioniert eine digitale Signatur?

### Erstellung

Wir als Softwarehersteller erzeugen im Rahmen der Softwareverteilung ein Schlüsselpaar.
(Privater-/öffentlicher Schlüssel)  
Wir verwenden hierfür einen speziellen Algorythmus
(siehe: [RSAKeyPairGenerator](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/license/src/main/java/de/fuhrpark_software/licensing/encryption/RSAKeyPairGenerator.java?ref_type=heads)).
Weiterhin ermitteln wir eine eindeutige Prüfsumme (Hash-Wert) der Informationen(Lizenzdaten).
Siehe [KeyFileUtils](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/license/src/main/java/de/fuhrpark_software/licensing/encryption/KeyFileUtils.java?ref_type=heads)  
Dieser Hash-Wert wird mit dem erzeugten privaten Schlüssel verschlüsselt und bildet die digitale Signatur.  

### Überprüfung

Der Empfänger verwendet den erzeugten 
[öffentlichen Schlüssel](https://gitlab.com/FP_Group/winfuhrhub/-/blob/master/license/src/main/resources/id_rsa.public?ref_type=heads),
um die digitale Signatur zu entschlüsseln.

### Vergleich

Gleichzeitig berechnet der Empfänger den Hash-Wert des empfangenen Dokuments erneut und vergleicht ihn
mit dem entschlüsselten Hash-Wert.

### Ergebnis

Stimmen beide Werte überein, ist die digitale Signatur gültig, was beweist, dass die 
Informationen nicht manipuliert wurden und zum Besitzer des privaten Schlüssels gehören.  

## Aufbewahren des privaten und öffentlichen Schlüssels

Der private Schlüssel ist geheim zu halten und sollte nicht im Versionierungssystem gespeichert sein.
Der öffentliche Schlüssel ist von jedem einsehbar und sollte optimalerweise von einem öffentlichen
Speicherort abrufbar sein.

