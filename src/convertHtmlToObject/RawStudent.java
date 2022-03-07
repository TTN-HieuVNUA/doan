package convertHtmlToObject;

public class RawStudent {

	private String stdCode;
	
	private String surName;
	
	private String name;
	
	private String classCode;
	
	private String className;

	public RawStudent() {
	
	}

	public String getStdCode() {
		return stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "stdCode=" + stdCode + ", surName=" + surName + ", name=" + name + ", classCode=" + classCode
				+ ", className=" + className + "\n";
	}
	
	
}
