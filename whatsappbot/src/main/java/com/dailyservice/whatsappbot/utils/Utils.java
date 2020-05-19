package com.dailyservice.whatsappbot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	
	 public static float MIN_LATITUDE = Float.valueOf("-90.0000");
	 public static float MAX_LATITUDE = Float.valueOf("90.0000");
	 public static float MIN_LONGITUDE = Float.valueOf("-180.0000");
	 public static float MAX_LONGITUDE = Float.valueOf("180.0000");
	 public static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
	  
	  
	private static double distanceBetweenPoints(double lat1, double lon1, double lat2, double lon2, String unit) {
		
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
	
	//https://maps.googleapis.com/maps/api/geocode/json?latlng=22.53014,75.771484&key=AIzaSyDyrHCh7zDqQw0KgzLEo_DNQyOAGLN1-Zw
	
	public static boolean isValidLongitude(float longitude) {
	    if(longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	
	public static boolean isValidLatitude(float latitude) {
	    if(latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	
	public static String formatDate(Date d) {
		return sdf.format(d);
	}
	
	public static double calculateItemTotal(int qwt, double d) {
		return  qwt * d;
	}
}
