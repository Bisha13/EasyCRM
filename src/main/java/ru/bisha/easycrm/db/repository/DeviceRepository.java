package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Device;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> getDevicesByOwnerId(int id);
}
