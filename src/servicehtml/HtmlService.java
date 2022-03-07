package servicehtml;

import java.io.IOException;
import java.util.List;

import com.hieu.entity.RawSchedule;

import convertHtmlToObject.RawScheduleDetail;
import convertHtmlToObject.RawStudent;

public interface HtmlService {

	List<RawSchedule> getListRawSchedule (String ids) throws IOException;
	
	List<RawScheduleDetail> getListRawScheduleDetails (String id) throws IOException;
	
	String startTimeToEnd(int lessonStart, int numOfLesson);
	
	List<List<RawStudent>> getAllListStudent (String LecCode) throws IOException;
	
	List<RawStudent> getAListStudent (String link);
}
