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
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class SendMessage {
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);


    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
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


    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody EmailData emailData) {
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            Gmail service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
                    .setApplicationName("Banca Caesar Magnus")
                    .build();
            Message message = createMessage(emailData.getSender(), emailData.getTo(), emailData.getSubject(), emailData.getMessageText());
            sendMessage(service, emailData.getUserId(), message);
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

    public static Message createMessage(String sender, String to, String subject, String messageText) throws MessagingException, IOException {
        javax.mail.internet.MimeMessage email = new javax.mail.internet.MimeMessage(Session.getDefaultInstance(new Properties(), null));
        email.setFrom(new InternetAddress(sender));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(messageText);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);

        byte[] bytes = buffer.toByteArray();
        String encodedEmail = com.google.api.client.util.Base64.encodeBase64URLSafeString(bytes);

        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}