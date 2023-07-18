package com.Device.ConfigurationSoftware;

import com.Device.ConfigurationSoftware.entity.Device;
import com.Device.ConfigurationSoftware.exceptions.DeviceException;
import com.Device.ConfigurationSoftware.service.DeviceService;
import com.Device.ConfigurationSoftware.service.EncryptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Device> json;

    @MockBean
    DeviceService deviceService;

    @MockBean
    EncryptionService encryptionService;

    @Before
    public void setup() throws DeviceException {
        Device device = new Device();
        device.setId(1L);
        Device configuredDevice = new Device();
        configuredDevice.setStatus("ACTIVE");
        configuredDevice.setTemperature(1);
        given(deviceService.addDevice(any())).willReturn(device);
        given(deviceService.getDeviceById(any())).willReturn(Optional.of(device));
        given(deviceService.configureDevice(any())).willReturn(configuredDevice);
        given(deviceService.updateDevice(any())).willReturn(device);
        given(deviceService.getAllDevices()).willReturn(Collections.emptyList());
    }

    @Test
    public void getAllDevices() throws Exception {
        mockMvc.perform(get("/device"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"devices\":[],\"message\":null}"));

        verify(deviceService,times(1)).getAllDevices();
    }

    @Test
    public void getAddDevice() throws Exception {
        Device mockDevice = getDevice();
        mockMvc.perform(post("/device")
                .content(json.write(mockDevice).getJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteDevice() throws Exception {
        mockMvc.perform(delete("/device/1")
                        .contentType(MediaType.APPLICATION_JSON))
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk());
    }

    @Test
    public void editDevice() throws Exception {
        Device mockDevice = getDevice();
        mockMvc.perform(put("/device")
                        .content(json.write(mockDevice).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void configureDevice() throws Exception {
        mockMvc.perform(post("/configure/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Device getDevice() {
        Device device = new Device();
        device.setPin_code("1234567");
        device.setStatus("READY");
        device.setTemperature(1);
        device.setId(1L);
        return device;
    }
}
