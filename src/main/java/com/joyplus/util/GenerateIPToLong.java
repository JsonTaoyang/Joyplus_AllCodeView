package com.joyplus.util;

public class GenerateIPToLong {
    public static long GenerateIPToLongValue(String IP)
    {
        String[] IpList =  IP.split("\\.");
        long result = 0;

        if(IpList.length == 4) {
            long a = Integer.parseInt(IpList[0]) * 256;
            a = a * 256 * 256;
            long b = Integer.parseInt(IpList[1]) * 256 * 256;
            long c = Integer.parseInt(IpList[2]) * 256;
            long d = Integer.parseInt(IpList[3]);
            result = a + b + c + d;
        }
        return result;
    }
    

}
