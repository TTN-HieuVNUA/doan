package time;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TimeHandling {
	List<String> listDayOnWeek = new ArrayList<String>();
	
	public String startWeekToEnd(String week) {
		String startDate = readFileStartDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date parse = simpleDateFormat.parse(startDate);
			long weeks = Long.valueOf(week);
			
			Timestamp startDateOnWeek = new Timestamp(parse.getTime() + (1000*60*60*24*7*(weeks-1)));
			Timestamp endDateOnWeek = new Timestamp(startDateOnWeek.getTime()+(1000*60*60*24*6L));
			
			Date date = new Date();
			Date date1 = new Date();
			date.setTime(startDateOnWeek.getTime());
			date1.setTime(endDateOnWeek.getTime());
			return simpleDateFormat.format(date)+"-"+simpleDateFormat.format(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String readFileStartDate () {
		File file = new File("startDate.txt");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				return scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void dayOnWeek(String week) {
		String startDate = readFileStartDate();
		long numWeek = Long.valueOf(week);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date = simpleDateFormat.parse(startDate);
			Long time = date.getTime();
			Long startWeek = time + (1000*60*60*24*7*(numWeek-1));
			Long dayOnW = 1L;
			for (int i = 1; i < 8; i++) {
				Date date2 = new Date();
				date2.setTime(startWeek + (1000*60*60*24*i)- (1000*60*60*24));
				String day = convert(i) +simpleDateFormat.format(date2);
				listDayOnWeek.add(day);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String convert(int i) {
		switch (i) {
		case 1:
			return "thứ 2: ";
		case 2:
			return "thứ 3: ";
		case 3: 
			return "thứ 4: ";
		case 4:
			return "thứ 5: ";
		case 5:
			return "thứ 6: ";
		case 6:
			return "thứ 7: ";
		case 7:
			return "chủ nhật: ";

		default:
			return null;
		}
	}
	
	public List<String> getListDayOnWeek(String week){
		dayOnWeek(week);
		return listDayOnWeek;
	}
	public static void main(String[] args) {
		TimeHandling timeHandling = new TimeHandling();
		System.out.println(timeHandling.startWeekToEnd("1"));;
	}
}
