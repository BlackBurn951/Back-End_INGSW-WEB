package com.example.progettowebtest.EmailSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EmailTemplateLoader {
    public static String loadEmailTemplate(String fileName) throws IOException {
        InputStream inputStream = EmailTemplateLoader.class.getResourceAsStream(fileName);
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                return stringBuilder.toString();
            }
        } else {
            throw new IOException("File " + fileName + " not found");
        }
    }
}
