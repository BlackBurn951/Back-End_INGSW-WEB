package com.example.progettowebtest.OtpMail;

import com.example.progettowebtest.OtpMail.EmailData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailController(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/send")
    public String inviaEmail(@RequestBody EmailData emailData) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(emailData.getDestinatario());
            helper.setSubject(emailData.getOggetto());
            helper.setText(emailData.getTesto());

            emailSender.send(message);
            return "Email inviata con successo!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Errore durante l'invio dell'email: " + e.getMessage();
        }
    }
}
