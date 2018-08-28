package com.highguard.Wisdom.struts.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DnsCache {

	private Map<String, String> first = null;
	
	private Map<String, String> second = null;
	
	private static DnsCache uc = null;
	
	private DnsCache() {
		first = new HashMap<String, String>();
		second = new HashMap<String, String>();
	}
	
	public Map<String, String> getMap() {
		return first;
	}

	public synchronized void setFirst(Map<String, String> map) {
		this.first = map;
	}
	
	public synchronized void setSecond(Map<String, String> map) {
		this.second = map;
	}

	public static DnsCache instance() {
		if (uc == null) 
			uc = new DnsCache();
		
		return uc;
	}
	
	public synchronized void putFirst(String key, String value) {
		first.put(key, value);
	}
	
	public synchronized void putSecond(String key, String value) {
		second.put(key, value);
	}
	
	public synchronized String removeFirst(String key) {
		return first.remove(key);
	}
	
	public synchronized String removeSecond(String key) {
		return second.remove(key);
	}
	
	public synchronized String getFirst(String key) {
		return first.get(key);
	}
	
	public synchronized Map<String, String> getFirst() {
		return first;
	}
	
	public synchronized Map<String, String> getSecond() {
		return second;
	}
	
	public Set<String> firstKeySet() {
		return first.keySet();
	}
	
	public Set<String> secondKeySet() {
		return second.keySet();
	}
	
	public boolean containsSecKey(String key) {
		return second.containsKey(key);
	}
	
	public synchronized String getSecond(String key) {
		return second.get(key);
	}
}
