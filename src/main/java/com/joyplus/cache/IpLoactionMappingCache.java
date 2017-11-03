package com.joyplus.cache;




import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.joyplus.model.IpLocationMapping;
import com.joyplus.util.IpLocationMappingMapper;




@Component
public  class IpLoactionMappingCache {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	private String sql;
	
	@Autowired
	ApplicationContext ctx;

	public static List<IpLocationMapping> ipLocationMappings;
	
	//query the ip location mapping from db into memory
	@PostConstruct
	public void init() {		
		sql = new String ("select * from ip_location_mapping order by ip_from_long ASC");
		ipLocationMappings = jdbcTemplate.query(sql, new IpLocationMappingMapper());
		
		//print all beans initiated by container
				String[] beanNames =  ctx.getBeanDefinitionNames();
				System.out.println("鎵�浠eanNames涓暟锛�"+beanNames.length);
				for(String bn:beanNames){
					System.out.println(bn);
				}

	}	

}
