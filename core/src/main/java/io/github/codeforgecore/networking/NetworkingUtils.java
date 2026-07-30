package io.github.codeforgecore.networking;

import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class NetworkingUtils {

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static RequestBody createFileBody(String filePath, String mimeType) {
        return RequestBody.create(new File(filePath), MediaType.parse(mimeType));
    }

    public static RequestBody createStringBody(String string) {
        return RequestBody.create(string, MediaType.parse(MULTIPART_FORM_DATA));
    }

    private static BodyPart createCalendarMessageBodyPart(String data) {
        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setHeader("X-ALT-DESC", "text/html");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(data, "text/calendar")));

            return messageBodyPart;
        } catch (Exception e) {
            return null;
        }
    }

    private static Session createSmtpSession(String username, String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.ionos.de");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private static MimeMessage createMimeMessage(Session session, Multipart multipart, String from,
                                                 String to, String subject) {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setHeader("Content-Class", "urn:content-classes:calendarmessage");
            message.setHeader("X-MimeOLE", "Produced By Microsoft MimeOLE");
            message.setContent(multipart);
            message.saveChanges();

            return message;
        } catch (Exception e) {
            return null;
        }
    }

    public static MimeMessage createOutlookCalendarRequestMessage(String username, String password, String from,
                                                                  String to, String subject, String location,
                                                                  String description, Date begin, Date end) {
        MimeMessage msg = null;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
            ZoneId zoneId = ZoneId.of("Europe/Berlin");
            String dtStart = fmt.format(begin.toInstant().atZone(zoneId));
            String dtEnd = fmt.format(end.toInstant().atZone(zoneId));
            String dtStamp = fmt.format(Instant.now().atZone(zoneId));

            String data = "BEGIN:VCALENDAR\n" +
                    "PRODID:-//Microsoft Corporation//Outlook 19.0 MIMEDIR//EN\n" +
                    "VERSION:2.0\n" +
                    "METHOD:REQUEST\n" +
                    "BEGIN:VEVENT\n" +
                    "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + to + "\n" +
                    "DTSTART;TZID=Europe/Berlin:" + dtStart  + "\n" +
                    "DTEND;TZID=Europe/Berlin:" + dtEnd + "\n" +
                    "DTSTAMP:" + dtStamp + "\n" +
                    "LOCATION:" + location + "\n" +
                    "TRANSP:OPAQUE\n" +
                    "SEQUENCE:0\n" +
                    "UID:" + UUID.randomUUID() + "\n" +
                    // Work around because outlook is ignoring new line characters
                    "DESCRIPTION:" + "Please join this event" + "\n\n" +
                    "X-ALT-DESC;FMTTYPE=text/html:<!doctype html><html><body><p>" +
                    description.replace("\n", "<br>")
                    + "</p></body></html>" + "\n\n" +
                    "SUMMARY:Calendar meeting request\n" +
                    "PRIORITY:5\n" +
                    "CLASS:PUBLIC\n" +
                    "BEGIN:VALARM\n" +
                    "TRIGGER:-PT15M\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Reminder\n" +
                    "END:VALARM\n" +
                    "END:VEVENT\n" +
                    "END:VCALENDAR";

            Session session = createSmtpSession(username, password);

            BodyPart messageBodyPart = createCalendarMessageBodyPart(data);
            Multipart multipart = createMultiPart(messageBodyPart);

            msg = createMimeMessage(session, multipart, from, to, subject);

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return msg;
    }

    public static Message createSimpleTextMail(String username, String password, String from,
                                             String to, String subject, String message) {
        Session session = createSmtpSession(username, password);
        try {
            Message txtMsg = new MimeMessage(session);
            txtMsg.setFrom(new InternetAddress(from));
            txtMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            txtMsg.setSubject(subject);
            txtMsg.setText(message);

            return txtMsg;

        } catch (Exception ignored) {
            return null;
        }
    }

    public static Message createMailWithAttachments(String username, String password, String from,
                                                   String to, String subject, String message, List<File> attachments) {
        Session session = createSmtpSession(username, password);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            Multipart multipart = createMultipart(message, attachments);
            msg.setContent(multipart);

            return msg;

        } catch (Exception ignored) {
            return null;
        }
    }

    private static Multipart createMultipart(String message, List<File> attachments) {
        MimeBodyPart textPart = new MimeBodyPart();
        try {
            textPart.setText(message, "UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            if (attachments != null) {
                for (File file : attachments) {
                    if (file.exists()) {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        attachmentPart.attachFile(file);
                        attachmentPart.setFileName(file.getName());
                        multipart.addBodyPart(attachmentPart);
                    }
                }
            }
            return multipart;

        } catch (Exception e) {
            return null;
        }
    }

    private static Multipart createMultiPart(BodyPart bodyPart) {
        Multipart multipart = new MimeMultipart();
        try {
            multipart.addBodyPart(bodyPart);
            return multipart;
        } catch (MessagingException e) {
            return null;
        }
    }
}
