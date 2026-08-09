package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZugferdInvoiceWriterTest {

    @Test
    public void shouldWriteEn16931InvoiceToString() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build();

        String xml = writer.writeToString(invoice);

        Assert.assertNotNull(xml);
        Assert.assertFalse(xml.isBlank());
        Assert.assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        Assert.assertTrue(xml.contains("urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:en16931"));
    }

    @Test
    public void shouldWriteEn16931InvoiceToFile() throws IOException {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        Path path = Files.createTempFile("invoice4j-zugferd-", ".xml");
        File file = path.toFile();

        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .prettyPrint(true)
                .build();

        writer.writeToFile(invoice, file);

        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.length() > 0);

        String xml = Files.readString(path);

        Assert.assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        Assert.assertTrue(xml.contains("urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:en16931"));
    }

    @Test
    public void shouldPrettyPrintXml() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .prettyPrint(true)
                .build();

        String xml = writer.writeToString(invoice);

        Assert.assertTrue(xml.contains("\n"));
    }

    @Test
    public void shouldValidateGeneratedXml() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .validateAgainstXsd(true)
                .build();

        String xml = writer.writeToString(invoice);
        Assert.assertNotNull(xml);
    }

    @Test
    public void shouldReturnConfiguredProfile() {
        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build();

        Assert.assertEquals(ZugferdProfile.EN16931, writer.getProfile());
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForString() {
        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build();

        writer.writeToString(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForFile() {
        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build();

        writer.writeToFile(null, new File("invoice.xml"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EN16931)
                .build();

        writer.writeToFile(invoice, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullProfile() {
        ZugferdInvoiceWriter.builder()
                .profile(null);
    }
}