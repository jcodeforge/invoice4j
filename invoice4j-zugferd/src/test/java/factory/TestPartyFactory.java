package factory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.*;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.*;
import java.time.LocalDate;

public final class TestPartyFactory {

    public static Seller createSeller() {
        return Seller.builder()
                .name("Müller & Söhne GmbH")
                .tradingName("JCodeForge")
                .vatIdentifier(
                        TaxIdentifier.builder()
                                .value("DE123456789")
                                .type(TaxRegistrationScheme.VAT_REGISTRATION_NUMBER)
                                .build()
                )
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .addIdentifier(createPartyIdentifier())
                .build();
    }

    public static Buyer createBuyer() {
        return Buyer.builder()
                .name("Müller & Söhne GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .build();
    }

    public static Payee createPayee() {
        return Payee.builder()
                .name("JCodeForge GmbH")
                .tradingName("JCodeForge")
                .address(createAddress())
                .electronicAddress(createElectronicAddress())
                .contact(createContact())
                .addIdentifier(createPartyIdentifier())
                .build();
    }

    public static ShipTo createShipTo() {
        return ShipTo.builder()
                .name("Warehouse Leipzig")
                .address(TestPartyFactory.createAddress())
                .build();
    }

    public static PartyIdentifier createPartyIdentifier() {
        return PartyIdentifier.builder()
                .value("1234567890123")
                .scheme(IdentifierScheme.GLN)
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .street("Example Street 1")
                .city("Leipzig")
                .postcode("04109")
                .countryCode(CountryCode.DE)
                .build();
    }

    private static Contact createContact() {
        return Contact.builder()
                .name("Müller & Söhne GmbH")
                .email("info@example.com")
                .telephone("+49 341 123456")
                .build();
    }

    private static ElectronicAddress createElectronicAddress() {
        return ElectronicAddress.builder()
                .scheme(IdentifierScheme.EMAIL)
                .value("info@example.com")
                .build();
    }

    public static InvoicePeriod createInvoicePeriod() {
        return InvoicePeriod.builder()
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .build();
    }
}