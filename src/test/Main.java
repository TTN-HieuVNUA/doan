package test;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import convertHtmlToObject.RawData;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
//		try {
//			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		    Date parsedDate = dateFormat.parse("2022-01-21 00:00:00.000");
//		    Timestamp timestamps = new java.sql.Timestamp(parsedDate.getTime());
//		    System.out.println(timestamps.after(timestamp));
//		} catch(Exception e) { 
//		   
//		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		Date parsedDate = dateFormat.parse("2022/01/24");
//		Timestamp timestamps = new java.sql.Timestamp(parsedDate.getTime());
//		
//		System.out.println(timestamps);
//		RawData rawData = new RawData();
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		rawData.getListRawScheduleDetails("cnp03").forEach(p->{
//			if (p.getTime().before(timestamp)) {
//				System.out.println(p);
//			}
//		});
		String dateToDate = "2022/24/01-2022/01/30";
		String s = "tuần 1:" + "(" + dateToDate + ")";
		String w = s.substring(8, s.length()-2);
		String week[] = w.split("-");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedDate = dateFormat.parse(week[0]);
		Timestamp timestamps = new java.sql.Timestamp(parsedDate.getTime());
		System.out.println(week[0]);
	}
}
