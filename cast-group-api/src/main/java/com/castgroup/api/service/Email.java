package com.castgroup.api.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.activation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class Email {

	@Autowired
    private JavaMailSender javaMailSender;

	public void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("wrandrade@stefanini.com", "wandrademg@gmail.com", "wandrademg@hotmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

    }
	
	public void sendEmailWithAttachment(DataSource fds) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("wandrademg@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        helper.addAttachment("rqcode.png", fds);

        javaMailSender.send(msg);

    }
	
}
