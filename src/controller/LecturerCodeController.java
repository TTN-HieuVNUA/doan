package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import time.StartDate;

public class LecturerCodeController implements Initializable {

	@FXML
	private TextField txtCode;

	@FXML
	private Label lecCodeDefault;

	@FXML
	private ChoiceBox<String> listLecCode;

	@FXML
	void Confirm(ActionEvent event) throws IOException {
		FileInputStream in = new FileInputStream("information.properties");
		Properties props = new Properties();
		props.load(in);

		FileOutputStream out = new FileOutputStream("information.properties");
		
		// neu ma giang vien khong rong va co ton tai ma giang vien nay tren trang dao tao
		if (!txtCode.getText().equals("") && checkLecCode(txtCode.getText()) == true) {
			// noi them 1 ma giang vien vao, neu trong file chua co ma giang vien nay
			if (checkLecCodeInFile(props, txtCode.getText())==false) {
				props.setProperty("lecturerCode", props.getProperty("lecturerCode")+","+txtCode.getText());
				// lay thoi khoa bieu tren trang dao tao va xu ly
			}
			// neu ma giang vien co trong file va thoi khoa bieu thay doi
			else if (checkLecCodeInFile(props, txtCode.getText())==true && checkChangeUpdateAt()==true) {
				// lay thoi khoa bieu tren dao tao va update lai csdl
				
			}
			else {
				
			}
		}

		else if (!txtCode.getText().equals("") && checkLecCode(txtCode.getText()) == false) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("error");
			alert.setContentText("không tìm thấy mã gv hoặc xảy ra lỗi");
			alert.show();
		}

		else if (props.getProperty("lecturerCode").equals("") && txtCode.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("error");
			alert.setContentText("hãy nhập mã giảng viên");
			alert.show();
		}

		else {
			String code = listLecCode.getValue();
			if (checkChangeUpdateAt()==true) {
				//lay code va update lai csdl
				
			}
			else {
				// dung code lay du lieu trong database ra de xu ly
				
			}
		}
		props.store(out, null);
		out.close();
	}

	@FXML
    void checkKey(KeyEvent event) {
		listLecCode.setDisable(true);
		if (!txtCode.getText().equals("")) {
			listLecCode.setDisable(true);
		}
		else {
			listLecCode.setDisable(false);
		}
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FileInputStream in = new FileInputStream("information.properties");
			Properties props = new Properties();
			props.load(in);
			if (!props.getProperty("lecturerCode").equals("")) {
				String lecCode = props.getProperty("lecturerCode");
				String listLec[] = lecCode.split(",");
				ObservableList<String> listString = FXCollections.observableArrayList();
				for (int i = 0; i < listLec.length; i++) {
					listString.add(listLec[i]);
				}
				listLecCode.setItems(listString);
			} else {
				lecCodeDefault.setText("");
				listLecCode.setDisable(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			checkNullStartDate();
			checkNullUpdateAt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//kiem tra ma gv co ton tai tren web khong
	private boolean checkLecCode(String lecCode) {
		Document doc;
		try {
			doc = Jsoup.connect("http://daotao.vnua.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id=" + lecCode).get();
			Elements els = doc.getElementsByClass("grid-roll2");
			String s = els.toString();
			Document docs = Jsoup.parse(s);
			if (docs.getElementsByTag("table").size() > 0) {
				return true;
			}
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	private boolean checkLecCodeInFile (Properties props, String code) {
		String s[] = props.getProperty("lecturerCode").split(",");
		for (int i = 0; i < s.length; i++) {
			if (s[i].equalsIgnoreCase(code)) {
				return true;
			}
		}
		return false;
	}
	
	// su dung cho lan mo ung dung lan dau tien
	private void checkNullStartDate () throws IOException {
			StartDate startDate = new StartDate();
			String sD = startDate.getStartDate();
			FileInputStream in = new FileInputStream("information.properties");
			Properties props = new Properties();
			props.load(in);
			FileOutputStream out = new FileOutputStream("information.properties");
			if (props.getProperty("startdate").equals("")) {
				props.setProperty("startdate", sD);
			}
			props.store(out, null);
			out.close();
	}
	
	// su dung cho lan mo ung dung lan dau tien
	private void checkNullUpdateAt () throws IOException {
		StartDate startDate = new StartDate();
		String sD = startDate.getUpdateAt();
		FileInputStream in = new FileInputStream("information.properties");
		Properties props = new Properties();
		props.load(in);
		FileOutputStream out = new FileOutputStream("information.properties");
		if (props.getProperty("updateAt").equals("")) {
			props.setProperty("updateAt", sD);
		}
		props.store(out, null);
		out.close();
	}
	
	//neu thoi khoa bieu co thay doi thi tra ve true
	private boolean checkChangeUpdateAt () throws IOException {
		StartDate startDate = new StartDate();
		FileInputStream in = new FileInputStream("information.properties");
		Properties props = new Properties();
		props.load(in);
		FileOutputStream out = new FileOutputStream("information.properties");
		if (!startDate.getStartDate().equals(props.getProperty("updateAt"))) {
			props.store(out, null);
			out.close();
			return true;
		}
		props.store(out, null);
		out.close();
		return false;
	}
}
