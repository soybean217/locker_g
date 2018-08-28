package com.highguard.Wisdom.mgmt.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class BaseDao {
	@Resource
	SessionFactory factory;
	
	
}
