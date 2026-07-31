package io.github.jcodeforge.invoice4jbase.utils;

import io.github.jcodeforge.core.utils.FileUtils;
import io.github.jcodeforge.core.utils.StringUtils;
import jakarta.xml.bind.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collections;

public abstract class XmlUtils {

    public static <T> T unmarshal(Class<T> clazz, String xmlFile, Schema schema) throws UnmarshalException,
            FileNotFoundException {
        Path localFilePath = Paths.get(xmlFile);
        if (!Files.exists(localFilePath)) {
            throw new FileNotFoundException();
        }

        try {
            String content = StringUtils.readAllBytes(localFilePath);

            InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(inputStream);

            SchemaValidationEventHandler eventHandler = new SchemaValidationEventHandler();

            Unmarshaller unmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();
            unmarshaller.setEventHandler(eventHandler);
            unmarshaller.setSchema(schema);

            JAXBElement<T> element = unmarshaller.unmarshal(eventReader, clazz);

            return element.getValue();

        } catch (XMLStreamException e) {
            throw new UnmarshalException(e.getMessage());
        } catch (JAXBException e) {
            throw new UnmarshalException(e.getCause().getMessage());
        }
    }

    private static Document marshal(JAXBElement<?> element, Schema schema, ValidationEventHandler eventHandler)
            throws MarshalException {
        try {
            JAXBContext context = JAXBContext.newInstance(element.getDeclaredType());

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setSchema(schema);
            marshaller.setEventHandler(eventHandler);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            Document doc = factory.newDocumentBuilder().newDocument();
            marshaller.marshal(element, doc);

            return doc;

        } catch (Exception e) {
            throw new MarshalException(e);
        }
    }

    public static void signPKCS12(String xmlFile, String outputFile, String certFile, char[] passphrase)  {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);

            Document doc = builderFactory.newDocumentBuilder().parse(new File(xmlFile));

            // Check if the document is already signed
            if (isXMLSigned(doc)) {
                FileUtils.copyFile(new File(xmlFile), new File(outputFile));
                return;
            }

            KeyStore ks = KeyStore.getInstance("PKCS12");

            try (FileInputStream fis = new FileInputStream(certFile)) {
                ks.load(fis, passphrase);
            }

            String alias = ks.aliases().nextElement();

            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, passphrase);
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

            XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");

            Reference ref = factory.newReference("",
                    factory.newDigestMethod(DigestMethod.SHA256, null),
                    Collections.singletonList(factory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                    null,
                    null
            );

            SignedInfo signedInfo = factory.newSignedInfo(
                    factory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                    factory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
                    Collections.singletonList(ref));

            KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();
            X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(cert));
            KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));

            DOMSignContext signContext = new DOMSignContext(privateKey, doc.getDocumentElement());
            XMLSignature signature = factory.newXMLSignature(signedInfo, keyInfo);
            signature.sign(signContext);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                transformer.transform(new DOMSource(doc), new StreamResult(fos));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isXMLSigned(Document doc) {
        NodeList signatureNodes = doc.getElementsByTagNameNS(XMLSignature.XMLNS,
                "Signature");
        return signatureNodes.getLength() > 0;
    }
}
