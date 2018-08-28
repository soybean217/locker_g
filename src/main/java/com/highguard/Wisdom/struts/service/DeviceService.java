package com.highguard.Wisdom.struts.service;

import java.util.List;

import com.highguard.Wisdom.mgmt.hibernate.beans.Device;

public interface DeviceService {

	public List<Device> getDeviceList(String location);
}
