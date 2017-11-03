package com.joyplus.util;

public class iptolong {
	
public static void main(String[] args) {
	String ip1="101.90.126.0";
	String ip2="101.90.126.255";
	Long logip1=GenerateIPToLong.GenerateIPToLongValue(ip1);
	Long logip2=GenerateIPToLong.GenerateIPToLongValue(ip2);
	System.out.println(logip1);
	System.out.println(logip2);
}
}
