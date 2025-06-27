package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class FileUtility {
	static ArrayList<String> checkingHistory = new ArrayList<String>();
	static ArrayList<String> savingsHistory = new ArrayList<String>();
	
	public static boolean isValid(String username, String password) {  //checks if username-password is valid 
		File file = new File(System.getProperty("user.dir") + "//src/history.txt"); //finds history.txt in file system
		
		try (Scanner scanner = new Scanner(file)) {  
			while (scanner.hasNextLine()) { 
				
				Scanner scanner2 = new Scanner(scanner.nextLine()); //scans each word in line
				
				if (scanner2.next().equals(username)) {
					if (scanner2.next().equals(password)) {
						scanner2.close();  //prevents memory leaks
						return true;
					}
				}
			} 
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public static String getProperty(String accountName, String propertyName) {
		File file = new File(System.getProperty("user.dir") +"//src/accounts/" + accountName + ".txt"); //gets file
		
		try (Scanner scanner = new Scanner(file)) {
			
			while(scanner.hasNextLine()){
				
				Scanner scanner2 = new Scanner(scanner.nextLine());
				
				if (scanner2.next().equals(propertyName)) {	//checks for property in first index
					String returnString = scanner2.next(); //passes off scanner to string to close scanner
					scanner2.close();
					return returnString;
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "ERROR"; 
	}
	
	public static String updateBalance(String accountName, String accountType) {
		File file = new File(System.getProperty("user.dir") +"//src/accounts/" + accountName + ".txt");
		double balance = 0;
		
		try (Scanner scanner2 = new Scanner(file)) { //looks through account text
			
			while(scanner2.hasNextLine()){
				
				String transaction = scanner2.nextLine(); //saves potential transaction for later
				Scanner scnr = new Scanner(transaction); //looks at each word 
				
				while(scnr.hasNext()) {
					if (scnr.next().equals("transaction")) { //checks if first line is transaction
						if (scnr.next().equals(accountType)) { //and if second line is checking or savings
							
							if (accountType.equals("checking")) { //sorts into correct arrayList
								checkingHistory.add(transaction);	
							}
							
							if (accountType.equals("savings")) {
								savingsHistory.add(transaction);
							}
							balance += Double.valueOf(transaction.substring(transaction.lastIndexOf(" "))); 
						}
					}
				}
				scnr.close();
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return String.format("%.2f", balance);
	}
	
	public static String generateRandom(int length, String validCharacters) {
		Random random = new Random();
		String password = "";
		for (int i=0; i<length; i++) {
			password = password + (validCharacters.charAt(random.nextInt(validCharacters.length()))); //gets random character from validCharacters
		}
		return password;
	}
	
	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy,HH:mm "); 
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void saveCredentials(String accountName, String password){
		FileWriter myWriter; 
		try {
			myWriter = new FileWriter(System.getProperty("user.dir") +"//src/history.txt", true); //writes to file, keeping preserving information
			myWriter.append("\n" + accountName + " " + password); 
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void storeTransaction(String accountName, String accountType, String description, String amount) {
		File file = new File(System.getProperty("user.dir") +"//src/accounts/" + accountName + ".txt"); 
		try {
			FileWriter writer = new FileWriter(file, true);
			writer.append("\ntransaction " + accountType + " " + getTime() + description + amount);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void createAccount(String accountName, String password, String first, String last,Boolean checking, Boolean savings) throws IOException {
		File file  = new File(System.getProperty("user.dir") +"//src/accounts/" + accountName + ".txt");
		file.createNewFile();
		saveCredentials(accountName, password);
		
		FileWriter myWriter = new FileWriter(file);
		
		myWriter.append("accountID " + generateRandom(12, "0123456789") 
			+ "\ncheckingID " + generateRandom(12, "0123456789")
			+ "\nsavingsID " + generateRandom(12, "0123456789")
			+ "\nDate: " + getTime()
			+ "\nFirstName "  + first
			+ "\nLastName "  + last
			+ "\nchecking: " + checking
			+ "\nsavings: " + savings
			+ "\ntransaction checking " + getTime() + "Account,Created 0.0"
			+ "\ntransaction checking " + getTime() + "Account,Created 0.0"
			+ "\ntransaction savings " + getTime() + "Account,Created 0.0"
			+ "\ntransaction savings " + getTime() + "Account,Created 0.0");
		myWriter.close(); 
	}
	
	public static String getPassword(String accountName) {
		File file = new File(System.getProperty("user.dir") +"//src/" + "history.txt"); //gets file
		
		try (Scanner scanner = new Scanner(file)) {
			
			while(scanner.hasNextLine()){
				
				Scanner scanner2 = new Scanner(scanner.nextLine());
				
				if (scanner2.next().equals(accountName)) {	//checks for property in first index
					String returnString = scanner2.next(); //passes off scanner to string to close scanner
					scanner2.close();
					return returnString;
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "ERROR"; 
	}
	
	public static String getHistory(String accountType) {
		if (accountType.equals("checking")) {
			return checkingHistory.get(checkingHistory.size()-1); 
		}
		else {
			return savingsHistory.get(savingsHistory.size()-1);
		}
		
	}
	public static String getHistory2(String accountType) {
		if (accountType.equals("checking")) {
			return checkingHistory.get(checkingHistory.size()-2);
		}
		else {
			return savingsHistory.get(savingsHistory.size()-2);
		}

	}
}
