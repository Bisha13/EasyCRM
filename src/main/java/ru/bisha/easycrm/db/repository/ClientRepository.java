package ru.bisha.easycrm.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bisha.easycrm.db.entity.ClientEnitiy;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEnitiy, Integer> {
    ClientEnitiy findClientByPhoneNumberLike(String phoneNumber);
    List<ClientEnitiy> findClientsByPhoneNumberContains(String number);

    @Query(value = "select c from ClientEnitiy c where lower(c.name) " +
            "like lower(:search) or lower(c.phoneNumber) like lower(:search) " +
            "or lower(c.phoneNumber2) like lower(:search)")
    Page<ClientEnitiy> getClientsBySearch(
            @Param("search") String search, Pageable pageable);
}
