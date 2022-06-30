
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
import model.Instructor;
import model.Name;
import model.Person;
import model.PersonBag;
import utils.Generator;

//Name name,String rank,double salary

public class InstructorView {
	private PersonBag pBag;
	private VBox root;
	private TextField salaryField, nameField, outputField;

	public InstructorView(PersonBag personBag) {
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
		removeAllBtn.setPrefSize(100, 30);
		
		Button backBtn = new Button("MAIN MENU");
		backBtn.setPrefSize(100, 30);
		
		backBtn.setOnAction(e ->{
			Main.stage.setScene(new Scene(new MainMenuView(Main.pBag,Main.tBag,Main.stage).getRoot()));
			Main.stage.show();
		});

		HBox btnBox = new HBox(30);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(insertBtn, searchBtn, updateBtn, removeBtn, removeAllBtn,backBtn);

		nameField = new TextField();
		nameField.setPromptText("NAME");
		nameField.setPrefSize(150, 30);

		salaryField = new TextField();
		salaryField.setPrefSize(150, 30);
		salaryField.setPromptText("SALARY");

		HBox inputBox = new HBox(20);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(nameField, salaryField);

		outputField = new TextField();
		outputField.setMaxSize(300, 30);
		TextArea outputArea = new TextArea();
		outputArea.setMaxSize(600, 300);
		outputArea.setEditable(false);

		VBox outputBox = new VBox(20);
		outputBox.setAlignment(Pos.CENTER);
		outputBox.getChildren().addAll(outputField, outputArea);

		insertBtn.setOnAction(e -> {
			AddInstructorView edit = new AddInstructorView(pBag);
			Main.stage.setScene(new Scene(edit.getRoot(), 500, 500));
			Main.stage.show();
		});

		searchBtn.setOnAction(e -> {

			Person[] instructors = Main.pBag.search(combinedPredicate());

			StringBuilder sb = new StringBuilder();

			for (Person i : instructors)
				sb.append(i.getId() + "\t\t" + i.getName().getFirstName() + "\t\t"
						+ i.getName().getLastName() + "\t\t" + ((Instructor) i).getRank() + "\t\t"
						+ Generator.round(((Instructor) i).getSalary(),2) + "\n");
			
			outputArea.setText(sb.toString());

		});

		removeBtn.setOnAction(e -> {

			// remove by id
			String id = outputField.getText();
			pBag.delete(s -> s.getId().equals(id));
		});

		removeAllBtn.setOnAction(e -> {
			// removes everything based on the combined filters
			pBag.delete(combinedPredicate());
		});

		searchBtn.fire();

		updateBtn.setOnAction(e -> {
			String id = outputField.getText();
			Instructor s = (Instructor) personBag.search(s1 -> s1.getId().equals(id))[0];
			EditInstructorView edit = new EditInstructorView(s, pBag);
			Main.stage.setScene(new Scene(edit.getRoot(), 500, 500));
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

	private Predicate<Person> combinedPredicate() {
		String name = nameField.getText();

		// CREATE PREDICATES DEPENDING ON IF THE GPA FIELD IS EMPTY OR NOT , NAME FIELD
		// IS EMPTY OR NOT AND VALID

		Predicate<Person> nameFilter = p -> {
			Instructor s = (Instructor) p;
			if (name.isBlank())
				return true;
			return s.getName().toString().contains(name);
		};

		Predicate<Person> salaryFilter = p -> {
			Instructor s = (Instructor) p;
			String gpaString = salaryField.getText().strip();
			if (gpaString.isBlank())
				return true;

			if (isDouble(gpaString))
				return s.getSalary() == Double.valueOf(gpaString);

			String firstTwo = gpaString.substring(0, 2);

			double salaryVal = Double.valueOf(gpaString.substring(2));

			if (firstTwo.equals("<="))
				return s.getSalary() <= salaryVal;

			else if (firstTwo.equals(">="))
				return s.getSalary() >= salaryVal;

			else {
				salaryVal = Double.valueOf(gpaString.substring(1));
				if (gpaString.charAt(0) == '<')
					return s.getSalary() < salaryVal;
			}

			return s.getSalary() > salaryVal;

		};

		Predicate<Person> rankFilter = p -> {

			Instructor s = (Instructor) p;
			String rank = outputField.getText();
			if (rank.isBlank())
				return true;
			return s.getRank().equals(rank);
		};
		
		 Predicate<Person> instructorFilter = p -> p instanceof Instructor;

		List<Predicate<Person>> filters = new ArrayList<>();
		filters.addAll(Arrays.asList(instructorFilter, salaryFilter, nameFilter, rankFilter));

		return p -> {
			for (Predicate<Person> testPredicate : filters) {
				if (!testPredicate.test(p))
					return false;
			}

			return true;
		};

	}

	public static boolean isDouble(String val) {
		try {
			double d = Double.valueOf(val);
			return true;
		}

		catch (Exception e) {
			return false;
		}
	}

}

