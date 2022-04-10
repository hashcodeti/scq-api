/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.services;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ScqMailSupportService {

    public void sendMail(String recipientAddress, String subject, String bodyMessage) {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtpout.secureserver.net");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.mime.charset", "ISO-8859-1");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("scq.supporte@cationbrasil.com.br", "SCQ_%Sup!@#");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("scq.supporte@cationbrasil.com.br"));

            Address[] toUser = InternetAddress.parse(recipientAddress);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(subject);
            message.setText(bodyMessage + "\r\n");
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMail(String recipientAddress, String subject, String bodyMessage, String confirmationUrl,
            String appUrl, File file) throws MessagingException {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtpout.secureserver.net");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.mime.charset", "ISO-8859-1");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("scq.supporte@cationbrasil.com.br", "SCQ_%Sup!@#");
            }
        });
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        if (file != null) {
            // Fill the message
            messageBodyPart.setText(bodyMessage);

            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file.getName());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
        }
        // Create the message part

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("scq.supporte@cationbrasil.com.br"));

            Address[] toUser = InternetAddress.parse(recipientAddress);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(subject);
            message.setText(bodyMessage + "\r\n" + confirmationUrl);
            if (file != null) {
                message.setContent(multipart);
            }

            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
