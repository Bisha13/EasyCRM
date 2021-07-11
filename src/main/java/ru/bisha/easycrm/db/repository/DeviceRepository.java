package ru.bisha.easycrm.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bisha.easycrm.db.entity.Device;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> getDevicesByOwnerId(int id);

    @Query(value = "select d from Device d where lower(d.deviceName) " +
            "like lower(:search) or lower(d.vendor) like lower(:search) " +
            "or lower(d.model) like lower(:search) or " +
            "lower(d.description) like lower(:search)")
    Page<Device> getDevicesBySearch(
            @Param("search") String search, Pageable pageable);
}
