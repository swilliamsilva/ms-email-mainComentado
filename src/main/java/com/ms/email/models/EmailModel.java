package com.ms.email.models;

import com.ms.email.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
// Importa o Lombok
// Para não precisar colocar os construtores e metodos get seter

@Entity
// Esta classe é uma entidade

@Table(name = "TB_EMAIL")
// Nome da tabela que será gerada dentro do banco de dados 
// -- Esta classe vai guardar os email-s recebidos

public class EmailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long emailId;
    private String ownerRef;
    //- É uma referencia do proprietario que esta mandando a mensagem
    // onde vai passar um ID de quem envia.
    private String emailFrom;
    // -- quem esta enviando este email
    private String emailTo;
    // -- para quem este email esta sendo enviado
    private String subject;
    // -- O titulo do email
    @Column(columnDefinition = "TEXT")
    // É importante por esta definição por causa do espaço para escrever a mensagemm
    private String text;
    //Que é  corpo do email em si.
    private LocalDateTime sendDateEmail;
    // A data que vai sem enviada o email. LocalDateState
    private StatusEmail statusEmail;
    // E o status do envio se foi correto ou não.
}
