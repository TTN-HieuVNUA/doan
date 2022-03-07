package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import convertHtmlToObject.RawData;
import convertHtmlToObject.RawScheduleDetail;
import convertHtmlToObject.RawStudent;
import dao.StudentDao;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import time.TimeHandling;

public class HomeController1 extends Thread implements Initializable {

	@FXML
	private Label weekName;

	@FXML
	private Label titleWorkDetail;

	@FXML
	private AnchorPane scollSchedulePane;

	@FXML
	private AnchorPane scollWorkDetail;

	@FXML
	private FontAwesomeIconView toggerOff;

	@FXML
	private FontAwesomeIconView user;

	@FXML
	private DatePicker calendar;

	@FXML
	private ChoiceBox<String> listWeek;

	@FXML
	private TableView<RawStudent> tableListStudent;

	@FXML
	private TableColumn<RawStudent, String> col_stdCode;

	@FXML
	private TableColumn<RawStudent, String> col_surName;

	@FXML
	private TableColumn<RawStudent, String> col_stdName;

	@FXML
	private TableColumn<RawStudent, String> col_classCode;

	private static List<GridPane> listBlock = new ArrayList<GridPane>();

	private ObservableList<String> listWeeks = FXCollections.observableArrayList();

	private ObservableList<Student> listStd = FXCollections.observableArrayList();

	private static ObservableList<String> listLecturers = FXCollections.observableArrayList();

	private static List<RawScheduleDetail> ListRawSchedules = new ArrayList<RawScheduleDetail>();

	private static ObservableList<RawStudent> listRawStudent = FXCollections.observableArrayList();

	private static List<RawStudent> listStudent = new ArrayList<RawStudent>();

	private RawData rawData = new RawData();

	private static int week = 1;

	@FXML
	void changeToDarkMode(MouseEvent event) {
		if (toggerOff.getGlyphName().equalsIgnoreCase("TOGGLE_OFF")) {
			toggerOff.setGlyphName("TOGGLE_ON");
			scollSchedulePane.setStyle("-fx-background-color: #373b40;");
			scollWorkDetail.setStyle("-fx-background-color: #373b40;");

		} else {
			toggerOff.setGlyphName("TOGGLE_OFF");
			scollSchedulePane.setStyle("-fx-background-color: none;");
			scollWorkDetail.setStyle("-fx-background-color: white;");
		}
	}

	@FXML
	void zoomTable(MouseEvent event) throws IOException {
		listStd.clear();
		StudentDao studentDao = new StudentDao();
		for (Student s : studentDao.getListStudents()) {
			listStd.add(s);
		}

//		ExcelToObjectStudent excelToObjectStudent = new ExcelToObjectStudent();
//		for (Student s : excelToObjectStudent.getListStudentByExcel("dssv.xlsx")) {
//			listStd.add(s);
//		}
		Stage newStage = new Stage();

		VBox vBox = new VBox();
		vBox.setPrefHeight(600);
		vBox.setPrefWidth(1000);
		vBox.setMaxHeight(600);
		vBox.setMaxWidth(2000);

		TableView<Student> tableView = new TableView<Student>();
		tableView.setPrefWidth(800);
		tableView.setPrefHeight(500);
		TableColumn<Student, Integer> tableColumn = new TableColumn<Student, Integer>();
		TableColumn<Student, String> tableColumn1 = new TableColumn<Student, String>();
		TableColumn<Student, String> tableColumn2 = new TableColumn<Student, String>();

		tableColumn.setText("msv");
		tableColumn1.setText("họ tên");
		tableColumn2.setText("mã lớp");
		tableColumn.setPrefWidth(80);
		tableColumn1.setPrefWidth(150);
		tableColumn2.setPrefWidth(80);
		tableView.getColumns().add(tableColumn);
		tableView.getColumns().add(tableColumn1);
		tableView.getColumns().add(tableColumn2);

		for (int i = 1; i < 7; i++) {
			TableColumn<Student, String> tableColumn3 = new TableColumn<Student, String>();
			TableColumn<Student, String> tableColumn4 = new TableColumn<Student, String>();
			TableColumn<Student, String> tableColumn5 = new TableColumn<Student, String>();
			tableColumn3.setText("tuần " + i);
			tableColumn4.setText("thu 2");
			tableColumn5.setText("thu 3");
			tableColumn3.setPrefWidth(100);

			// column thứ
			tableColumn4.setPrefWidth(50);
			tableColumn5.setPrefWidth(50);
			tableColumn3.getColumns().addAll(tableColumn4, tableColumn5);
			tableView.getColumns().add(tableColumn3);

			if (i < week) {
				tableColumn4.setCellFactory(tc -> new CheckBoxTableCell<>());
				tableColumn5.setCellFactory(tc -> new CheckBoxTableCell<>());
				tableColumn3.setStyle("-fx-background-color:#cfcfcf");
				tableColumn4.setStyle("-fx-background-color:#cfcfcf");
				tableColumn5.setStyle("-fx-background-color:#cfcfcf");
			}
			if (i == week) {
				if (tableColumn4.getText().equals("thu 3")) {
					tableColumn4.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
					tableColumn5.setCellFactory(tc -> new CheckBoxTableCell<>());
					tableColumn5.setStyle("-fx-background-color:#cfcfcf");
				} else if (tableColumn5.getText().equals("thu 3")) {
					tableColumn5.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
					tableColumn4.setCellFactory(tc -> new CheckBoxTableCell<>());
					tableColumn4.setStyle("-fx-background-color:#cfcfcf");
				}
			}
		}

		tableColumn.setCellValueFactory(new PropertyValueFactory<>("stdCode"));
		tableColumn1.setCellValueFactory(new PropertyValueFactory<>("stdName"));
		tableColumn2.setCellValueFactory(new PropertyValueFactory<>("classCode"));
		tableView.setItems(listStd);
		vBox.getChildren().add(tableView);

		Button button = new Button("điểm danh");
		vBox.getChildren().add(button);

		// điểm danh và kiểm tra
		button.setOnAction((ActionEvent) -> {
			for (Student item : listStd) {
				if (item.getCheckBox().isSelected() == true) {
					System.out.println(item);
				}
			}
		});
		tableView.setRowFactory(tv -> {
			TableRow<Student> row = new TableRow<>();
			row.setOnMouseClicked(action -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("chọn chức năng");
					Button button2 = new Button("+");
					Button button3 = new Button("-");
					HBox hBox = new HBox();
					hBox.getChildren().add(button2);
					hBox.getChildren().add(button3);
					alert.setGraphic(hBox);
					alert.setHeaderText("điểm cộng trừ");
					alert.show();
				}
			});
			return row;
		});
		Scene stageScene = new Scene(vBox, 1000, 600);
		newStage.setScene(stageScene);
		newStage.show();
	}

	@FXML
	void openBoxLecturerCode(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("nhập mã giảng viên");
		TextField textField = new TextField();
		HBox hBox = new HBox();
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.setItems(listLecturers);
		choiceBox.getSelectionModel().selectFirst();
		hBox.getChildren().add(textField);
		hBox.getChildren().add(choiceBox);
		alert.setGraphic(hBox);
		alert.setHeaderText("mã giảng viên");

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK) {
			System.out.println("oke");
		}
	}

	@FXML
	void nextWeek(MouseEvent event) {
		week++;
		setBlock(String.valueOf(week), "2022/01/24-2022/01/30");
		TimeHandling timeHandling = new TimeHandling();
		String time = "tuần " + week + "(";
		time = time + timeHandling.startWeekToEnd(String.valueOf(week)) + ")";
		setBlock(String.valueOf(week), timeHandling.startWeekToEnd(String.valueOf(week)));
		weekName.setText(time);
	}

	@FXML
	void preWeek(MouseEvent event) {
		if (week > 1) {
			week--;
			setBlock(String.valueOf(week), "2022/01/24-2022/01/30");
			TimeHandling timeHandling = new TimeHandling();
			String time = "tuần " + week + "(";
			time = time + timeHandling.startWeekToEnd(String.valueOf(week)) + ")";
			setBlock(String.valueOf(week), timeHandling.startWeekToEnd(String.valueOf(week)));
			weekName.setText(time);
		}
	}

	@FXML
	void viewDay(ActionEvent event) {
		LocalDate value = calendar.getValue();
		titleWorkDetail.setText("chi tiết công việc ngày: " + value);
		if (listWeek.getValue() != null) {
			String s[] = listWeek.getValue().split(" ");
			setBlock(s[1], "2022/01/24-2022/01/30");
		}
	}

	@FXML
	void switchToLectures(MouseEvent event) {
		Parent root;
		Scene scene;
		Stage stage;
		try {
			root = FXMLLoader.load(getClass().getResource("../views/LecturesFile.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../views/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		geListRawData();
		setStyleBlock();
		setWidthAndHeightScoll();
		setBlock("1", "2022/01/24-2022/01/30");
		setListWeek();
		setCalendar();
		toolTip();
		setListLecturers();
		setLableWeek("2022/01/24-2022/01/30");
		setScoreWorkDetail();

	}

	public void setLableWeek(String dateToDate) {
		weekName.setText("tuần 1:" + "(" + dateToDate + ")");
	}

	public void setStyleBlock() {
		HBox hBox = new HBox();
		for (int i = 0; i < 7; i++) {
			GridPane gridPane = new GridPane();
			hBox.setMargin(gridPane, new Insets(20, 20, 0, 20));
			gridPane.setPrefHeight(250);
			gridPane.setPrefWidth(310);
			gridPane.setStyle("-fx-background-radius: 15px;\r\n" + "	-fx-background-color: white;\r\n"
					+ "	-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);\r\n"
					+ "	-fx-text-fill: #63bca9;");
			gridPane.setId("dayofweek");
			hBox.getChildren().add(gridPane);

			listBlock.add(gridPane);
		}
		scollSchedulePane.getChildren().add(hBox);
	}

	public void setWidthAndHeightScoll() {
		scollSchedulePane.setPrefHeight(280);
		scollSchedulePane.setMaxHeight(2000);
		scollSchedulePane.setMaxWidth(5000);

		scollWorkDetail.setPrefHeight(1000);
		scollWorkDetail.setPrefWidth(1000);
		scollWorkDetail.setMaxWidth(2000);
		scollWorkDetail.setMaxHeight(5000);
	}

	// set thuoc tinh cho cac khoi tu thu 2 den chu nhat
	public void setBlock(String week, String dateToDate) {
		scollSchedulePane.getChildren().clear();
		listBlock.clear();
		setStyleBlock();
		TimeHandling timeHandling = new TimeHandling();
		List<String> listWeeks = new ArrayList<String>();
		listWeeks = timeHandling.getListDayOnWeek(week);
		for (int i = 0; i < 7; i++) {
			VBox vBox = new VBox();
			vBox.setAlignment(Pos.BOTTOM_CENTER);
			Label label = new Label();
			label.setText(listWeeks.get(i));
			label.setPrefWidth(2000);
			label.setPrefHeight(30);
			label.setAlignment(Pos.CENTER);
			label.setTextFill(Color.rgb(0, 160, 134));

			TextField textField = new TextField();
			textField.setPromptText("ghi chú...!");
			textField.setPrefWidth(2000);
			textField.setPrefHeight(30);
			textField.setStyle("-fx-background-color: transparent");
			vBox.setMargin(textField, new Insets(0, 10, 10, 10));

			ScrollPane scrollPane = new ScrollPane();
			AnchorPane anchorPane = new AnchorPane();
			scrollPane.setContent(anchorPane);
			anchorPane.setPrefWidth(300);
			anchorPane.setPrefHeight(300);

			// set du lieu cho cac thu trong tuan
			VBox vboxMain = new VBox();
			anchorPane.getChildren().add(vboxMain);

			/// dung vong lap de set gia tri
			for (RawScheduleDetail p : ListRawSchedules) {
				if (p.getTime().after(getStartWeek(dateToDate)) && p.getTime().before(getEndWeek(dateToDate))
						|| p.getTime().equals(getStartWeek(dateToDate))) {
					switch (p.getDayOfWeek()) {
					case "Hai":
						if (i == 0) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});

							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;
					case "Ba":
						if (i == 1) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});
							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;
					case "Tư":
						if (i == 2) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});

							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;
					case "Năm":
						if (i == 3) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});

							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;
					case "Sáu":
						if (i == 4) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});
							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;
					case "Bảy":
						if (i == 5) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});

							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;
					case "CN":
						if (i == 6) {
							HBox hBox = new HBox();
							Separator separator = new Separator();
							separator.setMinSize(200, 10);
							vboxMain.getChildren().add(hBox);
							vboxMain.getChildren().add(separator);
							hBox.setPadding(new Insets(20, 0, 0, 0));
							VBox vBox2 = new VBox();
							VBox vBox3 = new VBox();
							hBox.getChildren().add(vBox2);
							hBox.getChildren().add(vBox3);

							// cot ten thuoc tinh
							Label subCode = new Label("mã môn học");
							Label subName = new Label("tên môn học");
							Label subGroup = new Label("nhóm môn học");
							Label classCode = new Label("mã lớp");
							Label practiceGroup = new Label("nhóm thực hành");
							Label startTimeToEnd = new Label("phòng học: ");
							Label location = new Label("thời gian từ: ");
							Label listStd = new Label("danh sách sinh viên: ");
							ArrayList<Label> proslist = new ArrayList<Label>();
							proslist.add(subCode);
							proslist.add(subName);
							proslist.add(subGroup);
							proslist.add(classCode);
							proslist.add(practiceGroup);
							proslist.add(startTimeToEnd);
							proslist.add(location);
							proslist.add(listStd);
							for (Label l : proslist) {
								l.setPadding(new Insets(0, 0, 0, 10));
							}
							vBox2.getChildren().addAll(proslist);

							// set cot gia tri
							Label subCodeData = new Label(p.getSubCode());
							Label subNameData = new Label(p.getSubName());
							Label subGroupData = new Label(String.valueOf(p.getGroupSub()));
							Label classCodeData = new Label(p.getClassCode());
							Label practiceGroupData = new Label(String.valueOf(p.getPractiveTeam()));
							Label startToEndData = new Label(String.valueOf(p.getStartTimeToEnd()));
							Label locationData = new Label(String.valueOf(p.getLocation()));
							Label listStdData = new Label("xem danh sách");
							listStdData.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
								setTableStudent(p.getLinkListStd());
							});
							ArrayList<Label> listLabe = new ArrayList<Label>();
							listLabe.add(subCodeData);
							listLabe.add(subNameData);
							listLabe.add(subGroupData);
							listLabe.add(classCodeData);
							listLabe.add(practiceGroupData);
							listLabe.add(startToEndData);
							listLabe.add(locationData);
							listLabe.add(listStdData);
							for (Label l : listLabe) {
								l.setPadding(new Insets(0, 0, 0, 20));
							}
							vBox3.getChildren().addAll(listLabe);
						}
						break;

					default:
						break;
					}
				}
			}
			/// ket thuc set thuoc tinh

			vBox.getChildren().add(label);
			vBox.getChildren().add(scrollPane);
			vBox.getChildren().add(textField);

			listBlock.get(i).getChildren().add(vBox);
		}
	}

	public void setStyleScoll() {

	}

	public void setListWeek() {
		for (int i = 1; i < 27; i++) {
			listWeeks.add("tuần " + i);
		}
		listWeek.setItems(listWeeks);
	}

	public void setCalendar() {
		calendar.setShowWeekNumbers(true);
	}

	public void toolTip() {
		Tooltip tooltip = new Tooltip("Trần Trung Hiếu");
		tooltip.setPrefHeight(50);
		tooltip.setPrefWidth(270);
		Tooltip.install(user, tooltip);
	}

	public void showBoxLecturer() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stage newStage = new Stage();
		VBox comp = new VBox();
		Button button = new Button();
		button.setStyle("-fx-background-color:red; -fx-background-radius:50%;");

		Button button1 = new Button();
		Button button2 = new Button();
		button1.setText("to");
		button2.setText("okes");
		Label label = new Label("chọn bảng màu");
		comp.getChildren().add(label);
		HBox hBox = new HBox();
		hBox.getChildren().add(button);
		hBox.getChildren().add(button1);
		hBox.getChildren().add(button2);
		comp.getChildren().add(hBox);

		Scene stageScene = new Scene(comp, 400, 400);
		newStage.setScene(stageScene);
		newStage.show();
	}

	public void setListLecturers() {
		listLecturers.add("cnp09");
		listLecturers.add("cnp08");
		listLecturers.add("cnp07");

	}

	public void setScoreWorkDetail() {
		VBox vBox = new VBox();
		scollWorkDetail.getChildren().add(vBox);

		for (int i = 0; i < 3; i++) {
			HBox hBox = new HBox();
			Separator separator = new Separator();
			separator.setPrefSize(550, 5);
			vBox.getChildren().add(hBox);
			vBox.getChildren().add(separator);

			VBox vBoxProsName = new VBox();
			hBox.getChildren().add(vBoxProsName);
			Label subCode = new Label("mã môn học");
			Label subName = new Label("tên môn học");
			Label subGroup = new Label("nhóm môn học");
			Label classCode = new Label("mã lớp");
			Label practiceGroup = new Label("nhóm thực hành");
			ArrayList<Label> proslist = new ArrayList<Label>();
			proslist.add(subCode);
			proslist.add(subName);
			proslist.add(subGroup);
			proslist.add(classCode);
			proslist.add(practiceGroup);
			for (Label l : proslist) {
				l.setPadding(new Insets(0, 0, 0, 10));
			}
			vBoxProsName.getChildren().addAll(proslist);
			hBox.setPadding(new Insets(20, 0, 0, 0));
		}

	}

	public void geListRawData() {
		try {
			ListRawSchedules = rawData.getListRawScheduleDetails("cnp03");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Timestamp getStartWeek(String dateToDate) {
		try {
			String week[] = dateToDate.split("-");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date parsedDate = dateFormat.parse(week[0]);
			Timestamp timestamps = new java.sql.Timestamp(parsedDate.getTime());
			return timestamps;
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public Timestamp getEndWeek(String dateToDate) {
		try {
			String week[] = dateToDate.split("-");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date parsedDate = dateFormat.parse(week[1]);
			Timestamp timestamps = new java.sql.Timestamp(parsedDate.getTime());
			return timestamps;
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public void setTableStudent(String link) {
		listStudent.clear();
		listRawStudent.clear();
		listStudent = rawData.getAListStudent("http://daotao.vnua.edu.vn/" + link);
		for (RawStudent s : listStudent) {
			listRawStudent.add(s);
		}
		tableListStudent.setItems(listRawStudent);
		col_classCode.setCellValueFactory(new PropertyValueFactory<>("classCode"));
		col_surName.setCellValueFactory(new PropertyValueFactory<>("surName"));
		col_stdCode.setCellValueFactory(new PropertyValueFactory<>("stdCode"));
		col_stdName.setCellValueFactory(new PropertyValueFactory<>("name"));
	}
}
