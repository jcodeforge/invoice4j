package io.github.jcodeforge.core.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class UtilMethods {

    // Constants to configure PDF creation
    private final static int DEFAULT_DOCUMENT_PAGE_FONT_SIZE = 14;
    private final static int DOCUMENT_PAGE_TEXT_WIDTH_MAX = 500;
    private final static int DOCUMENT_PAGE_MARGIN = 50;
    private final static int DOCUMENT_PAGE_HEADER_OFFSET = 100;
    private final static int DOCUMENT_PAGE_OFFSET_X = 60;

    public static Properties loadProperties(String resource) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = UtilMethods.class.getClassLoader().getResourceAsStream(resource);
            if (inputStream != null) {
                properties.load(inputStream);
            }

            return properties;

        } catch (Exception e) {
            return properties;
        }
    }

    public static ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon != null) {
            return new ImageIcon(icon.getImage().getScaledInstance(width, height,
                    Image.SCALE_SMOOTH));
        }

        return null;
    }

    public static String writeToPdfDocument(String text, boolean printHeader, String path, String fileName) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDRectangle mediaBox = page.getMediaBox();

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            if (printHeader) {
                drawDocumentPageHeader(document, page, contentStream);
            }

            float headerOffset = printHeader ? DOCUMENT_PAGE_HEADER_OFFSET : 0;
            float startY = mediaBox.getHeight() - DOCUMENT_PAGE_MARGIN - headerOffset;
            float posY = startY;
            float leading = DEFAULT_DOCUMENT_PAGE_FONT_SIZE * 2;

            contentStream.beginText();
            contentStream.setFont(font, DEFAULT_DOCUMENT_PAGE_FONT_SIZE);
            contentStream.newLineAtOffset(DOCUMENT_PAGE_OFFSET_X,
                    mediaBox.getHeight() - DOCUMENT_PAGE_MARGIN - headerOffset);
            contentStream.setLeading(leading);

            for (String line : text.split("\n")) {
                List<String> wrappedLines = wrapLineByMaxWidth(line, font, DEFAULT_DOCUMENT_PAGE_FONT_SIZE,
                        DOCUMENT_PAGE_TEXT_WIDTH_MAX);

                for (String wrappedLine : wrappedLines) {
                    if (posY <= DOCUMENT_PAGE_MARGIN) {
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage();
                        document.addPage(page);
                        mediaBox = page.getMediaBox();

                        contentStream = new PDPageContentStream(document, page);

                        if (printHeader) {
                            drawDocumentPageHeader(document, page, contentStream);
                        }

                        contentStream.beginText();
                        contentStream.setFont(font, DEFAULT_DOCUMENT_PAGE_FONT_SIZE);
                        contentStream.newLineAtOffset(DOCUMENT_PAGE_OFFSET_X,
                                mediaBox.getHeight() - DOCUMENT_PAGE_MARGIN - headerOffset);
                        contentStream.setLeading(leading);

                        posY = startY;
                    }

                    contentStream.showText(wrappedLine);
                    contentStream.newLine();
                    posY -= leading;
                }
            }

            contentStream.endText();
            contentStream.close();

            File doc = new File(path, fileName + ".pdf");
            document.save(doc);

            return doc.getAbsolutePath();

        } catch (Exception e) {
            return null;
        }
    }

    private static List<String> wrapLineByMaxWidth(String text, PDFont font, float fontSize, float maxWidth) {
        List<String> lines = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            lines.add("");
            return lines;
        }

        try {
            String[] words = text.split("\\s+");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;

                float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

                if (textWidth <= maxWidth) {
                    currentLine.setLength(0);
                    currentLine.append(testLine);

                } else {
                    if (!currentLine.isEmpty()) {
                        lines.add(currentLine.toString());
                    }

                    if (font.getStringWidth(word) / 1000 * fontSize > maxWidth) {
                        lines.addAll(splitLongWord(word, font, fontSize, maxWidth));
                        currentLine.setLength(0);
                    } else {
                        currentLine.setLength(0);
                        currentLine.append(word);
                    }
                }
            }

            if (!currentLine.isEmpty()) {
                lines.add(currentLine.toString());
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }

        return lines;
    }

    private static List<String> splitLongWord(String word, PDFont font, float fontSize, float maxWidth) {
        List<String> parts = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        try {
            for (char c : word.toCharArray()) {
                String test = builder.toString() + c;
                float width = font.getStringWidth(test) / 1000 * fontSize;

                if (width <= maxWidth) {
                    builder.append(c);
                } else {
                    parts.add(builder.toString());
                    builder.setLength(0);
                    builder.append(c);
                }
            }

            if (!builder.isEmpty()) {
                parts.add(builder.toString());
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }

        return parts;
    }

    private static void drawDocumentPageHeader(PDDocument document, PDPage page, PDPageContentStream contentStream) {
        try (InputStream is = ClassLoader.getSystemResourceAsStream("icn_fuhrpark.png")) {
            PDImageXObject image = PDImageXObject.createFromByteArray(document, is.readAllBytes(), null);

            float aspectRatio = (float) image.getWidth() / image.getHeight();
            float imageWidth = 200;
            float imageHeight = imageWidth / aspectRatio;

            float pageWidth = page.getMediaBox().getWidth();
            float x = (pageWidth - imageWidth) / 2;
            float y = page.getMediaBox().getHeight() - imageHeight - 20;

            contentStream.drawImage(image, x, y, imageWidth, imageHeight);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
