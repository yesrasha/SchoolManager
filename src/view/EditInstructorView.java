package view;

import app.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.PersonBag;
import model.Instructor;
import utils.BackUpRestoreTools;

public class EditInstructorView {
	
	private VBox root;
	private Instructor instructor;
	private PersonBag pBag;
	private TextField firstNameField, lastNameField, salaryField, rankField;
	private Button updateBtn;
	//private Label firstNameLabel, lastNameLabel, salaryLabel, majorLabel;
	
	public EditInstructorView(Instructor instructor,PersonBag pBag) {
		this.pBag = pBag;
		this.instructor = instructor;
		
		firstNameField = new TextField(instructor.getName().getFirstName());
		lastNameField = new TextField(instructor.getName().getLastName());
		salaryField = new TextField(formatDouble(instructor.getSalary()));
		rankField = new TextField(instructor.getRank());
		updateBtn = new Button("Update");
		
		root = new VBox();
		
		updateBtn.setOnMouseClicked(e ->{
			
			instructor.getName().setFirstName(firstNameField.getText());
			instructor.getName().setLastName(lastNameField.getText());
			instructor.setSalary(Double.valueOf(salaryField.getText()));
			instructor.setRank(rankField.getText());
			
			BackUpRestoreTools.backupPersonBag(pBag);
			
			Main.stage.setScene(new Scene(new InstructorView(Main.pBag).getRoot()));
		});
		
		root.getChildren().addAll(firstNameField,lastNameField,salaryField,rankField,updateBtn);
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
