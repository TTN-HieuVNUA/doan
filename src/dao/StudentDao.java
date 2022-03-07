package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Student;
import javafx.scene.control.CheckBox;

public class StudentDao {

	public List<Student> getListStudents(){
		List<Student> arrayList = new ArrayList<Student>();
		Student student = new Student(637630, "tran van hieu", "K63ATTT", new CheckBox());
		Student student1 = new Student(621145, "tran thi lan", "K62CNPM", new CheckBox());
		Student student2 = new Student(637648, "mai thi phuong", "K63HTTT", new CheckBox());
		Student student4 = new Student(637629, "van minh hieu", "K63CNPM", new CheckBox());
		arrayList.add(student4);
		arrayList.add(student2);
		arrayList.add(student1);
		arrayList.add(student);
		return arrayList;
	}
}
