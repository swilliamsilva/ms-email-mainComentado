package com.ms.email.repositories;

import com.ms.email.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {
}
// Esta interface extende a classe JPA para usar o metodos prontos
// como FindById , FindAll , Save,  Change
// e aqui passa a entidade que no caso EmailModel que é do tipo Long
