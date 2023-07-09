
package ru.bisha.easycrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.DeviceEntity;
import ru.bisha.easycrm.db.repository.DeviceRepository;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public DeviceEntity getDevice(int id) {
        return deviceRepository.findById(id).orElseThrow();
    }

    public List<DeviceEntity> getDevicesByUserId(int id) {
        return deviceRepository.getDevicesByOwnerId(id);
    }

    public List<DeviceEntity> getAll() {
        return deviceRepository.findAll();
    }

    public void saveDevice(DeviceEntity device) {
        deviceRepository.save(device);
    }

    public Page<DeviceEntity> getPageOfDevices(PageRequest request) {
        return deviceRepository.findAll(request);
    }

    public Page<DeviceEntity> getDevicesBySearch(String search, PageRequest request) {
        return deviceRepository.getDevicesBySearch("%" + search + "%", request);
    }
}
