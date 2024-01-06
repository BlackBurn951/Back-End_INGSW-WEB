package com.example.progettowebtest.EmailSender;
import com.google.api.services.gmail.model.Message;
import java.io.*;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static com.example.progettowebtest.EmailSender.EmailService.*;
import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;


@RestController
@CrossOrigin(origins = "*")
public class SendEmailOtpController {
    public static String generatedOTP;


    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody EmailData emailData) {
        String nomeCognome = emailData.getNomeCognome();
        generatedOTP = generateOTP();
        String htmlContent = "<p style=\"text-align: left;\">&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<table border=\"10\" width=\"690\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td style=\"background-color: #91defa; padding: 20px; text-align: center;\">\n" +
                "            <h1 style=\"color: #333333;\">Verifica la tua identit&agrave;</h1>\n" +
                "            <h3 style=\"text-align: left;\"><strong>Gentile " + nomeCognome + "</strong></h3>\n" +
                "            <h4 style=\"text-align: left;\"><strong>Inserisci questo codice sul sito della Banca Caesar Magnus per verificare la tua identit&agrave;</strong></h4>\n" +
                "            <h2><strong>Codice OTP:</strong></h2>\n" +
                "            <h1>" + generatedOTP + "</h1>\n" +
                "            <p style=\"text-align: left;\">&nbsp;</p>\n" +
                "            <h3 style=\"text-align: center;\"><strong>Questo codice scadr&agrave; tra 10 minuti.</strong></h3>\n" +
                "            <p style=\"text-align: left;\"><strong>Se non riconosci l'indirizzo caesar.magnus.info@gmail.com, puoi ignorare questa email.</strong></p>\n" +
                "            <p style=\"text-align: left;\"><strong>Ti preghiamo di non rispondere a questa email.</strong></p>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n";
        try {

            Message message = createMessage(emailData.getSender(), emailData.getTo(), emailData.getSubject(), htmlContent);
            sendMessage(getService(), emailData.getUserId(), message);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }




}