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
import model.Instructor;
import utils.BackUpRestoreTools;

public class AddInstructorView {
	//TODO
	// INPUT VALIDATION 
	
	private VBox root;
	private PersonBag pBag;
	private TextField firstNameField, lastNameField, salaryField, rankField;
	private Button add,back;
	//private Label firstNameLabel, lastNameLabel, salaryLabel, rankLabel;
	
	public AddInstructorView(PersonBag pBag) {
		this.pBag = pBag;
		
		firstNameField = new TextField();
		firstNameField.setPromptText("First Name");
		
		lastNameField = new TextField();
		lastNameField.setPromptText("Last Name");
		
		salaryField = new TextField();
		salaryField.setPromptText("salary");
		
		rankField = new TextField();
		rankField.setPromptText("Rank");
		
		add = new Button("ADD");
		back = new Button("BACK");
		
		root = new VBox();
		
		add.setOnMouseClicked(e ->{
			String firstName = firstNameField.getText().strip(),
				   lastName = lastNameField.getText().strip(), 
				   rank = rankField.getText().strip();
			
			Double salary = Double.valueOf(salaryField.getText().strip());
			
			Instructor newInstructor = new Instructor(
					new Name(firstName,lastName),
					rank,
					salary
					);
			
			pBag.insert(newInstructor);
			
			
			BackUpRestoreTools.backupPersonBag(pBag);
			
			back.fire();
		});
		
		back.setOnAction( e->{
			Main.stage.setScene(new Scene(new InstructorView(Main.pBag).getRoot()));
		});
		
		root.getChildren().addAll(firstNameField,lastNameField,salaryField,rankField,add,back);
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
