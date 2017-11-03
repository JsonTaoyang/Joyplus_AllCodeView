package com.joyplus.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyplus.service.GetDeliveryJson;

@Controller
@RequestMapping("/Campaign")
@ResponseBody
public class WebServerController {
	
	@Autowired
	private GetDeliveryJson gdj;
	private String url;
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    public String getInfo(HttpServletRequest request ,HttpServletResponse response,ModelMap model)  {
    	//String clientIPW=getHeader(request,"X-Real-IP");
    	String clientIPW="101.90.253.14";
    	String vipAddress=request.getParameter("IPAddress");
    	String macAddress=request.getParameter("MacAddress");
    	String media=request.getParameter("media");
    	String ad_space=request.getParameter("ad_space");
    	request.setAttribute("ClientRequestIP",clientIPW);
    	long startTime = System.currentTimeMillis(); 
    	url=gdj.DeliveryString(request,response);
    	long endTime = System.currentTimeMillis(); 
    	System.out.println("汇总程序运行时间：44444444444444444444444444444444444============" + (endTime - startTime) + "ms");
        response.setHeader("Campaign_id", "240");
        response.setHeader("MacAddress", macAddress);
        response.setHeader("media", media);
        response.setHeader("ad_space", ad_space);
        response.setHeader("vipAddress", vipAddress);
        //localhost:8081/Campaign/getInfo?ip=36.149.12.0&deliver_date=20170907&deiliver_hour=0&media=称&ad_space=g
        return url;
    }
    @RequestMapping(value = "/getClientIP",method = RequestMethod.GET)
    public String getClientIP(HttpServletRequest request ,HttpServletResponse response,ModelMap model)  {
    	String clientIPW=request.getHeader("X-Real-IP");
    	
    	System.out.println("clientIPW is=======>" + clientIPW);
   
    	
        return clientIPW;
    }
    private static String getHeader(HttpServletRequest request, String headName) {  
        String value = request.getHeader(headName);  
       // return !StringUtils.isBlank(value) && !"unknown".equalsIgnoreCase(value) ? value : "";  
        return value;
    }  
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(HttpServletRequest request ,HttpServletResponse response,ModelMap model)  {
    	String ClientIP=request.getRemoteAddr();
    	url=gdj.Test(request, response);
    	System.out.println("ClientIP is=======>" + ClientIP);
    	url.indexOf("campaign_id");
    
    	response.setHeader("Campaign_id",response.getHeader("") );
        return url;
    }
}