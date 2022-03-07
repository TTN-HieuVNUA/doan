package time;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StartDate {

	public String getStartDate() {
		Document doc;
		try {
			doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id=CNP09").get();
			Element els = doc.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblNote");
			String s = els.toString();
			Document docs = Jsoup.parse(s);
			String startDate = splitDate(docs.text());
			return reverse(startDate);

		} catch (IOException e) {

		}
		return null;
	}

	public String getUpdateAt () {
		Document doc;
		try {
			doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id=CNP09").get();
			Element els = doc.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblNoteUpdate");
			String s = els.toString();
			Document docs = Jsoup.parse(s);
			String startDate = splitDate(docs.text());
			return reverse(startDate);

		} catch (IOException e) {

		}
		return null;
	}
	
	// khi doc tren daotao, ngay se co dang dd/mm/yyyy, dao nguoc lai yyyy/mm/dd
	public String reverse(String startDate) {
		String str[] = startDate.split("/");
		String s = str[2] + "/" + str[1] + "/" + str[0];
		return s;
	}

	// tach chuoi doc duoc de lay ngay bat dau
	private String splitDate(String str) {
		int lastSpace = str.lastIndexOf(" ");
		String startDate = str.substring(lastSpace + 1, str.length() - 1);
		return startDate;
	}
}
