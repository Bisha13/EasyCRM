package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.Device;

import java.util.List;

public interface DeviceService {
    Device getDevice(int id);
    List<Device> getDevicesByUserId(int id);
    List<Device> getAll();
    void saveDevice(Device device);
    Page<Device> getPageOfDevices(PageRequest request);
}
