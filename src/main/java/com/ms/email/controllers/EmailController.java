package com.ms.email.controllers;


import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
// Como vai utilizar um uma API REST precisa usar o @RestController
public class EmailController {

    @Autowired
    // Um ponto de injeção de controler de emailService no service 
    EmailService emailService;
    // Agora vamos criar o método POST que é o metodo que vai receber 
    // o DTO de entrada , fazer a validação, passar do DTO para o Model
    // para salvar este model e tambem enviar o email para o usuário.
    @PostMapping("/sending-email")
    // O sending-email recebe o post
    // passa para o Sending-email a URI que é o /sending-email
    // sempre que alguem enviar um email é este metodo que vai enviar 
    // o emai.
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        // Recebe como parametros deste método o email DTO aquele que 
        // foi mapeado 
        // O @valid  é para que aquelas anotaçoes de validadas sejam
        // executadas testando o email recebido. Se algum campo
        // que foi anotado por exemplo como @NotBlank estiver em branco ele vai 
        // retornar para o cliente um aviso de Request dizendo que o 
        // campo esta em branco. E é obrigatorio.
        EmailModel emailModel = new EmailModel();
        // Para que se consiga salvar no banco de dados é
        // preciso transformar este email em model 
        // por isto é criado esta instancia de emailModel
        BeanUtils.copyProperties(emailDto, emailModel);
        // Depois de instanciado este emailModel é passado para 
        // para este metodo copyProperties do BeanUtils
        // Ele faz esta conversao de DTO para model.
        // Eu passo o que eu quero converter no caso o email DTO para
        // email model.
        emailService.sendEmail(emailModel);
        // Agora eu vou fazer o salvamento do email quanto o envio deste email.
        // no caso o metodo sendEmail dentro do service
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
        // Agora vai retornar para o cliente este model salvo e também
        // o status HTTP CREATED 
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<EmailModel> emailModelPage = emailService.findAll(pageable);
        if(emailModelPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(emailModelPage, HttpStatus.OK);
        }
    }
}
