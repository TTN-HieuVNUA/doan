package com.hieu.entity;

import java.util.Arrays;

public class RawSchedule {
	
	private String subCode;
	
	private String subName;
	
	private int groupSub;
	
	private String classCode;
	
	private String practiveTeam;
	
	private String dayOfWeek;
	
	private int lessonStart;
	
	private int numOfLesson;
	
	private String location;
	
	private String week;

	private String linkListStd;
	
	public RawSchedule() {
		
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getLessonStart() {
		return lessonStart;
	}

	public void setLessonStart(int lessonStart) {
		this.lessonStart = lessonStart;
	}

	public int getNumOfLesson() {
		return numOfLesson;
	}

	public void setNumOfLesson(int numOfLesson) {
		this.numOfLesson = numOfLesson;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getLinkListStd() {
		return linkListStd;
	}

	public void setLinkListStd(String linkListStd) {
		this.linkListStd = linkListStd;
	}

	public int getGroupSub() {
		return groupSub;
	}

	public void setGroupSub(int groupSub) {
		this.groupSub = groupSub;
	}

	public String getPractiveTeam() {
		return practiveTeam;
	}

	public void setPractiveTeam(String practiveTeam) {
		this.practiveTeam = practiveTeam;
	}

	@Override
	public String toString() {
		return "subCode=" + subCode + ", subName=" + subName + ", groupSub=" + groupSub + ", classCode="
				+ classCode + ", practiveTeam=" + practiveTeam + ", dayOfWeek=" + dayOfWeek + ", lessonStart="
				+ lessonStart + ", numOfLesson=" + numOfLesson + ", location=" + location + ", week=" + week
				+ ", linkListStd=" + linkListStd + "\n";
	}

	
	
}
