package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Parser {
	String data;
	String imei;
	Bd bd;

	public Parser(Bd bd) {
		this.bd = bd;
	}

	public void first(String data) {
		this.data = data;
		List<String> list = setList(data);
		// this.imei = list.get(0).split(",")[0];
		String sourcedata = list.get(0);
		String[] param = list.remove(0).split(",");
		this.imei = param[0];
		Map<String, String> map = new HashMap<String, String>();

		map.put("time", param[1]);
		map.put("veraciously", param[2]);
		map.put("lat", param[3]);
		map.put("lng", param[5]);
		map.put("speed", param[7]);
		map.put("azimuth", param[8]);
		map.put("date", param[9]);
		map.put("sputnik", param[10]);
		map.put("zaryad", param[13]);
		map.put("sourcedata", sourcedata);
		// System.out.println("imei: "+this.imei+ " lat: "+map.get("lat"));
		// {2910921893832,120305,A,5025.989,N,03023.704,E,0.0,58.,180414,7,00,F9,62,1,,,,,,,07,,,191.815,M,3,,}
		// {120305,A,5025.989,N,03023.704,E,0.0,58.,180414,7,00,F9,62,1,,,,,,,07,,,191.815,M,3,,}
		convert(map);
		while (!list.isEmpty()) {
			params(list.remove(0));
		}

	}

	public void next(String data) {
		List<String> list = setList(data);
		while (!list.isEmpty()) {
			params(list.remove(0));
		}

	}

	private void params(String row) {
		String[] param = row.split(",");
		Map<String, String> map = new HashMap<String, String>();
		map.put("time", param[0]);
		map.put("veraciously", param[1]);
		map.put("lat", param[2]);
		map.put("lng", param[4]);
		map.put("speed", param[6]);
		map.put("azimuth", param[7]);
		map.put("date", param[8]);
		map.put("sputnik", param[9]);
		map.put("zaryad", param[12]);
		map.put("sourcedata",row);
		//System.out.println(param[4]);
		convert(map);
	}

	private List<String> setList(String data) {
		List<String> allMatches = new ArrayList<String>();
		
		Matcher m = Pattern.compile("[{][^}]+[}]").matcher(data);
		while (m.find()) {
			//System.out.println("List add");
			allMatches.add(m.group().replaceAll("[{]|[}]", ""));
		}

		return allMatches;

	}

	private String demic(String s) {
		if(s.isEmpty()){
			System.out.println("Empty latLng");
			return null;
		}
		
		double d  = Double.parseDouble(s);
		d = d / 100;
		double src = d;
		int res = (int)src;
		double res2=(src - res);
		res2=(res2*100)/60;
		d =res+res2;
		double newDouble = new BigDecimal(d).setScale(6, RoundingMode.UP).doubleValue();
		s=Double.toString(newDouble);
		
		
		return s;
	}

	private String datetime(String date, String time) {
		if (date == null || date.length() == 0) {
			return null;
		}
		if (time == null || time.length() == 0) {
			return null;
		}
		String[] aDate = date.split(""); // 180414

		date = "" + aDate[5] + aDate[6] + aDate[3] + aDate[4] + aDate[1]
				+ aDate[2];

		if (time.indexOf(".") != -1) {
			time = time.split("[.]")[0];
		}

		return date + time;
	}

	private String zaryadDec(String z) {
		int decimal = Integer.parseInt(z, 16);
		String s = "" + decimal;
		double d = Double.valueOf(s.trim()).doubleValue();
		d = d / 10;
		s = "" + Double.toString(d);
		return s;
	}

	private String azimuth(String s) {
		if (s == null || s.length() == 0) {
			return "0";
		}
		// double d =Double.valueOf(s.trim()).doubleValue();
		//
		return s;

	}
	private int sputnik(String s){
		int i = Integer.parseInt(s);
		return i;
	}

	private String speedKmh(String s) {
		double d = Double.valueOf(s.trim()).doubleValue();
		d = d * 1.825;
		s = Double.toString(d);
		return s;
	}

	private void convert(Map<String, String> map) {
		//{012207005768384,085812,A,5023.318,N,03029.624,E,0.9,12.,200414,6,00,F9,91,1,,,,,,,07,,,157.167,M,3,,}
		//{012207005768384,122402,A,5023.273,N,03029.655,E,0.2,107,200414,8,00,F9,91,1,,,,,,,07,,,165.404,M,3,,}
		//{012207005768384,122402,A,5023.273,N,03029.655,E,0.2,107,200414,8,00,F9,91,1,,,,,,,07,,,165.404,M,3,,}
		//{085812,A,5023.318,N,03029.624,E,0.9,12.,200414,6,00,F9,91,1,,,,,,,07,,,157.167,M,3,,}
		//{2910921893832,120305,A,5025.989,N,03023.704,E,0.0,58.,180414,7,00,F9,62,1,,,,,,,07,,,191.815,M,3,,}
		//{120305,A,5025.989,N,03023.704,E,0.0,58.,180414,7,00,F9,62,1,,,,,,,07,,,191.815,M,3,,}
		//{105454,A,5023.322,N,03029.630,E,0.5,181,200414,8,00,00,93,2,,,,,,,07,,,173.597,M,3,,}

		//{2910921893832,,A,,N,,E,0.0,,,7,00,F9,62,1,,,,,,,07,,,191.815,M,3,,}
		//{080119,V,,,,,1.4,344,200414,4,00,F9,94,1,,,,,,,07,,,,M,3,,}
		/////maxim@maxim-HP-250-G1-Notebook-PC:~
		Map<String,String> line = map;
	
		String dateTime = datetime(map.get("date"), map.get("time"));
		map.put("dateTime", dateTime);
		
		String lat = demic(map.get("lat"));
		map.put("lat",lat);
		
		String lng = demic(map.get("lng"));
		map.put("lng", lng);
		
		String _zaryad = zaryadDec(map.get("zaryad"));
		map.put("zaryad", _zaryad);
		
		String _speed = speedKmh(map.get("speed"));
		
		map.put("speed", _speed);
		
		String _azimuth =azimuth(map.get("azimuth"));
		
		map.put("azimuth", _azimuth);
		
		System.out.println("imei: "+this.imei+" lat: "+map.get("lat")+" lng:  "+map.get("lng")+
				" dateTime: "+map.get("dateTime")+ " zaryad: "+ map.get("zaryad")+" azimuth: "+
				map.get("azimuth")+" speed:" + map.get("speed")+ " sputnik: "+map.get("sputnik"));
	
	
		
		
		
		if(map.get("dateTime")!=null 
				&& 3<sputnik(map.get("sputnik")) 
				&& map.get("lat")!=null
				&& map.get("lng")!=null
				&& !map.get("lat").equals("null")
				&& !map.get("lng").equals("null")
				){
			saveToDb(map);
		}else{
			
			map.put("dateTime", new TimeStamp().getDateTime())	;
			Map <String, String> _map = bd.getData(this.imei);
			
			System.out.println("No visible sattelits");
			map.put("lat", _map.get("lat"));
			map.put("lng", _map.get("lng"));
			map.put("speed", "0");
			map.put("sputnik", "0");
			saveToDb(map);
			
		
		}
	}
	
	private void saveToDb(Map<String, String> map){
		String _line = "INSERT INTO " +"`log`"+"(imei, lat, lng, datetime, speed, sputnik, azimuth,sourcedata, zaryad) "+
				"VALUES("+
				"'"+this.imei+"'"+
				",'"+map.get("lat")+"'"+
				",'" +map.get("lng")+"'"+
				",'"+map.get("dateTime")+"'"+
				",'"+map.get("speed")+"'"+
				",'"+map.get("sputnik")+"'"+
				",'"+map.get("azimuth")+"'"+
				",'"+map.get("sourcedata")+"'"+
				",'"+map.get("zaryad")+"'"+
					")";
				
		try {
			bd.save(_line);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
