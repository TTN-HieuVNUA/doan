package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.Student;
import javafx.scene.control.CheckBox;

public class ExcelToObjectStudent {

	public List<Student> getListStudentByExcel(String url) throws IOException {
		List<Student> listStudent = new ArrayList<Student>();
		FileInputStream file = new FileInputStream(new File(url));
		
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Row row;
		DataFormatter dataFormatter = new DataFormatter();
		for (int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			Student student = new Student();
			student.setStdCode(Integer.valueOf(dataFormatter.formatCellValue(row.getCell(1))));
			student.setClassCode(row.getCell(4).getStringCellValue());
			student.setStdName(row.getCell(2).getStringCellValue()+" "+ row.getCell(3).getStringCellValue());
			student.setCheckBox(null); // null de chay trong ham main
			listStudent.add(student);
		}
		return listStudent;
	}
	
	public static void main(String[] args) throws IOException {
		ExcelToObjectStudent excelToObjectStudent = new ExcelToObjectStudent();
		
		excelToObjectStudent.getListStudentByExcel("C://Users//Hieu Johnny//Documents//dssv.xlsx").forEach(p->{
			System.out.println(p.toString());
		});
	}
}
