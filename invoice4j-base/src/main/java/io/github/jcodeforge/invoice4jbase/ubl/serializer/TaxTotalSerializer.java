package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Tax;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class TaxTotalSerializer implements XmlSerializer<List<Tax>> {

    private final TaxSerializer taxSerializer = new TaxSerializer();

    @Override
    public void serialize(XmlWriter writer, List<Tax> taxes) {
        if (taxes == null || taxes.isEmpty()) {
            return;
        }

        writer.startElement(
                "cac",
                XmlNamespaces.UBL_CAC,
                "TaxTotal"
        );

        /*
         * TaxTotal/TaxAmount is the total VAT amount.
         *
         * Individual Tax objects contain the VAT amount for
         * their respective tax category.
         */
        Tax firstTax = taxes.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (firstTax != null) {
            writer.startElement(
                    "cbc",
                    XmlNamespaces.UBL_CBC,
                    "TaxAmount"
            );

            writer.writeAttribute(
                    "currencyID",
                    firstTax.getTaxAmount().getCurrency().getCode()
            );

            writer.writeCharacters(
                    calculateTotalTaxAmount(taxes)
            );

            writer.endElement();
        }

        for (Tax tax : taxes) {
            if (tax == null) {
                continue;
            }

            taxSerializer.serialize(writer, tax);
        }

        writer.endElement(); // TaxTotal
    }

    private String calculateTotalTaxAmount(List<Tax> taxes) {
        return taxes.stream()
                .filter(Objects::nonNull)
                .map(tax -> tax.getTaxAmount().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toPlainString();
    }
}