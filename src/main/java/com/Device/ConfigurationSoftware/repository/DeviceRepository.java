package com.Device.ConfigurationSoftware.repository;

import com.Device.ConfigurationSoftware.entity.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
}
