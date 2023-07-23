package com.Device.ConfigurationSoftware.repository;

import com.Device.ConfigurationSoftware.entity.Device;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeviceRepositoryH2Test {

    @Autowired
    private DeviceRepository deviceRepository;


    @Test
    public void getAllDevices(){
        List<Device> devices = deviceRepository.findAll();
        Assertions.assertEquals(devices.size(),1);
    }

    @Test
    public void getDeviceByID(){
        Optional<Device> device = deviceRepository.findById(1L);
        Assertions.assertNotNull(device.get());
        Assertions.assertEquals(device.get().getPin_code(),"1234567");
    }

}
