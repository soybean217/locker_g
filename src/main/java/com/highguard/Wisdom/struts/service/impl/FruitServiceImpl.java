package com.highguard.Wisdom.struts.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.struts.service.FruitService;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class FruitServiceImpl implements FruitService {
	@Resource
	SessionFactory factory;

	private static String baseUrl="http://42.96.154.169:8080/";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getFruitsByDevice(int deviceId) {
		Session session = factory.getCurrentSession();
		//session.createQuery("").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = session.createQuery(
				"select new map(fruit.category as category,"
				+ "lattice.price as price,"
				+ "lattice.deviceid as deviceid,"
				+ "lattice.id as latticeid,"
				+ "lattice.copyprice  as copyprice,"
				+ "lattice.copynum as copynum,"
				+ "fruit.texture as texture,fruit.description as description,"
				+ "fruit.id as fruitid,fruit.place as place, fruit.productname as productname,"
				+ "CONCAT('"+baseUrl+"',fruit.imageurl) as imageurl,lattice.weight as weight "
				+ ") from Lattice lattice,Fruit fruit where lattice.deviceid="+deviceId
				+" and fruit.id=lattice.fruitid"
				).list();
		return list;
	}
}
