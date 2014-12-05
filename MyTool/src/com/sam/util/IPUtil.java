package com.sam.util;

public class IPUtil {
	  public static long ipToLong(String ipString){
	        long result = 0;
	        java.util.StringTokenizer token = new java.util.StringTokenizer(ipString,".");
	        result += Long.parseLong(token.nextToken())<<24;
	        result += Long.parseLong(token.nextToken())<<16;
	        result += Long.parseLong(token.nextToken())<<8;
	        result += Long.parseLong(token.nextToken());
	        return result;
	    }
}
