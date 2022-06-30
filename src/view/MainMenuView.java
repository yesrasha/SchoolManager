package view;
import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PersonBag;
import model.TextbookBag;

public class MainMenuView {
	
	private PersonBag pBag;
	private TextbookBag tBag;
	private VBox root;
	private Button studentsBtn,booksBtn,instructorsBtn;
	private Stage stage;
	
	public MainMenuView(PersonBag pBag, TextbookBag tBag,Stage stage) {
		this.pBag = pBag;
		this.tBag = tBag;
		this.stage = stage;
		
		studentsBtn = new Button("Manage Students");
		booksBtn = new Button("Manage Textbooks");
		instructorsBtn = new Button("Manage Instructors");
		
		Arrays.asList(studentsBtn,booksBtn,instructorsBtn).forEach(b -> b.setPrefSize(200, 50));
		
		studentsBtn.setOnAction( e ->{
			stage.setScene(new Scene(new StudentView(pBag).getRoot()));
			stage.show();
		});
		
		booksBtn.setOnAction( e ->{
			stage.setScene(new Scene(new TextbookView(tBag).getRoot()));
			stage.show();
		});
		
		instructorsBtn.setOnAction( e ->{
			stage.setScene(new Scene(new InstructorView(pBag).getRoot()));
			stage.show();
		});
		
		root = new VBox(studentsBtn,booksBtn,instructorsBtn);
		root.setSpacing(7);
		root.setPadding(new Insets(120,50,50,50));
		root.setAlignment(Pos.CENTER);
		root.setPrefSize(500, 500);
	}
	
	
	public VBox getRoot() {
		return root;
	}

}
