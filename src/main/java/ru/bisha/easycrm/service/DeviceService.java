package ru.bisha.easycrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bisha.easycrm.db.entity.DeviceEntity;

import java.util.List;

public interface DeviceService {
    DeviceEntity getDevice(int id);
    List<DeviceEntity> getDevicesByUserId(int id);
    List<DeviceEntity> getAll();
    void saveDevice(DeviceEntity device);
    Page<DeviceEntity> getPageOfDevices(PageRequest request);
    Page<DeviceEntity> getDevicesBySearch(String search, PageRequest request);

}
