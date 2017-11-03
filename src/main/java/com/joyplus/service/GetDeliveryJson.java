package com.joyplus.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyplus.cache.IpLoactionMappingCache;
import com.joyplus.model.IpLocationMapping;
import com.joyplus.util.GenerateIPToLong;

import redis.clients.jedis.Jedis;

@Service
public class GetDeliveryJson {
	
	//properties for mapping request parameters
	private String media;
	private String ip;
	private long ipValue;
	private String deliver_date;
	private int hour;
	private String ad_space;
	

	//Redis configurations
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.password}")
	private String password;
	private Jedis jedis;
	
	//properties for searching ip mapping
	private int length;
	private int index;
	private IpLocationMapping ipLocationMapping;
	private String province;
	private String city;
	
	//properties for processing redis search
	private String pointKey;
	private String result;
	

    public String DeliveryString(HttpServletRequest request,HttpServletResponse response) {
    	String localAddress=request.getLocalAddr();
		String remoteAddress=(String) request.getAttribute("ClientRequestIP");
		System.out.println("ClientRequestIP=====>>>>"+remoteAddress);
		Date requestDate=new Date();
		String getRequestDate=new SimpleDateFormat("yyyy-MM-dd").format(requestDate);
		int deliver_hour= Integer.parseInt(new SimpleDateFormat("HH").format(requestDate));
		System.out.println("localAddress="+localAddress+";;;;;remoteAddress===="+remoteAddress+";;;getRequestDate=="+getRequestDate+"deliver_hour"+deliver_hour);
    	//Get Http request parameters
        ip = remoteAddress;
		//ip="101.90.126.89";
        ipValue = GenerateIPToLong.GenerateIPToLongValue(ip);
        
   /*     long startTime = System.currentTimeMillis(); 
    	url=gdj.DeliveryString(request,response);
    	long endTime = System.currentTimeMillis(); 
    	System.out.println("汇总程序运行时间：" + (endTime - startTime) + "ms");*/
        
        
        
        
        deliver_date = getRequestDate;
        media = request.getParameter("media");
         hour = deliver_hour;
        ad_space = request.getParameter("ad_space");
        
        
        
        
        System.out.println("media is " + media);
        System.out.println("ip is " + ip);
        System.out.println("ipValue is " + ipValue);
        System.out.println("hour is " + hour);
        System.out.println("ad_space is " + ad_space);       
      
        long startTime = System.currentTimeMillis();
        //initiate redis connection
        jedis = new Jedis(redisHost, redisPort);  
        jedis.auth(password);
        System.out.println("Redis host is: " + redisHost + " and port is: " + redisPort);
        //testUrl
        //http://localhost:8081/Campaign/getInfo?ip=36.100.15.255&deliver_date=2017-10-17&deliver_hour=10&media=1&ad_space=1
        long endTime = System.currentTimeMillis(); 
    	System.out.println("redis connection运行时间：1111111111111111111111===========" + (endTime - startTime) + "ms");
    	 long startTime2 = System.currentTimeMillis();
        //process ip location mapping
        length = IpLoactionMappingCache.ipLocationMappings.size();        
        province = null;
        city = null;
        index = length/2;
        length /= 2;
        int s=0;
        
        while(province == null) {
        	if(s<index) {
	        ipLocationMapping = IpLoactionMappingCache.ipLocationMappings.get(index);
	        System.out.println("index is " + index);
	        if(ipLocationMapping.getIp_from_long()<=ipValue && ipLocationMapping.getIp_to_long()>=ipValue) {
	        	province = ipLocationMapping.getProvince();
	        	city = ipLocationMapping.getCity();
	        	System.out.println("found the province " + province + " and city " + city);
	        	break;
	        }else if(ipLocationMapping.getIp_from_long() < ipValue){
	        	length /= 2;
	        	if (length == 0)
	        		length = 1;
	        	index = index + length;
	        	//System.out.println("bigger and index changed to " + index + " and length changed to " + length);
	        }else if (ipLocationMapping.getIp_from_long() > ipValue) {
	        	length /= 2;
	        	if (length == 0)
	        		length = 1;
	        	index = index - length;
	        	System.out.println("smaller and index changed to " + index + " and length changed to " + length);
	        }
	        System.out.println("index is " + index + "\t" + "ip is: " + ipValue + "\t" + 
	        IpLoactionMappingCache.ipLocationMappings.get(index).getIp_from_long() + " " +
	        IpLoactionMappingCache.ipLocationMappings.get(index).getIp_to_long());
	        s++;
	        System.out.println("IP查找共计："+s+"次");
        	}
        	else {
        		
        		System.out.println("IP不存在。。。");
        		break;
        		
        	}
        }
        long endTime2 = System.currentTimeMillis(); 
    	System.out.println("查询IP与省市对应运行时间：22222222222222222222222222222222222========" + (endTime2 - startTime2) + "ms");


        pointKey = deliver_date + "_" + hour + "_" + province + "_" + city + "_" + media + "_" + ad_space;        
        //pointKey ="2017-10-17_10_青海省_西宁市_1_l";
        System.out.println(pointKey);
        long startTime1 = System.currentTimeMillis();
        result = jedis.get(pointKey);
        System.out.println("Key is " + pointKey + " and value is " + result);
        long endTime1 = System.currentTimeMillis(); 
    	System.out.println("查询Redis运行时间：333333333333333333333333333333==========" + (endTime1 - startTime1) + "ms");
        
        response.setHeader("ad_space", ad_space);
        response.setHeader("media", media);
        

        return result;
    }
    public String Test(HttpServletRequest request,HttpServletResponse response) {
    	deliver_date ="2017-10-17";
    	ipValue=16778240;
        media = "1";
        hour = 10;
        ad_space = "l";
        //initiate redis connection
        jedis = new Jedis(redisHost, redisPort);  
        jedis.auth(password);
        System.out.println("Redis host is: " + redisHost + " and port is: " + redisPort);
        //process ip location mapping
        length = IpLoactionMappingCache.ipLocationMappings.size();        
        province = null;
        city = null;
        index = length/2;
        length /= 2;
        
        while(province == null) {
	        ipLocationMapping = IpLoactionMappingCache.ipLocationMappings.get(index);
	        System.out.println("index is " + index);
	        if(ipLocationMapping.getIp_from_long()<=ipValue && ipLocationMapping.getIp_to_long()>=ipValue) {
	        	province = ipLocationMapping.getProvince();
	        	city = ipLocationMapping.getCity();
	        	System.out.println("found the province " + province + " and city " + city);
	        	break;
	        }else if(ipLocationMapping.getIp_from_long() < ipValue){
	        	length /= 2;
	        	if (length == 0)
	        		length = 1;
	        	index = index + length;
	        	//System.out.println("bigger and index changed to " + index + " and length changed to " + length);
	        }else if (ipLocationMapping.getIp_from_long() > ipValue) {
	        	length /= 2;
	        	if (length == 0)
	        		length = 1;
	        	index = index - length;
	        	System.out.println("smaller and index changed to " + index + " and length changed to " + length);
	        }
	        System.out.println("index is " + index + "\t" + "ip is: " + ipValue + "\t" + 
	        IpLoactionMappingCache.ipLocationMappings.get(index).getIp_from_long() + " " +
	        IpLoactionMappingCache.ipLocationMappings.get(index).getIp_to_long());
        }
        pointKey = deliver_date + "_" + hour + "_" + province + "_" + city + "_" + media + "_" + ad_space;        
        System.out.println(pointKey);
        result = jedis.get(pointKey);
        System.out.println("Key is " + pointKey + " and value is " + result);
        response.setHeader("ad_space", ad_space);
        response.setHeader("media", media);
        

        return result;
    }
}
