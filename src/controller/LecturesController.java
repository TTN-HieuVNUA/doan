package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class LecturesController {

	@FXML
	private FontAwesomeIconView user;

	@FXML
	private FontAwesomeIconView toggerOff;

	@FXML
	private GridPane mainPane;

	@FXML
	private FontAwesomeIconView iconSesrch;

	private static Path urlFile = null;
	
	@FXML
	void changeToDarkMode(MouseEvent event) {
		if (toggerOff.getGlyphName().equalsIgnoreCase("TOGGLE_OFF")) {
			toggerOff.setGlyphName("TOGGLE_ON");
			mainPane.setStyle("-fx-background-color: #3a3b3c");
			iconSesrch.setFill(Color.WHITE);
		} else {
			toggerOff.setGlyphName("TOGGLE_OFF");
			mainPane.setStyle("-fx-background-color: none");
			iconSesrch.setFill(Color.BLACK);
		}
	}

	@FXML
	void openBoxLecturerCode(MouseEvent event) {

	}

	@FXML
	void switchToHome(MouseEvent event) {
		Parent root;
		Scene scene;
		Stage stage;
		try {
			root = FXMLLoader.load(getClass().getResource("../views/home1.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../views/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void chooseFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(null);
		if (f != null) {
			String s = f.getName();
			urlFile = f.toPath();
			System.out.println(urlFile);
		}
	}
}
