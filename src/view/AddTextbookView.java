package view;

import app.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Name;
import model.Textbook;
import model.TextbookBag;
import utils.BackUpRestoreTools;

public class AddTextbookView {
	
	private VBox root;
	private TextbookBag tBag;
	private TextField isbnField, titleField, authorFName,authorLName,priceField;
	private Button addBtn , backBtn;
	
	public AddTextbookView(TextbookBag tBag) {
		this.tBag = tBag;
		
		isbnField = new TextField();
		isbnField.setPromptText("ISBN");
		
		titleField = new TextField();
		titleField.setPromptText("TITLE");
		
		authorFName = new TextField();
		authorFName.setPromptText("First Name of Author");
		
		authorLName = new TextField();
		authorLName.setPromptText("Last Name of Author");
		
		priceField = new TextField();
		priceField.setPromptText("PRICE");
		
		addBtn = new Button("ADD");
		backBtn = new Button("BACK");
		
		addBtn.setOnAction( e ->{
			// maybe perform isbn validation
			
			String isbn = isbnField.getText().strip(),
				   title = titleField.getText().strip(),
				   firstName = authorFName.getText().strip(),
				   lastName = authorLName.getText().strip();
			
			// number validation required
			
			double price = Double.valueOf(priceField.getText().strip());
			
			//String title, String isbn, Name author, double price
			
			Textbook newBook = new Textbook(title,
											isbn,
											new Name(firstName,lastName),
											price);
			tBag.insert(newBook);
			
			BackUpRestoreTools.backupTextbookBag(tBag);
			
			backBtn.fire();
		});
		
		backBtn.setOnAction(e ->{
			Main.stage.setScene(new Scene(new TextbookView(tBag).getRoot()));
		});
		
		
		root = new VBox(titleField,isbnField,authorFName,authorLName,priceField,addBtn,backBtn);
		
		root.setSpacing(4);
		root.setPadding(new Insets(120,50,50,50));
		
	}
	
	public VBox getRoot() {
		return root;
	}

}
