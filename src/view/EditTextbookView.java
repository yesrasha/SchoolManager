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
import utils.Generator;

public class EditTextbookView {
	
	private VBox root;
	private TextbookBag tBag;
	private TextField isbnField, titleField, authorFName,authorLName,priceField;
	private Button updateBtn , backBtn;
	
	public EditTextbookView(TextbookBag tBag,Textbook book) {
		this.tBag = tBag;
		
		isbnField = new TextField();
		isbnField.setPromptText("ISBN");
		isbnField.setText(book.getIsbn());
		
		titleField = new TextField();
		titleField.setPromptText("TITLE");
		titleField.setText(book.getTitle());
		
		authorFName = new TextField();
		authorFName.setPromptText("First Name of Author");
		authorFName.setText(book.getAuthor().getFirstName());
		
		authorLName = new TextField();
		authorLName.setPromptText("Last Name of Author");
		authorLName.setText(book.getAuthor().getLastName());
		
		priceField = new TextField();
		priceField.setPromptText("PRICE");
		priceField.setText(Generator.round(book.getPrice(),2)+" ");
		
		updateBtn = new Button("UPDATE");
		backBtn = new Button("BACK");
		
		updateBtn.setOnAction( e ->{
			// maybe perform isbn validation
			
			String isbn = isbnField.getText().strip(),
				   title = titleField.getText().strip(),
				   firstName = authorFName.getText().strip(),
				   lastName = authorLName.getText().strip();
			
			// number validation required
			
			double price = Double.valueOf(priceField.getText().strip());
			
			//String title, String isbn, Name author, double price
			
			Name authorName = book.getAuthor();
			
			if(!isbn.isBlank()) book.setIsbn(isbn);
			
			if(!title.isBlank()) book.setTitle(title);
			
			if(!firstName.isBlank()) authorName.setFirstName(firstName);
			
			if(!lastName.isBlank()) authorName.setLastName(lastName);
			
			book.setPrice(price);
			
			BackUpRestoreTools.backupTextbookBag(tBag);
			
			backBtn.fire();
		});
		
		backBtn.setOnAction(e ->{
			Main.stage.setScene(new Scene(new TextbookView(tBag).getRoot()));
		});
		
		
		root = new VBox(titleField,isbnField,authorFName,authorLName,priceField,updateBtn,backBtn);
		
		root.setSpacing(4);
		root.setPadding(new Insets(120,50,50,50));
		
	}
	
	public VBox getRoot() {
		return root;
	}

}
