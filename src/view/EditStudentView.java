package view;

import app.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.PersonBag;
import model.Student;
import utils.BackUpRestoreTools;

public class EditStudentView {
	
	private VBox root;
	private Student student;
	private PersonBag pBag;
	private TextField firstNameField, lastNameField, gpaField, majorField;
	private Button updateBtn;
	//private Label firstNameLabel, lastNameLabel, gpaLabel, majorLabel;
	
	public EditStudentView(Student student,PersonBag pBag) {
		this.pBag = pBag;
		this.student = student;
		
		firstNameField = new TextField(student.getName().getFirstName());
		lastNameField = new TextField(student.getName().getLastName());
		gpaField = new TextField(formatDouble(student.getGpa()));
		majorField = new TextField(student.getMajor());
		updateBtn = new Button("Update");
		
		root = new VBox();
		
		updateBtn.setOnMouseClicked(e ->{
			
			student.getName().setFirstName(firstNameField.getText());
			student.getName().setLastName(lastNameField.getText());
			student.setGpa(Double.valueOf(gpaField.getText()));
			student.setMajor(majorField.getText());
			
			BackUpRestoreTools.backupPersonBag(pBag);
			
			Main.stage.setScene(new Scene(new StudentView(Main.pBag).getRoot()));
		});
		
		root.getChildren().addAll(firstNameField,lastNameField,gpaField,majorField,updateBtn);
		root.setSpacing(4);
		
		root.setPadding(new Insets(120,50,50,50));
		
		
		
	}
	
	public String formatDouble(double d) {
		return String.format("%.1f", d);
	}
	
	public VBox getRoot() {
		return root;
	}
}
