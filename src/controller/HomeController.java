package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import time.TimeHandling;
import time.Today;

public class HomeController implements Initializable {
	@FXML
	private GridPane greenpane;

	@FXML
	private GridPane homepane;

	@FXML
	private Pane paneSetting;

	@FXML
	private GridPane dayofw;

	@FXML
	private GridPane sundayPane;

	@FXML
	private GridPane thursdayPane;

	@FXML
	private Button tkbs;

	@FXML
	private Label txtTitleTime;

	@FXML
	private Label monday;

	@FXML
	private Label tuesday;

	@FXML
	private Label wednesday;

	@FXML
	private Label thursday;

	@FXML
	private Label friday;

	@FXML
	private Label saturday;

	@FXML
	private Label sunday;

	@FXML
	private AnchorPane scoll1;
	@FXML
	private AnchorPane scoll2;
	@FXML
	private AnchorPane scoll3;
	@FXML
	private AnchorPane scoll4;
	@FXML
	private AnchorPane scoll5;
	@FXML
	private AnchorPane scoll6;
	@FXML
	private AnchorPane scoll7;

	@FXML
	private TableView<String> tableWeek;

	@FXML
	private DatePicker datePicker;

	@FXML
	private HBox toggerBackgrounfColor;
	
    @FXML
    private FontAwesomeIconView toggerLightMode;

	@FXML
	private TableColumn<String, String> col_week;

	private static int week = 1;

	private ObservableList<String> listWeek = FXCollections.observableArrayList();

	@FXML
	void dsl(ActionEvent event) {
		greenpane.toFront();
	}

	@FXML
	void tkb(ActionEvent event) {
		homepane.toFront();
		LocalDate value = datePicker.getValue();
		System.out.println(value);
	}

	@FXML
	void clickSetting(MouseEvent event) {
		Stage newStage = new Stage();
		VBox comp = new VBox();
		Button button = new Button();
		button.setStyle("-fx-background-color:red; -fx-background-radius:50%;");
		button.setOnAction(action -> {
			homepane.setStyle("-fx-background-color:red;");
		});
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

	@FXML
	void nextWeek(MouseEvent event) {
		TimeHandling timeHandling = new TimeHandling();
		week = week + 1;
		String time = "tuần thứ " + week + " bắt đầu từ: ";
		time = time + timeHandling.startWeekToEnd(String.valueOf(week));
		txtTitleTime.setText(time);
		setDayOnWeek();
	}

	@FXML
	void preWeek(MouseEvent event) {
		TimeHandling timeHandling = new TimeHandling();
		if (week > 1) {
			week = week - 1;
			String time = "tuần thứ " + week + " bắt đầu từ: ";
			time = time + timeHandling.startWeekToEnd(String.valueOf(week));
			txtTitleTime.setText(time);
			setDayOnWeek();
		} else {

		}
	}

	@FXML
	void changeToDarkMode(MouseEvent event) {
		if (toggerLightMode.getGlyphName().equalsIgnoreCase("TOGGLE_OFF")) {
			toggerLightMode.setGlyphName("TOGGLE_ON");
			// change to dark mode
		}
		else {
			toggerLightMode.setGlyphName("TOGGLE_OFF");
			// changr to light mode
			
		}
	}

	public void toolTip() {
		Tooltip tooltip = new Tooltip("hello");
		tooltip.setPrefHeight(300);
		tooltip.setPrefWidth(400);
		Tooltip.install(dayofw, tooltip);
	}

	public void setWeek() {
		String week = "tuan ";
		for (int i = 1; i < 27; i++) {
			listWeek.add(week + i);
		}
	}

	public void loadDate() {
		tableWeek.setItems(listWeek);
	}

	public void setValueColumn() {
		col_week.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
	}

	public void setDayOnWeek() {
		TimeHandling timeHandling = new TimeHandling();
		monday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(0));
		tuesday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(1));
		wednesday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(2));
		thursday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(3));
		friday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(4));
		saturday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(5));
		sunday.setText(timeHandling.getListDayOnWeek(String.valueOf(week)).get(6));
	}

	public void setWidthPane() {
		sundayPane.setMinWidth(thursdayPane.getWidth());
	}

	public void ClickRow() {
		tableWeek.setRowFactory(tv -> {
			TableRow<String> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					TimeHandling timeHandling = new TimeHandling();
					String weeks = row.getItem();
					String s[] = weeks.split(" ");
					week = Integer.valueOf(s[1]);
					setDayOnWeek();
					String time = "tuần thứ " + week + " bắt đầu từ: ";
					time = time + timeHandling.startWeekToEnd(String.valueOf(week));
					txtTitleTime.setText(time);
				}
			});
			return row;
		});
	}

	private void setScoll() {
		scoll1.setPrefHeight(350);
		scoll2.setPrefHeight(350);
		scoll3.setPrefHeight(350);
		scoll4.setPrefHeight(350);
		scoll5.setPrefHeight(350);
		scoll6.setPrefHeight(350);
		scoll7.setPrefHeight(350);

		scoll1.setMaxHeight(1000);
		scoll2.setMaxHeight(1000);
		scoll3.setMaxHeight(1000);
		scoll4.setMaxHeight(1000);
		scoll5.setMaxHeight(1000);
		scoll6.setMaxHeight(1000);
		scoll7.setMaxHeight(1000);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		homepane.setStyle("-fx-background-color: #e3e4e6");
		setScoll();
		setWidthPane();
		setWeek();
		setValueColumn();
		loadDate();
		setDayOnWeek();
		ClickRow();
		datePicker.setShowWeekNumbers(true);
		TimeHandling timeHandling = new TimeHandling();
		String time = txtTitleTime.getText();
		time = time + timeHandling.startWeekToEnd("1");
		txtTitleTime.setText(time);
		toolTip();

		datePicker.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				System.out.println(datePicker.getValue());
			}
		});

	}

	/*
	 * set style
	 */

}
