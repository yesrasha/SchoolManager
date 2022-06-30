package utils;

import java.util.Arrays;

import model.Person;
import model.PersonBag;
import model.Student;
import model.TextbookBag;

public class Demo {
	
	public static void main(String[] args) {
		
		
//		Generator g = new Generator();
//		
//		PersonBag pBag = g.getPersonBag();
//		TextbookBag tBag = g.getTextbookBag();
//		
//		BackUpRestoreTools.backupPersonBag(pBag);
//		BackUpRestoreTools.backupTextbookBag(tBag);
		
		PersonBag pBag = BackUpRestoreTools.restorePersonBag();
		TextbookBag tBag = BackUpRestoreTools.restoreTextbookBag();
		
		if(pBag == null && tBag == null) {
			
			Generator g = new Generator();
			
			 pBag = g.getPersonBag();
			 tBag = g.getTextbookBag();
			 
			 BackUpRestoreTools.backupPersonBag(pBag);
			 BackUpRestoreTools.backupTextbookBag(tBag);
		}
		
		Person[] s = pBag.search(p -> p instanceof Student);
		System.out.println(Arrays.toString(s));
	}

}
