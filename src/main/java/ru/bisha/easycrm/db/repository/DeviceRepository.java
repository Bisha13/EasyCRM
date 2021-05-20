package ru.bisha.easycrm.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bisha.easycrm.db.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
