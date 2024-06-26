package com.agrotech.usersecurity.services;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.agrotech.usersecurity.entities.Usuario;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.security.active-user.link}")
    private String activeLink;

    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    
    @Async
    public void sendEmail(String email, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        
        helper.setFrom("wfmzipi@gmail.com");
        helper.setTo(email);
        
        helper.setSubject(subject);
        helper.setText(content, true);
        
        javaMailSender.send(message);

    }

    public void sendEmailRegister(Usuario usuario, String uuid) throws MessagingException, UnsupportedEncodingException {

        
        String subject = "Confirmação de Cadastro - Cotação de Preço";
        String content = "<p>Olá "+ usuario.getUsername()+ ",</p><br><br><br>"+
                          "<p>Você está recebendo esse e-mail para a efetivação do seu cadastro no nosso sistema de cotação de preço.<BR> "+
                          "Click no link abaixo para ativar o seu email."+
                          "<p><a href='"+activeLink+"email="+ usuario.getEmail()+"&uuid="+uuid+"'>Ativar Email</p>";
        
        sendEmail( usuario.getEmail(),  subject,  content);



    }

 }
