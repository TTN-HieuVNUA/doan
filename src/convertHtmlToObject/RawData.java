package convertHtmlToObject;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hieu.entity.RawSchedule;
import com.hieu.entity.ScheduleDetail;

import servicehtml.HtmlService;

public class RawData implements HtmlService{

	List<String> listLink = new ArrayList<String>();

	List<RawSchedule> listSchedules = new ArrayList<RawSchedule>();

	List<RawScheduleDetail> listScheduleDetails = new ArrayList<RawScheduleDetail>();

	@Override
	public List<RawSchedule> getListRawSchedule (String ids) throws IOException {
		int count = 0;
		Document doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id="+ids).get();
		
		Elements els = doc.getElementsByClass("grid-roll2");
		String s = els.toString();
		Document docs = Jsoup.parse(s);

		// đếm tổng số thẻ table có trong class
		int num = docs.getElementsByTag("table").size();

		// đếm tổng số link có trong page(page này chỉ có link danh sách sinh viên nên
		// không cần quan tâm đến class của thẻ a)
		int numsLink = docs.getElementsByTag("a").size();

		// lấy chuỗi các link ds sinh viên của từng hàng con và thêm vào list
		for (int i = 0; i < numsLink; i++) {
			String URLStr = docs.select("a").get(i).attr("href");
			listLink.add(URLStr);
		}

		// duyệt từng thẻ table
		for (int i = 0; i < num; i++) {
			/*
			 * vì trong table cha có nhi�?u table con và table cháu, nên ta loại các table
			 * cháu, chỉ đ�?c table con là các dòng lớn
			 */
			if (docs.getElementsByTag("table").get(i).text().length() > 30) {

				StringBuilder stringBuilder = new StringBuilder();

				// 1 dòng lớn có các dòng con, nhưng cột từ 0->6 là không thay đổi nên đ�?c cứng
				// được
				for (int j = 0; j < 6; j++) {
					stringBuilder.append(docs.getElementsByTag("table").get(i).getElementsByTag("td").get(j).text())
							.append("\t");
				}

				// các cột con từ 7 trở đi luôn có số lượng table con là giống nhau
				count = docs.getElementsByTag("table").get(i).getElementsByTag("td").get(7).getElementsByTag("td")
						.size();

				// đ�?c cột sau cột thứ 7(cột nhóm thực hành) để cột nhóm thực hành xử lý sau
				for (int j = 7 + count; j < docs.getElementsByTag("table").get(i).getElementsByTag("td")
						.size(); j += count) {
					if (!docs.getElementsByTag("table").get(i).getElementsByTag("td").get(j).text().equals("")) {
						stringBuilder.append(docs.getElementsByTag("table").get(i).getElementsByTag("td").get(j).text())
								.append("\t");
					}
				}

				// đ�?c cột nhóm thực hành, nếu td con bên trong td cha rỗng thì cho =0 để dễ
				// dàng split
				for (int j = 8; j < 7 + count; j++) {
					if (docs.getElementsByTag("table").get(i).getElementsByTag("td").get(j).text().equals("")) {
						stringBuilder.append("0 ");
					} else {
						stringBuilder.append(docs.getElementsByTag("table").get(i).getElementsByTag("td").get(j).text())
								.append(" ");
					}
				}

				// bên trong mỗi cột thực hành thư�?ng có 1 thẻ div nếu có 2 phần tử con trở lên
				if (docs.getElementsByTag("table").get(i).getElementsByTag("td").get(7).getElementsByTag("div").text()
						.length() == 0) {
					stringBuilder.append("0 ");
				} else {
					stringBuilder.append(docs.getElementsByTag("table").get(i).getElementsByTag("td").get(7)
							.getElementsByTag("div").text());
				}

				show(stringBuilder);
			}

		}
		
		// kiểm tra nếu số lượng danh sách sinh viên bằng với số lượng thời khóa biểu dạng thô thì thêm ds sinh viên
		if (listLink.size() == listSchedules.size()) {
			for (int j = 0; j < listLink.size(); j++) {
				listSchedules.get(j).setLinkListStd(listLink.get(j));
			}
		}
		return listSchedules;
	}
	
	@Override
	public List<RawScheduleDetail> getListRawScheduleDetails (String id) throws IOException{
		getListRawSchedule(id).forEach(p->{
			convert(p);
		});
//		 sắp xếp lại lịch theo ngày
		Collections.sort(listScheduleDetails);
		return listScheduleDetails;
	}
	
	public void show(StringBuilder str) {
		// chuyển stringbuilder sang string để tách chuỗi
		String string = str.toString();
		// tách các thuộc tính dựa vào thẻ tab
		String[] strs = string.split("\t");

		// đếm số lượng phần tử con có được ở cột thứ để dùng vòng lặp
		String[] strCount = strs[6].split(" ");
		int count = strCount.length;

		// vì 1 phần tử cha có nhiều con, nên phải dùng mảng và tách từng phần tử
		String[] dayOfWeek = strs[6].split(" ");
		String[] LessionStart = strs[7].split(" ");
		String[] numOfSession = strs[8].split(" ");
		String[] location = strs[9].split(" ");
		String[] week = strs[10].split(" ");
		String[] practiveTeam = strs[12].split(" ");

		// dùng vòng lặp với số lượng phần tử con trong 1 hàng cha để tạo(1 hàng cha có
		// 2 hàng con thì lặp 2 lần)
		for (int j = 0; j < count; j++) {
			RawSchedule schedule2 = new RawSchedule();
			schedule2.setSubCode(strs[0]);
			schedule2.setSubName(strs[1]);
			schedule2.setGroupSub(Integer.valueOf(strs[2]));
			schedule2.setClassCode(strs[4]);
			schedule2.setDayOfWeek(dayOfWeek[j]);
			schedule2.setLessonStart(Integer.valueOf(LessionStart[j]));
			schedule2.setNumOfLesson(Integer.valueOf(numOfSession[j]));
			schedule2.setLocation(location[j]);
			schedule2.setWeek(week[j]);
			schedule2.setPractiveTeam(practiveTeam[j]);

			listSchedules.add(schedule2);
		}
	}

	// dựa vào chuỗi tuần để tách 1 tkb cha ra nhiều tkb con dong thoi them vao danh sach
	public void convert(RawSchedule rawSchedule) {
		String startDate = "2022/01/24";
		String week = rawSchedule.getWeek();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		try {
			Date parse = format.parse(startDate);
			Timestamp times = new Timestamp(parse.getTime());
			Timestamp timeslast = new Timestamp(0L);
			for (int i = 0; i < week.length(); i++) {
				timeslast = new Timestamp(times.getTime() + (1000 * 60 * 60 * 24 * 6L));
				if (week.charAt(i) != '-') {
					RawScheduleDetail scheduleDetail = new RawScheduleDetail();
					scheduleDetail.setSubCode(rawSchedule.getSubCode());
					scheduleDetail.setSubName(rawSchedule.getSubName());
					scheduleDetail.setGroupSub(rawSchedule.getGroupSub());
					scheduleDetail.setClassCode(rawSchedule.getClassCode());
					scheduleDetail.setPractiveTeam(rawSchedule.getPractiveTeam());
					scheduleDetail.setDayOfWeek(rawSchedule.getDayOfWeek());
					scheduleDetail.setLessonStart(rawSchedule.getLessonStart());
					scheduleDetail.setNumOfLesson(rawSchedule.getNumOfLesson());
					scheduleDetail.setLocation(rawSchedule.getLocation());
					scheduleDetail.setTime(new Timestamp(
							times.getTime() + (1000 * 60 * 60 * 24 * convertStringToInt(rawSchedule.getDayOfWeek())- (1000 * 60 * 60 * 24))));
					scheduleDetail.setStartTimeToEnd(startTimeToEnd(rawSchedule.getLessonStart(), rawSchedule.getNumOfLesson()));
					scheduleDetail.setLinkListStd(rawSchedule.getLinkListStd());
					listScheduleDetails.add(scheduleDetail);
				}
				times = new Timestamp(times.getTime() + (1000 * 60 * 60 * 24 * 7L));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public long convertStringToInt(String dayOfWeek) {
		if (dayOfWeek.equalsIgnoreCase("hai")) {
			return 1L;
		}
		if (dayOfWeek.equalsIgnoreCase("ba")) {
			return 2L;
		}
		if (dayOfWeek.equalsIgnoreCase("tư")) {
			return 3L;
		}
		if (dayOfWeek.equalsIgnoreCase("năm")) {
			return 4L;
		}
		if (dayOfWeek.equalsIgnoreCase("sáu")) {
			return 5L;
		}
		if (dayOfWeek.equalsIgnoreCase("bảy")) {
			return 6L;
		}
		if (dayOfWeek.equalsIgnoreCase("CN")) {
			return 7L;
		}
		return 0;
	}
	
	@Override
	public String startTimeToEnd(int lessonStart, int numOfLesson) {
		StringBuilder str = new StringBuilder();
		if (lessonStart==1) {
			if (numOfLesson == 3) {
				str.append("7h to 9h35");
			}
			else {
				str.append("7h to 11h40 ");
			}
		}
		if (lessonStart == 4) {
			str.append("9h50 to 11h40");
		}
		if (lessonStart == 6) {
			if (numOfLesson == 3) {
				str.append("12h45 to 15h25 ");
			}
			else {
				str.append("12h45 to 17h25");
			}
		}
		if (lessonStart == 9) {
			str.append("15h40 to 17h25");
		}
		
		return str.toString();
	}
	
	@Override
	public List<List<RawStudent>> getAllListStudent (String LecCode) throws IOException {
		Document doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id=cnp09").get();
		
		Elements els = doc.getElementsByClass("grid-roll2");
		String s = els.toString();
		Document docs = Jsoup.parse(s);
		int numsLink = docs.getElementsByTag("a").size();
		List<List<RawStudent>> listStd = new ArrayList<List<RawStudent>>();
		String arrLink[] = new String[numsLink];
		// lấy chuỗi các link ds sinh viên của từng hàng con và thêm vào list
		for (int i = 0; i < numsLink; i++) {
			String URLStr = docs.select("a").get(i).attr("href");
			arrLink[i] = URLStr;
		}
			for (int i = 0; i < arrLink.length; i++) {
				listStd.add(getAListStudent("http://daotao.vnua.edu.vn/"+arrLink[i]));
			}
		return listStd;
	}
	
	// lay ra danh sach sinh vien dua vao link, ds null quang ra 1 exception va return null
	@Override
	public List<RawStudent> getAListStudent (String link) {
		List<RawStudent> ListStudent = new ArrayList<RawStudent>();
		Document doc;
		try {
			doc = Jsoup.connect(link).get();
			Element els = doc.getElementById("ctl00_ContentPlaceHolder1_ctl00_gvDSSinhVien");
			String s = els.toString();
			Document docs = Jsoup.parse(s);
			int countRow = docs.getElementsByTag("tr").size();
			for (int i = 1; i < countRow; i++) {
				RawStudent rawStudent = new RawStudent();
				rawStudent.setStdCode(docs.getElementsByTag("tr").get(i).getElementsByTag("td").get(1).text());
				rawStudent.setSurName(docs.getElementsByTag("tr").get(i).getElementsByTag("td").get(2).text());
				rawStudent.setName(docs.getElementsByTag("tr").get(i).getElementsByTag("td").get(3).text());
				rawStudent.setClassCode(docs.getElementsByTag("tr").get(i).getElementsByTag("td").get(4).text());
				rawStudent.setClassName(docs.getElementsByTag("tr").get(i).getElementsByTag("td").get(5).text());
				ListStudent.add(rawStudent);
			}
			return ListStudent;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public String getLecturerName(String lecCode) {
		Document doc;
		try {
			doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id="+lecCode).get();
			Element els = doc.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV");
			String s = els.toString();
			Document docs = Jsoup.parse(s);
			String name = docs.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV").text();
			return name;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getSchoolYear(String lecCode) {
		Document doc;
		try {
			doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id="+lecCode).get();
			Elements els = doc.getElementsByClass("navigate-base");
			String s = els.toString();
			Document docs = Jsoup.parse(s);
			String schoolYear = docs.getElementsByTag("select").get(0).getElementsByTag("option").get(0).text();
			return schoolYear;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) throws IOException {
		RawData rawData = new RawData();
		System.out.println(rawData.getAListStudent("http://daotao.vnua.edu.vn/Default.aspx?page=danhsachsvtheonhomhoc&malop=K65CNTTA&mand=CNP03~THCNTT01~4~1~--345~"));
	}
	
}
