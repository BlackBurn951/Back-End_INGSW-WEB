package com.example.progettowebtest.OtpMail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.util.Date;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class SendMessageWithAttachment {
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);

    public static String generatedOTP;


    static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        InputStream in = SendMessage.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("caesar.magnus.info@gmail.com");
    }


    @PostMapping("/sendEmailWithAttachment")
    public void sendEmail(@RequestBody EmailDataWithAttachment emailDataWithAttachment) {

        String htmlContent = "<div style=\"max-width: 800px; max-height: 900px; margin: 0 auto; padding: 0 20px; background-color: #e9e9e9;\">"+
                "<h2 style=\"text-align: center;\">Documento di apertura del conto</h2\n>" +
                "<p>&nbsp;</p>" +
                " <div id=\"datiDocumento\">\n" +
                " <p><strong>Io sottoscritto/a: "+emailDataWithAttachment.getNomeCognome()+" <span id=\"nome\"></strong></span></p>\n"+
                " <p><strong>Data di nascita: "+emailDataWithAttachment.getDataDiNascita()+" <span id=\"data_nascita\"></span></strong></p>\n"+
                " <p><strong>Residente a: "+emailDataWithAttachment.getIndirizzo()+"<span id=\"indirizzo\"></span></strong></p>\n"+
                " <p><strong>Numero di telefono: "+emailDataWithAttachment.getNumeroTelefono()+" <span id=\"telefono\"></span></strong></p>\n"+
                " <p><strong>Indirizzo email: "+emailDataWithAttachment.getTo()+"<span id=\"email\"></span></strong></p>\n"+
                " <p>&nbsp;</p>"+
                " <p>&nbsp;</p>"+
                " </div>\n"+
                " <div id=\"testoDocumento\">\n"+
                " <p>Con la presente, dichiaro di voler aprire un conto presso la Banca Caesar Magnus e accetto i termini e le condizioni relative all'apertura e all'utilizzo del suddetto conto.</p>\n"+
                " <p>Dichiaro inoltre di fornire informazioni accurate e veritiere in merito ai dati personali sopra indicati.</p>\n"+
                " <p>Autorizzo la Banca Caesar Magnus a utilizzare i dati forniti per finalit&agrave; connesse all'apertura e alla gestione del mio conto bancario.</p>\n"+
                " <p>Dichiaro di aver ricevuto, letto e compreso il regolamento relativo al conto e accetto di conformarmi alle disposizioni in esso contenute.</p>\n"+
                " </div>\n"+
                " <p>&nbsp;</p>"+
                " <p>&nbsp;</p>"+
                " <div id=\"dataEfirma\">\n"+
                " <div id=\"data\">\n"+
                " <p style=\"text-align: left;\"><strong>Data: "+emailDataWithAttachment.getDataFirma()+"</strong></p>\n"+
                " </div>\n"+
                " </div>\n"+
                " </div>\n"+
                " <p>&nbsp;</p>";
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            Gmail service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
                    .setApplicationName("Banca Caesar Magnus")
                    .build();

            PDDocument pdfFile = CreateAccountOpeningPDF.createAccountOpeningPDF(emailDataWithAttachment.getNomeCognome(), emailDataWithAttachment.getDataDiNascita(), emailDataWithAttachment.getIndirizzo(), emailDataWithAttachment.getNumeroTelefono(), emailDataWithAttachment.getTo(), emailDataWithAttachment.getDataFirma());

            Message message = createMessageWithAttachment(htmlContent, emailDataWithAttachment.getSender(), emailDataWithAttachment.getTo(), emailDataWithAttachment.getSubject(), pdfFile);
            sendMessage(service, emailDataWithAttachment.getUserID(), message);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(Gmail service, String userId, Message emailContent) throws IOException {
        com.google.api.services.gmail.model.Message sentMessage = null;
        try {
            sentMessage = service.users().messages().send(userId, emailContent).execute();
            System.out.println("Message Id: " + sentMessage.getId());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
        }
    }

    private Message createMessageWithAttachment(String htmlContent, String sender, String to, String subject, PDDocument file) throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(sender));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(htmlContent, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        file.save(byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        DataSource source = new ByteArrayDataSource(bytes, "application/pdf");
        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName("Documento apertura conto.pdf");
        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        Message message = new Message();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        message.setRaw(encodedEmail);

        return message;
    }



}