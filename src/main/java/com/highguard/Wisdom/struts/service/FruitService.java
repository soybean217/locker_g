package com.highguard.Wisdom.struts.service;

import java.util.List;
import java.util.Map;

public interface FruitService {
	/*
	 * 获取设备下的水果
	 */
	List<Map<String, Object>> getFruitsByDevice(int deviceId);
}
