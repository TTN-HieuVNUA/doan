package entity;

import javafx.scene.control.CheckBox;

public class Student {

	private int stdCode;
	
	private String stdName;
	
	private String classCode;

	private CheckBox checkBox ;

	public Student() {
		
	}

	public Student(int stdCode, String stdName, String classCode) {
		this.stdCode = stdCode;
		this.stdName = stdName;
		this.classCode = classCode;
	}

	public Student(int stdCode, String stdName, String classCode, CheckBox checkBox) {
		this.stdCode = stdCode;
		this.stdName = stdName;
		this.classCode = classCode;
		this.checkBox = checkBox;
	}

	public int getStdCode() {
		return stdCode;
	}

	public void setStdCode(int stdCode) {
		this.stdCode = stdCode;
	}

	public String getStdName() {
		return stdName;
	}

	public void setStdName(String stdName) {
		this.stdName = stdName;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	
	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	@Override
	public String toString() {
		return "Student [stdCode=" + stdCode + ", stdName=" + stdName + ", classCode=" + classCode + ", checkBox="
				+ checkBox + "]";
	}
	
	
	
}
