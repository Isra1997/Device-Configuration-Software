package com.Device.ConfigurationSoftware.controller;

import com.Device.ConfigurationSoftware.TestUtilities;
import com.Device.ConfigurationSoftware.dto.DeviceDTO;
import com.Device.ConfigurationSoftware.entity.Device;
import com.Device.ConfigurationSoftware.repository.DeviceRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

public class DeviceControllerTest extends TestCase {

    private DeviceRepository deviceRepository = mock(DeviceRepository.class);
    private DeviceController deviceController;

    @Before
    public void setup(){
        deviceController = new DeviceController();
        TestUtilities.injectObject(deviceController,"deviceRepository",deviceRepository);
    }

    @Test
    public void test_happy_addDevice(){
        //Arrange
        Device mockDevice = new Device();
        mockDevice.setPin_code("1234567");
        mockDevice.setTemperature(1);
        mockDevice.setStatus("READY");
        //Act
        ResponseEntity<DeviceDTO> createdDevice = deviceController.addDevice(mockDevice);
        //Assert
        assertNotNull(createdDevice);
        assertEquals(200,createdDevice.getStatusCode());
        DeviceDTO device = createdDevice.getBody();
        assertEquals(device.getDevices().get(0).getPin_code(),1234567);
        assertEquals(device.getDevices().get(0).getTemperature(),1);
        assertEquals(device.getDevices().get(0).getStatus(),"READY");
    }


}
