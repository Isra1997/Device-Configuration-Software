package com.Device.ConfigurationSoftware.dto;

import com.Device.ConfigurationSoftware.entity.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceDTO {
    List<Device> devices;
    String message;

    public DeviceDTO(List<Device> devices) {
        this.devices = devices;
    }


    public DeviceDTO(String message) {
        this.devices = new ArrayList<>();
        this.message = message;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
