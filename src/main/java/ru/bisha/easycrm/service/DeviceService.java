package ru.bisha.easycrm.service;

import ru.bisha.easycrm.db.entity.Device;

import java.util.List;

public interface DeviceService {
    Device getDevice(int id);
    List<Device> getDevicesByUserId(int id);
    void saveDevice(Device device);
}
