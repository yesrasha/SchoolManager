package view;

import app.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Name;
import model.PersonBag;
import model.Student;
import utils.BackUpRestoreTools;

public class AddStudentView {
	//TODO
	// INPUT VALIDATION 
	
	private VBox root;
	private PersonBag pBag;
	private TextField firstNameField, lastNameField, gpaField, majorField;
	private Button add,back;
	//private Label firstNameLabel, lastNameLabel, gpaLabel, majorLabel;
	
	public AddStudentView(PersonBag pBag) {
		this.pBag = pBag;
		
		firstNameField = new TextField();
		firstNameField.setPromptText("First Name");
		
		lastNameField = new TextField();
		lastNameField.setPromptText("Last Name");
		
		gpaField = new TextField();
		gpaField.setPromptText("gpa");
		
		majorField = new TextField();
		majorField.setPromptText("major");
		
		add = new Button("ADD");
		back = new Button("BACK");
		
		root = new VBox();
		
		add.setOnMouseClicked(e ->{
			String firstName = firstNameField.getText().strip(),
				   lastName = lastNameField.getText().strip(), 
				   major = majorField.getText().strip();
			
			Double gpa = Double.valueOf(gpaField.getText().strip());
			
			Student newStudent = new Student(
					new Name(firstName,lastName),
					major,
					gpa
					);
			
			pBag.insert(newStudent);
			
			
			BackUpRestoreTools.backupPersonBag(pBag);
			
			back.fire();
		});
		
		back.setOnAction( e->{
			Main.stage.setScene(new Scene(new StudentView(Main.pBag).getRoot()));
		});
		
		root.getChildren().addAll(firstNameField,lastNameField,gpaField,majorField,add,back);
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
