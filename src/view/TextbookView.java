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
import model.Textbook;
import model.TextbookBag;
import model.Textbook;
import model.Textbook;

public class TextbookView {
	
	private TextbookBag tBag;
	private VBox root;
	private TextField isbnField,titleField,authorNameField,priceField;
	public TextbookView(TextbookBag textbookBag) {
		this.tBag = textbookBag;	
		
		
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
		
	    titleField = new TextField();
		titleField.setPromptText("TITLE");
		titleField.setPrefSize(150, 30);
		
		isbnField = new TextField();
		isbnField.setPrefSize(150, 30);
		isbnField.setPromptText("ISBN");
		
		authorNameField = new TextField();
		authorNameField.setPromptText("AUTHOR NAME");
		authorNameField.setPrefSize(150, 30);
		
		priceField = new TextField();
		priceField.setPromptText("PRICE");
		priceField.setPrefSize(150, 30);
		
		HBox inputBox = new HBox(20);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(titleField, isbnField,authorNameField,priceField);
		
		TextArea outputArea = new TextArea();
		outputArea.setMaxSize(600, 300);
		outputArea.setEditable(false);
		
		VBox outputBox = new VBox(20);
		outputBox.setAlignment(Pos.CENTER);
		outputBox.getChildren().addAll(outputArea);
		
		insertBtn.setOnAction(e ->{
			AddTextbookView edit = new AddTextbookView(tBag);
			Main.stage.setScene(new Scene(edit.getRoot(),500,500));
			Main.stage.show();
		});
		
		searchBtn.setOnAction(e ->{
			Textbook[] b = Main.tBag.search(combinedPredicate());
			StringBuilder sb = new StringBuilder();
			for(Textbook book: b) sb.append(book.getTitle() + "\t\t" + book.getIsbn() + "\t\t"+book.getAuthor().getFirstName() +" " +book.getAuthor().getLastName()+ "\n");
			outputArea.setText(sb.toString());
			
		});
		
		removeBtn.setOnAction(e ->{
			
			// remove by id
			String id = isbnField.getText();
			tBag.delete(s -> s.getIsbn().equals(id));
		});
		
		removeAllBtn.setOnAction(e ->{
			// removes everything based on the predicates
			tBag.delete(combinedPredicate());
		});
		
		
		
		//searchBtn.fire();
		
		updateBtn.setOnAction(e ->{
			String isbn = isbnField.getText();
			Textbook b = textbookBag.search(s1 -> s1.getIsbn().equals(isbn))[0];
			EditTextbookView edit = new EditTextbookView(tBag,b);
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
	
	private Predicate<Textbook> combinedPredicate(){
		String title = titleField.getText();
		String isbn = isbnField.getText();
		String authorName = authorNameField.getText().strip();
		
		
		Predicate<Textbook> titleFilter = b ->{
			if(title.isBlank()) return true;
			return b.getTitle().contains(title);
		};
		
		Predicate<Textbook> isbnFilter = b ->{
			if(isbn.isBlank()) return true;
			return b.getIsbn().equals(isbn);
		};
		
		Predicate<Textbook> authorFilter = b ->{
			if(authorName.isBlank()) return true;
			return b.getAuthor().toString().contains(authorName);
		};
		
		Predicate<Textbook> priceFilter = b ->{
			
		
			String priceString = priceField.getText().strip();
			if(priceString.isBlank()) return true;
			
			if(isDouble(priceString)) return b.getPrice() == Double.valueOf(priceString);
			
			String firstTwo = priceString.substring(0,2);
			
			double priceVal = Double.valueOf(priceString.substring(2));
			
			if(firstTwo.equals("<=")) return b.getPrice() <= priceVal;
			
			else if(firstTwo.equals(">=")) return b.getPrice() >= priceVal;
			
			else {
				priceVal = Double.valueOf(priceString.substring(1));
				if(priceString.charAt(0) == '<') return b.getPrice() < priceVal;
			}
			
			return b.getPrice() > priceVal;
		};
		
		
		List<Predicate<Textbook>> filters = new ArrayList<>();
		filters.addAll(Arrays.asList(titleFilter,isbnFilter,authorFilter,priceFilter));
		
		
		return b ->{
			for(Predicate<Textbook> testPredicate: filters) {
				if(!testPredicate.test(b) )return false;
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