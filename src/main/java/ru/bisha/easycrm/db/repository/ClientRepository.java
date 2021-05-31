package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findClientByPhoneNumberLike(String phoneNumber);
}
