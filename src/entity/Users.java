package entity;

import java.util.List;

public class Users {

	private int Id;
	
	private String lecCode;
	
	private String lecName;

	private List<Semester> listSemester;
	
	public Users() {
		
	}

	public Users(String lecCode, String lecName) {
		this.lecCode = lecCode;
		this.lecName = lecName;
	}

	public String getLecCode() {
		return lecCode;
	}

	public void setLecCode(String lecCode) {
		this.lecCode = lecCode;
	}

	public String getLecName() {
		return lecName;
	}

	public void setLecName(String lecName) {
		this.lecName = lecName;
	}

	public int getId() {
		return Id;
	}

	public List<Semester> getListSemester() {
		return listSemester;
	}

	public void setListSemester(List<Semester> listSemester) {
		this.listSemester = listSemester;
	}
	
	
}
