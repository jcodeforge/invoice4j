package cii;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import io.github.jcodeforge.invoice4jbase.cii.CiiEn16931XsdValidator;
import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceWriter;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CiiEn16931XsdValidatorTest {

    private final CiiEn16931XsdValidator SUT = new CiiEn16931XsdValidator();

    @Test
    public void shouldValidateMinimalInvoice() throws IOException {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        Path path = Files.createTempFile("invoice4j-", ".xml");
        File file = path.toFile();

        CiiInvoiceWriter.builder()
                .prettyPrint(true)
                .build()
                .writeToFile(invoice, file);

        SUT.validate(file);
    }

    @Test
    public void shouldValidateCompleteInvoice() throws IOException {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        Path path = Files.createTempFile("invoice4j-", ".xml");
        File file = path.toFile();

        CiiInvoiceWriter.builder()
                .prettyPrint(true)
                .build()
                .writeToFile(invoice, file);

        SUT.validate(file);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidXml() {
        String xml = "<invalid/>";

        InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        SUT.validate(in);
    }

    @Test(expected = XsdValidationException.class)
    public void shouldRejectInvalidDocument() {
        String xml = """
        <rsm:CrossIndustryInvoice
            xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"/>
        """;

        InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        SUT.validate(in);
    }

    @Test
    public void shouldValidateFromString() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = CiiInvoiceWriter.builder()
                .build()
                .writeToString(invoice);

        SUT.validate(xml);
    }

    @Test
    public void shouldValidateFromInputStream() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = CiiInvoiceWriter.builder()
                .build()
                .writeToString(invoice);

        InputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        SUT.validate(in);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        SUT.validate((File) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInputStream() {
        SUT.validate((InputStream) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullString() {
        SUT.validate((String) null);
    }

}
