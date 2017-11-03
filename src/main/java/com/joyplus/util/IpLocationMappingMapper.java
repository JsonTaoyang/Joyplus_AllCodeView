package com.joyplus.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.joyplus.model.IpLocationMapping;

public class IpLocationMappingMapper implements RowMapper<IpLocationMapping>{
	
	@Override
	public IpLocationMapping mapRow(ResultSet resultSet, int rows) throws SQLException{
		
		IpLocationMapping ipLocationMapping = new IpLocationMapping();
		ipLocationMapping.setIp_from(resultSet.getString(1));
		ipLocationMapping.setIp_to(resultSet.getString(2));
		ipLocationMapping.setProvince(resultSet.getString(3));
		ipLocationMapping.setCity(resultSet.getString(4));
		ipLocationMapping.setIp_from_long(resultSet.getLong(5));
		ipLocationMapping.setIp_to_long(resultSet.getLong(6));
		
		return ipLocationMapping;
	}

}
