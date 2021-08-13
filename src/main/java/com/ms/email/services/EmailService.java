package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


// É a camada intermediária entre o repository e o controller
// Que é uma classe do tipo Service

@Service
// Por ser uma classe service ela recebe esta anotação @Service

public class EmailService {

    @Autowired
    // Este ponto de Injeção do repository utilizando o @Autowired 
    // é para conseguir construir os metodos para fazer a persistencia
    // com os bancos.
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;
    // Segundo ponto de injeção que é o JMS 

    public EmailModel sendEmail(EmailModel emailModel) {
        // Aqui vai ser feito o salvamento e o envio do email
        // Aqui usamos o SMTP do gmail, para ter o mais robusto
        // pode trocar pelo aws.amazon.com
        //  Amazon Simple Email Service
        //
        emailModel.setSendDateEmail(LocalDateTime.now());
        // Aqui vai setar a data de envio 
        try{
            // Usamos este bloco try para realizar o envio
            // e tambem a persistência

            SimpleMailMessage message = new SimpleMailMessage();
            // Aqui se cria um instancia de SMM e monta a menssagem do email
            message.setFrom(emailModel.getEmailFrom());
            // O from que quem esta enviando email
            message.setTo(emailModel.getEmailTo());
            // O to para quem vai receber o email
            message.setSubject(emailModel.getSubject());
            // O titulo do email.
            message.setText(emailModel.getText());
            // tambem o texto do email
            emailSender.send(message);
            // Se tudo der certo eu vou enviar o email com
            // status email SENT ou seja email enviado
            emailModel.setStatusEmail(StatusEmail.SENT);
            // Se der algum erro no envio do email ele vai cair
            //dentro bloco catch
            // neste caso ele vai ser salvo com o status ERROR
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
            // E no final ele vai salvar este email com o seu 
            // determinado status
        } finally {
            return emailRepository.save(emailModel);
        }
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }
}
