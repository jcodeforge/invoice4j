package zugferd;

import io.github.jcodeforge.invoice4jbase.testfactory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.ZugferdProfile;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZugferdInvoiceEn16931WriterTest {

    private final ZugferdInvoiceWriter SUT = ZugferdInvoiceWriter.builder()
            .profile(ZugferdProfile.EN16931)
            .prettyPrint(true)
            .build();

    @Test
    public void shouldWriteEn16931InvoiceToString() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);

        Assert.assertNotNull(xml);
        Assert.assertFalse(xml.isBlank());
        Assert.assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        Assert.assertTrue(xml.contains("urn:cen.eu:en16931:2017"));
    }

    @Test
    public void shouldWriteEn16931InvoiceToFile() throws IOException {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        Path path = Files.createTempFile("invoice4j-zugferd-", ".xml");
        File file = path.toFile();

        SUT.writeToFile(invoice, file);

        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.length() > 0);

        String xml = Files.readString(path);

        Assert.assertTrue(xml.contains("<rsm:CrossIndustryInvoice"));
        Assert.assertTrue(xml.contains("urn:cen.eu:en16931:2017"));
    }

    @Test
    public void shouldPrettyPrintXml() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());
        String xml = SUT.writeToString(invoice);

        Assert.assertTrue(xml.contains("\n"));
    }

    @Test
    public void shouldValidateGeneratedXml() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());

        String xml = SUT.writeToString(invoice);
        Assert.assertNotNull(xml);
    }

    @Test
    public void shouldReturnConfiguredProfile() {
        Assert.assertEquals(ZugferdProfile.EN16931, SUT.getProfile());
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForString() {
        SUT.writeToString(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullInvoiceForFile() {
        SUT.writeToFile(null, new File("invoice.xml"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullFile() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());
        SUT.writeToFile(invoice, null);
    }
}