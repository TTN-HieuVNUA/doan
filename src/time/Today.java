package time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Today {

	public String getToday() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}
}
