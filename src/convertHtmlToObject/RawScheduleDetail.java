package convertHtmlToObject;

import java.sql.Timestamp;

public class RawScheduleDetail extends RawSchedule implements Comparable<RawScheduleDetail>{
private int id;
	
	private Timestamp time;

	private String startTimeToEnd;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}



	public String getStartTimeToEnd() {
		return startTimeToEnd;
	}

	public void setStartTimeToEnd(String startTimeToEnd) {
		this.startTimeToEnd = startTimeToEnd;
	}

	@Override
	public String toString() {
		return "id=" + id + ", time=" + time + ", startTimeToEnd=" + startTimeToEnd + ", toString()="
				+ super.toString() + "\n";
	}

	@Override
	public int compareTo(RawScheduleDetail o) {
		
		return getTime().compareTo(o.getTime());
	}

}
