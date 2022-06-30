package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Name;
import model.Person;
import model.PersonBag;
import model.Student;

public class StudentView {
	private PersonBag pBag;
	private VBox root;
	private TextField gpaField,nameField,outputField;
	public StudentView(PersonBag personBag) {
		this.pBag = personBag;	
		
		
		Button insertBtn = new Button("INSERT");
		insertBtn.setPrefSize(100, 30);
		
		Button searchBtn = new Button("SEARCH");
		searchBtn.setPrefSize(100, 30);
		
		Button updateBtn = new Button("UPDATE");
		updateBtn.setPrefSize(100, 30);
		
		Button removeBtn = new Button("REMOVE");
		removeBtn.setPrefSize(100, 30);
		
		Button removeAllBtn = new Button("REMOVE ALL");
		removeAllBtn.setPrefSize(100,30);
		
		Button backBtn = new Button("MAIN MENU");
		backBtn.setPrefSize(100, 30);
		
		backBtn.setOnAction(e ->{
			Main.stage.setScene(new Scene(new MainMenuView(Main.pBag,Main.tBag,Main.stage).getRoot()));
			Main.stage.show();
		});
		
		HBox btnBox = new HBox(30);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(insertBtn, searchBtn, updateBtn, removeBtn,removeAllBtn,backBtn);
		
	    nameField = new TextField();
		nameField.setPromptText("Name");
		nameField.setPrefSize(150, 30);
		
		gpaField = new TextField();
		gpaField.setPrefSize(150, 30);
		gpaField.setPromptText("GPA");
		
		HBox inputBox = new HBox(20);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(nameField, gpaField);
		
		outputField = new TextField();
		outputField.setMaxSize(300, 30);
		TextArea outputArea = new TextArea();
		outputArea.setMaxSize(600, 300);
		outputArea.setEditable(false);
		
		
		
		VBox outputBox = new VBox(20);
		outputBox.setAlignment(Pos.CENTER);
		outputBox.getChildren().addAll(outputField, outputArea);
		
		insertBtn.setOnAction(e ->{
			AddStudentView edit = new AddStudentView(pBag);
			Main.stage.setScene(new Scene(edit.getRoot(),500,500));
			Main.stage.show();
		});
		
		searchBtn.setOnAction(e ->{
			
			
			Person[] s = Main.pBag.search(combinedPredicate());
			
			StringBuilder sb = new StringBuilder();
			
			for(Person student: s) sb.append(student.getId() + "\t\t" + student.getName().getFirstName() + "\t\t" + student.getName().getLastName() + 
					"\t\t" + ((Student) student).getMajor() + "\t\t" + ((Student) student).getGpa()+"\n");
			outputArea.setText(sb.toString());
			
			
		});
		
		removeBtn.setOnAction(e ->{
			
			// remove by id
			String id = outputField.getText();
			pBag.delete(s -> s.getId().equals(id));
		});
		
		removeAllBtn.setOnAction(e ->{
			// removes everything based on the predicates
			pBag.delete(combinedPredicate());
		});
		
		
		
		searchBtn.fire();
		
		updateBtn.setOnAction(e ->{
			String id = outputField.getText();
			Student s = (Student)personBag.search(s1 -> s1.getId().equals(id))[0];
			EditStudentView edit = new EditStudentView(s,pBag);
			Main.stage.setScene(new Scene(edit.getRoot(),500,500));
			Main.stage.show();
		});
		
		

			
	
		root = new VBox(50);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(50));
		root.getChildren().addAll(inputBox, btnBox, outputBox);
		
	}
	
	public VBox getRoot() {
		return root;
	}
	
	private Predicate<Person> combinedPredicate(){
		String name = nameField.getText();
		
		// CREATE PREDICATES DEPENDING ON IF THE GPA FIELD IS EMPTY OR NOT , NAME FIELD IS EMPTY OR NOT AND VALID
		
		
		
		Predicate<Person> nameFilter = p ->{
			Student s = (Student) p;
			if(name.isBlank()) return true;
			return s.getName().toString().contains(name);
		};
		
		Predicate<Person> gpaFilter = p ->{
			Student s = (Student) p;
			String gpaString = gpaField.getText().strip();
			if(gpaString.isBlank()) return true;
			
			if(isDouble(gpaString)) return s.getGpa() == Double.valueOf(gpaString);
			
			String firstTwo = gpaString.substring(0,2);
			
			double gpaVal = Double.valueOf(gpaString.substring(2));
			
			if(firstTwo.equals("<=")) return s.getGpa() <= gpaVal;
			
			else if(firstTwo.equals(">=")) return s.getGpa() >= gpaVal;
			
			else {
				gpaVal = Double.valueOf(gpaString.substring(1));
				if(gpaString.charAt(0) == '<') return s.getGpa() < gpaVal;
			}
			
			return s.getGpa() > gpaVal;
			
			
		};
		
		Predicate<Person> majorFilter = p ->{
			
			Student s = (Student) p;
			String major = outputField.getText();
			if(major.isBlank()) return true;
			return s.getMajor().equals(major);
		};
		
		Predicate<Person> studentFilter = p -> p instanceof Student;
		
		
		List<Predicate<Person>> filters = new ArrayList<>();
		filters.addAll(Arrays.asList(studentFilter,gpaFilter,nameFilter,majorFilter));
		
		
		return p ->{
			for(Predicate<Person> testPredicate: filters) {
				if(!testPredicate.test(p)) return false;
			}
			
			return true;
		};
		
	}
	
	public static boolean isDouble(String val) {
		try {
			double d = Double.valueOf(val);
			return true;
		}
		
		catch(Exception e){
			return false;
		}
	}



}