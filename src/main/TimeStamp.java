package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeStamp {

	public TimeStamp() {

	}

	public String getDateTime() {
		Date now = new Date();
		DateFormat formatter = new SimpleDateFormat("yy.MM.dd.HH.mm.ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String s = formatter.format(now);
		s = s.replace(".", "");
		// System.out.println(s);
		return s;

	}
	public  String getDateTimeNormal() {
		Date now = new Date();
		DateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String s = formatter.format(now);
		
		// System.out.println(s);
		return s;
	}
}
