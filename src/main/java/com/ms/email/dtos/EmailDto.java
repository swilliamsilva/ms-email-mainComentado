package com.ms.email.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
// Esta classe é o objeto que vamos receber no metodo POST
@Data
// Já receber esta anotação do lombok para evitar Construtores Get e Set
public class EmailDto {
// Já vai fazer a validação usando o VALIDATION
    @NotBlank
    private String ownerRef;
    // o ownerRef é obrigatorio por que precisamos sabe quem é o proprietário da mensagem
    @NotBlank
    // com a anotação @NotBlank ele já faz esta validação se tiver 
    // algum campo faltando ele já envia um badrequest informando que 
    // tal campo é obrigatório
    @Email
    // Com esta validação @Email ele já explica se é um email valido.
    private String emailFrom;
    @NotBlank
    @Email
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;

}
