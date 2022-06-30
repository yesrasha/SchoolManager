package app;

import javafx.application.Application;
import view.InstructorView;
import view.MainMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PersonBag;
import model.TextbookBag;
import utils.BackUpRestoreTools;
import utils.Generator;
import view.StudentView;
import view.TextbookView;

public class Main extends Application {
	
	public static PersonBag pBag;
	public static TextbookBag tBag;
	public static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		Main.stage =stage;
		onFirstStart();
		stage.setScene(new Scene(new MainMenuView(pBag,tBag,stage).getRoot()));
		stage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
	
	public void onFirstStart() {
		
		 pBag = BackUpRestoreTools.restorePersonBag();
		 tBag = BackUpRestoreTools.restoreTextbookBag();
		
		if(pBag == null && tBag == null) {
			
			Generator g = new Generator();
			
			 pBag = g.getPersonBag();
			 tBag = g.getTextbookBag();
			 
			 BackUpRestoreTools.backupPersonBag(pBag);
			 BackUpRestoreTools.backupTextbookBag(tBag);
		}
	}
	
	
	@Override
	public void stop() throws Exception {
		BackUpRestoreTools.backupPersonBag(pBag);
		BackUpRestoreTools.backupTextbookBag(tBag);

		super.stop();
	}

}
