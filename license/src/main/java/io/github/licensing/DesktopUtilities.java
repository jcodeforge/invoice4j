package io.github.licensing;

public final class DesktopUtilities {

    public static String getLicenseCertificateContent(String customer, String productId,
                                                      String referenceId, String productKey, String createdAt,
                                                      String validAfterDate, String expiringDate,
                                                      int numberOfLicenses, int numberOfVehicles) {
        return String.format("""
                        Fuhrpark Software GmbH Produktzertifikat
                        
                        WICHTIG:
                        Diese Nachricht dient zur Zertifizierung und zum ordnungsgemäßen Gebrauch
                        von Fuhrpark Software Produkten.
                        Bitte speichern Sie diese Nachricht als Referenz für die zukünftige Verwendung.
                        
                        LIZENZDETAILS
                        
                        Lizenz: %s
                        Produktschlüssel: %s
                        Lizenznehmer: %s
                        Produkt: %s
                        Aussteller: Fuhrpark Software GmbH
                        Ausstellungsdatum: %s
                        Gültig ab: %s
                        Gültig bis: %s
                        Anzahl Arbeitsplätze: %d
                        Anzahl Fahrzeuge: %d
                        
                        AKTIVIERUNG DER SOFTWARE
                        
                        Diese Software wird elektronisch bereitgestellt und ist verfügbar unter:
                        https://www.fuhrpark-software.de/download-center
                        
                        Bitte besuchen Sie folgende Webseite für Informationen zur Aktivierung:
                        https://www.fuhrpark-software.de/
                        """,
                referenceId,
                productKey,
                customer,
                productId,
                createdAt,
                validAfterDate,
                expiringDate,
                numberOfLicenses,
                numberOfVehicles
        );
    }
}
