package com.joyplus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class GetWanIp {
	public static void main(String[] args) {
		GetNetIp("http://pv.sohu.com/cityjson?ie=utf-8");
		
	}
	
	 public static String GetNetIp(String url) {  
	        URL infoUrl = null;  
	        InputStream inStream = null;  
	        String line = "";  
	        try {  
	            infoUrl = new URL(url);  
	            URLConnection connection = infoUrl.openConnection();  
	            HttpURLConnection httpConnection = (HttpURLConnection) connection;  
	            int responseCode = httpConnection.getResponseCode();  
	            if (responseCode == HttpURLConnection.HTTP_OK) {  
	                inStream = httpConnection.getInputStream();  
	                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));  
	                StringBuilder strber = new StringBuilder();  
	                while ((line = reader.readLine()) != null)  
	                    strber.append(line + "\n");  
	                inStream.close();  
	                // 从反馈的结果中提取出IP地址  
	                int start = strber.indexOf("{");  
	                int end = strber.indexOf("}");  
	                String json = strber.substring(start, end + 1);  
	                if (json != null) {  
	                    try {  
	                        JSONObject jsonObject = new JSONObject(json);  
	                        line = jsonObject.optString("cip");  
	                    } catch (JSONException e) {  
	                        e.printStackTrace();  
	                    }  
	                }  
	                return line;  
	            }  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        System.out.println(line);
	        return line;  
	 }
	
	
	
	
	
	

}
