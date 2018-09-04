package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Objects;

/**
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class SystemUser {

	private Integer id;

	// 姓名
	private String name;
	private String username;
	private String password;
	private Integer role_id;
	
	public Integer getRole_id() {
		return role_id;
	}


	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}


	@Override
	public String toString() {
		return "SystemUser [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", role_id=" + role_id + "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public boolean equals(Object obj) {
	    if( obj instanceof SystemUser ){
	    	return Objects.equals(((SystemUser) obj).getId(), this.getId());
		}
		return false;
	}


	@Override
	public int hashCode() {
	    return this.id;
	}


}
