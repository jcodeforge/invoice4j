package io.github.jcodeforge.invoice4jzugferd.pdf;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.utils.Invoice4jDateTimeUtils;
import io.github.jcodeforge.invoice4jzugferd.ZugferdProfile;

public final class ZugferdXmpMetadata {

    private static final String FACTUR_X_NAMESPACE =
            "urn:factur-x:pdfa:CrossIndustryDocument:invoice:1p0#";

    private static final String PDFA_ID_NAMESPACE =
            "http://www.aiim.org/pdfa/ns/id/";

    private static final String PDFA_EXTENSION_NAMESPACE =
            "http://www.aiim.org/pdfa/ns/extension/";

    private static final String PDFA_SCHEMA_NAMESPACE =
            "http://www.aiim.org/pdfa/ns/schema#";

    private static final String PDFA_PROPERTY_NAMESPACE =
            "http://www.aiim.org/pdfa/ns/property#";

    private static final String PDF_NAMESPACE =
            "http://ns.adobe.com/pdf/1.3/";

    private static final String DC_NAMESPACE =
            "http://purl.org/dc/elements/1.1/";

    private static final String XMP_NAMESPACE =
            "http://ns.adobe.com/xap/1.0/";

    public String create(Invoice invoice, ZugferdProfile profile) {
        String invoiceNumber = escapeXml(invoice.getInvoiceNumber());
        String conformanceLevel = escapeXml(getConformanceLevel(profile));

        return """
                <?xpacket begin="﻿" id="W5M0MpCehiHzreSzNTczkc9d"?>
                <x:xmpmeta xmlns:x="adobe:ns:meta/">

                  <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">

                    <rdf:Description
                        rdf:about=""
                        xmlns:fx="%s"
                        xmlns:pdfaExtension="%s"
                        xmlns:pdfaSchema="%s"
                        xmlns:pdfaProperty="%s"
                        xmlns:xmp="%s"
                        xmlns:pdf="%s"
                        xmlns:dc="%s"
                        xmlns:pdfaid="%s">

                      <fx:DocumentType>INVOICE</fx:DocumentType>
                      <fx:DocumentFileName>factur-x.xml</fx:DocumentFileName>
                      <fx:Version>1.0</fx:Version>
                      <fx:ConformanceLevel>%s</fx:ConformanceLevel>

                      <pdfaExtension:schemas>

                        <rdf:Bag>

                          <rdf:li>

                            <rdf:Description>

                              <pdfaSchema:schema> Factur-X PDFA Extension Schema</pdfaSchema:schema>
                              <pdfaSchema:namespaceURI>%s</pdfaSchema:namespaceURI>
                              <pdfaSchema:prefix>fx</pdfaSchema:prefix>

                              <pdfaSchema:property>

                                <rdf:Seq>

                                  <rdf:li>

                                    <rdf:Description>
                                      <pdfaProperty:name>DocumentFileName</pdfaProperty:name>
                                      <pdfaProperty:valueType>Text</pdfaProperty:valueType>
                                      <pdfaProperty:category>external</pdfaProperty:category>
                                      <pdfaProperty:description>name of the embedded XML invoice file</pdfaProperty:description>
                                    </rdf:Description>

                                  </rdf:li>

                                  <rdf:li>

                                    <rdf:Description>
                                      <pdfaProperty:name>DocumentType</pdfaProperty:name>
                                      <pdfaProperty:valueType>Text</pdfaProperty:valueType>
                                      <pdfaProperty:category>external</pdfaProperty:category>
                                      <pdfaProperty:description>INVOICE</pdfaProperty:description>
                                    </rdf:Description>

                                  </rdf:li>

                                  <rdf:li>

                                    <rdf:Description>
                                      <pdfaProperty:name>Version</pdfaProperty:name>
                                      <pdfaProperty:valueType>Text</pdfaProperty:valueType>
                                      <pdfaProperty:category>external</pdfaProperty:category>
                                      <pdfaProperty:description>The actual version of the ZUGFeRD data</pdfaProperty:description>
                                    </rdf:Description>

                                  </rdf:li>

                                  <rdf:li>

                                    <rdf:Description>
                                      <pdfaProperty:name>ConformanceLevel</pdfaProperty:name>
                                      <pdfaProperty:valueType>Text</pdfaProperty:valueType>
                                      <pdfaProperty:category>external</pdfaProperty:category>
                                      <pdfaProperty:description>The conformance level of the ZUGFeRD data</pdfaProperty:description>
                                    </rdf:Description>

                                  </rdf:li>

                                </rdf:Seq>

                              </pdfaSchema:property>

                            </rdf:Description>

                          </rdf:li>

                        </rdf:Bag>

                      </pdfaExtension:schemas>

                      <xmp:CreateDate>%s</xmp:CreateDate>
                      <xmp:CreatorTool>invoice4j</xmp:CreatorTool>
                      <xmp:ModifyDate>%s</xmp:ModifyDate>
                      <pdf:Producer>invoice4j</pdf:Producer>

                      <dc:title>
                        <rdf:Alt>
                          <rdf:li xml:lang="x-default">Invoice %s</rdf:li>
                        </rdf:Alt>
                      </dc:title>

                      <pdfaid:part>3</pdfaid:part>
                      <pdfaid:conformance>B</pdfaid:conformance>

                    </rdf:Description>

                  </rdf:RDF>

                </x:xmpmeta>
                <?xpacket end="w"?>
                """.formatted(
                FACTUR_X_NAMESPACE,
                PDFA_EXTENSION_NAMESPACE,
                PDFA_SCHEMA_NAMESPACE,
                PDFA_PROPERTY_NAMESPACE,
                XMP_NAMESPACE,
                PDF_NAMESPACE,
                DC_NAMESPACE,
                PDFA_ID_NAMESPACE,
                conformanceLevel,
                FACTUR_X_NAMESPACE,
                Invoice4jDateTimeUtils.getCurrentTimestamp(),
                Invoice4jDateTimeUtils.getCurrentTimestamp(),
                invoiceNumber
        );
    }

    private String getConformanceLevel(ZugferdProfile profile) {
        return switch (profile) {
            case MINIMUM -> "MINIMUM";
            case BASIC_WL -> "BASIC WL";
            case BASIC -> "BASIC";
            case EN16931 -> "EN 16931";
            case EXTENDED -> "EXTENDED";
        };
    }

    private String escapeXml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}