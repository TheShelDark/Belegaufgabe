package de.cashback.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import de.cashback.shops.Shop;
import de.cashback.user.User;

//Filemanager Klasse
public class FileManager 
{
	//Konstruktor
	public FileManager()
	{
		//Programmordner
		File directory = new File("C:\\BHS Cashback");
		
		//Wenn Ordner nicht existiert, dann erstellen
		if (!directory.exists())
		{
		    directory.mkdirs();
		}
		
		//Files für User und Shops anlegen
		File userfile = new File("C:\\BHS Cashback\\users");
		File shopfile = new File("C:\\BHS Cashback\\shops");
		
		if(!userfile.exists())
		{
			try {
				userfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!shopfile.exists())
		{
			try {
				shopfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	//User speichern Methode
	public void WriteUserToFile(User user) 
	{
		try 
		{
			//Datei mithilfe von Hashcode für User anlegen
			FileOutputStream fileOut = new FileOutputStream("C:\\BHS Cashback\\user" + user.getName().toLowerCase().hashCode() + ".dat");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			//User Objekt speichern
			objectOut.writeObject(user);
			objectOut.close();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		
	}
	
	//User von Datei lesen
	public User ReadUserFromFile(String username) {
		try {
			//File finden, der selbe Bezeichnung wie übergebener String hat (Hashcode)
			FileInputStream fileIn = new FileInputStream("C:\\BHS Cashback\\user" + username.toLowerCase().hashCode() + ".dat");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			//Userobjekt zurückgeben
			return (User) objectIn.readObject();
		} catch(Exception ex) {
			return null;
		}
	}
	
	//Shop in Datei schreiben
	public void WriteShopToFile(Shop shop)  
	{
		try 
		{
			//Shop mithilfe von Hashcode in Datei schreiben
			FileOutputStream fileOut = new FileOutputStream("C:\\BHS Cashback\\shop" + shop.getName().toLowerCase().hashCode() + ".dat");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(shop);
			objectOut.close();

		}
		catch (Exception ex)
		{
			System.out.println("in catchblock");
			ex.printStackTrace();
		}
		
	}
	
	//Shops auslesen
	public ArrayList<Shop> ReadShopsFromFile() 
	{
		//Programmordner
		File dir = new File("C:\\BHS Cashback");
		//Alle Files in Array auflisten
		File[] directory = dir.listFiles();
		//neue Arraylist (bestehend aus Shops) anlegen
		ArrayList<Shop> shoplist = new ArrayList<>();
		
		//Wenn Ordner existiert
		if (directory != null) {
			//Durch die Files in dem Ordner loopen
			for (File child : directory) 
			{
			  	try 
				{
					//Inputstream erstellen
					FileInputStream fileIn = new FileInputStream(child);
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					//Shops auslesen und zur ArrayList hinzufügen
					Shop shop = (Shop) objectIn.readObject();
					shoplist.add(shop);
			  	}
			    catch (Exception ex)
			    {
				  
			    }
		    }
		} 
		else 
		{
		  System.out.println("Fehler: Ordner existiert nicht.");
		}
		
		//ArrayList zurückgeben
		return shoplist;
		
	}

}
