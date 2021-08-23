package de.cashback.software;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import de.cashback.files.FileManager;
import de.cashback.shops.HaushaltsShop;
import de.cashback.shops.LebensmittelShop;
import de.cashback.shops.ModeShop;
import de.cashback.shops.Shop;
import de.cashback.user.Haendler;
import de.cashback.user.Konsument;
import de.cashback.user.User;

public class Menu 
{
	//Variablen initialisieren
	//ArrayList für die Shops
	private ArrayList<Shop> shop_list;
	
	//Eingeloggter User
	User logged_in_user;
	
	//Instanz des Filemanagers
	FileManager fm = new FileManager();
	
	
	
	public Menu()
	{
		//Konstruktor
		//Shops aus Dateien auslesen
		shop_list = fm.ReadShopsFromFile();
	
	}
	
	
	//Erstes Menü - Einlogg- / Registrierungsscreen
	public void firstMenu() throws IOException
	{
		System.out.println("------------------------------------------------------------");
		System.out.println("Willkommen bei BHS Cashback! ;)");
		System.out.println("Willst du dich (E)inloggen oder einen (A)ccount erstellen?");
		System.out.println("------------------------------------------------------------");
		System.out.print("Deine Auswahl: ");
		
		//Auswahl auslesen
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		char login_selection = (char) reader.read();
		
		switch (login_selection) {
		case 'E':
			//Einloggen
			login();
			break;
		case 'A':		
			//Registrieren	
			Register();
			break;
		default:
			System.out.println("Falsche Eingabe! Korrekte eingaben sind: E ; A");
			break;
		}
	}
	
	//Methode zum Einloggen
	public void login() throws IOException 
	{
		//Username einlesen
		System.out.println("------------------------------------------------------------");
		System.out.println("Wie lautet dein Username?");
		
		BufferedReader name = new BufferedReader(new InputStreamReader(System.in));
		String usname = name.readLine();
		
		//Passwort einlesen
		System.out.println("Wie lautet dein Passwort?");
		
		BufferedReader pw = new BufferedReader(new InputStreamReader(System.in));
		String passwd = pw.readLine();
		
		//Instanz des Users erstellen, der sich einloggen möchte
		User loggingUser = fm.ReadUserFromFile(usname);
		if(loggingUser != null) {
			//Wenn User in Datei gefunden wurde:
			if(passwd.equals(loggingUser.getPassword())) {
				//Wenn Passwörter übereinstimmen:
				System.out.println("Erfolgreich eingeloggt");
				//logged_in_user setzen
				logged_in_user = loggingUser;
				//Zum zweiten Screen
				secondMenu();
			} else {
				//Wenn Passwörter nicht übereinstimmen:
				System.out.println("Falscher Username oder Falsches Passwort!");
				firstMenu();
			}
		} else {
			//Wenn User nicht in den Dateien gefunden wurde:
			System.out.println("Ein solcher Account existiert nicht.");
			firstMenu();
		}

	}
	
	//Registrierungsmethode
	public void Register() throws IOException
	{
		//Username einlesen
		System.out.println("------------------------------------------------------------");
		System.out.println("Wie soll dein Username lauten?");
		
		BufferedReader name = new BufferedReader(new InputStreamReader(System.in));
		String usname = name.readLine();
		
		//Email einlesen
		System.out.println("Wie lautet deine E-Mail?");
		
		BufferedReader em = new BufferedReader(new InputStreamReader(System.in));
		String email = em.readLine();
		
		//Passwort einlesen
		System.out.println("Wie soll dein Passwort lauten?");
		
		BufferedReader pw = new BufferedReader(new InputStreamReader(System.in));
		String passwd = pw.readLine();
		
		//Passwort bestätigen
		System.out.println("Bestätige dein Passwort!");
		
		BufferedReader pw_conf = new BufferedReader(new InputStreamReader(System.in));
		String passwd_conf = pw_conf.readLine();
		
		//Händlerstatus Ja/Nein
		System.out.println("Soll es ein Account mit Händlerstatus sein? (J/N)");
		
		BufferedReader h = new BufferedReader(new InputStreamReader(System.in));
		char state = (char) h.read();
		
		switch (state) {
		case 'J':
			//wenn Account Händlerstatus haben soll:
			if (passwd.equals(passwd_conf))
			{
				//Händler anlegen
				Haendler registered = new Haendler(usname, email, passwd);
				//Registrierten User in Datei schreiben
				fm.WriteUserToFile(registered);
				login();
			}
			else 
			{
				System.out.println("Passwörter stimmen nicht überein!");
				firstMenu();
			}
			break;
		case 'N':			
			//Kein Händlerstatus
			if (passwd.equals(passwd_conf))
			{
				Konsument registered = new Konsument(usname, email, passwd);
				fm.WriteUserToFile(registered);
				login();
			}
			else 
			{
				System.out.println("Passwörter stimmen nicht überein!");
				firstMenu();
			}
			break;
		default:
			System.out.println("Falsche Eingabe! Korrekte eingaben sind: J; N");
			break;
		}
		
		
	}
	
	//Zweiter Screen / Hauptscreen
	public void secondMenu() throws IOException 
	{
		while (true) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Was möchtest du als nächstes tun?");
			System.out.println("(S)hops anzeigen; Nach (K)ategorie filtern; (C)ashback-Guthaben anzeigen; Shop zum Einkaufen (A)uswählen; Cashback aus(Z)ahlen; Shop (H)inzufügen");
			System.out.println("------------------------------------------------------------");
			
			//Eingabe einlesen
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			char input = (char) reader.read();
			
			//Menü
			switch (input) {
			case 'S':
				//Shops anzeigen
				display_shops();
				break;
			case 'K':	
				//Filtern		
				filter();
			case 'C':	
				//Cashback Guthaben anzeigen lassen	
				show_Cashback();
				break;
			case 'A':			
				//Shop auswählen -> enkaufen
				goShop();
				break;
			case 'Z':			
				//Cashback auszahlen lassen
				payCashbackOut();
				break;
			case 'H':
				//Shop hinzufügen
				if (logged_in_user.getState().equals("Haendler"))
				{
					//wenn der User Händlerstatus besitzt
					//Daten einlesen
					System.out.println("Wie ist der Name deines Shops?");
					BufferedReader name = new BufferedReader(new InputStreamReader(System.in));
					String shopname = name.readLine();
					
					System.out.println("Wie ist die URL deines Shops?");
					BufferedReader br_url  = new BufferedReader(new InputStreamReader(System.in));
					String url = br_url.readLine();
					
					System.out.println("Wie viel Cashback können die Kunden bei dir bekommen? (in Prozent)");
					Scanner scanner = new Scanner(System.in);
					Float cashback = scanner.nextFloat();
					
					System.out.println("Ist dies eine besondere (zeitlich befristete) Cashback-Aktion? (J/N)");
					BufferedReader br_deal = new BufferedReader(new InputStreamReader(System.in));
					char deal_input = (char) br_deal.read();
					
					Boolean deal = null;
					
					switch (deal_input) {
						case 'J':
							deal = true;
							break;
						case 'N':			
							deal = false;
							break;
					}
					
					//Kategorie einlesen
					System.out.println("Zu welche Kategorie? Haushalt, Mode, Lebensmittel");
					BufferedReader br_kat = new BufferedReader(new InputStreamReader(System.in));
					String kat = br_kat.readLine();
					
					switch (kat) 
					{
						//je nachdem welche Kategorie, Shop erstellen und speichern
						case "Haushalt":
							HaushaltsShop newHShop = new HaushaltsShop(shopname, cashback, url, deal);
							shop_list.add(newHShop);
							fm.WriteShopToFile(newHShop);
							secondMenu();
							break;
						case "Mode":			
							ModeShop newMShop = new ModeShop(shopname, cashback, url, deal);
							shop_list.add(newMShop);
							fm.WriteShopToFile(newMShop);
							secondMenu();
							break;
						case "Lebensmittel":
							LebensmittelShop newLShop = new LebensmittelShop(shopname, cashback, url, deal);
							shop_list.add(newLShop);
							fm.WriteShopToFile(newLShop);
							secondMenu();
							break;
						default:
							System.out.println("Falsche Eingabe! Korrekte eingaben sind: Haushalt ; Mode ; Lebensmittel");
							break;
					}
					
					
				}
				else
				{
					//Kein Händlerstatus:
					System.out.println("Du benötigst einen Händleraccount dafür!");
					break;
				}
			default:
				System.out.println("Falsche Eingabe! Korrekte eingaben sind: S ; K ; C");
				break;
			}
		}

	}
	
	//Shops anzeigen
	public void display_shops()
	{
		//durch Shops durchloopen
		for(int i=0; i<shop_list.size(); i++)
		{
			System.out.println("---------------------");
			System.out.println("Shop "+(i+1)+": ");
			//Anzeigen
			shop_list.get(i).display();
		}
	}
	
	//Shops nach Kategorien filtern
	public void filter() throws IOException
	{
		while (true)
		{
			//Kategorie einlesen
			System.out.println("Welche Kategorie? Haushalt, Mode, Lebensmittel");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String input = reader.readLine();
			
			switch (input) 
			{
				case "Haushalt":
					for(int i=0; i<shop_list.size(); i++)
					{
						//Wenn Kategorie des Shops ... entspricht ; jeweils anzeigen lassen
						if(shop_list.get(i).getKategorie() == "Haushaltswaren") {
							System.out.println("-------------");
							shop_list.get(i).display();
						}
						
					}
					secondMenu();
					break;
				case "Mode":			
					for(int i=0; i<shop_list.size(); i++)
					{
						if(shop_list.get(i).getKategorie() == "Mode") {
							System.out.println("-------------");
							shop_list.get(i).display();
						}
						
						
					}
					secondMenu();
					break;
				case "Lebensmittel":
					for(int i=0; i<shop_list.size(); i++)
					{
						
						if(shop_list.get(i).getKategorie() == "Lebensmittel") {
							System.out.println("-------------");
							shop_list.get(i).display();
						}
								
					}
					secondMenu();
					break;
				default:
					System.out.println("Falsche Eingabe! Korrekte eingaben sind: Haushalt ; Mode ; Lebensmittel");
					break;
			}
		}
	}
	
	//Cashback anzeigen lassen
	public void show_Cashback() 
	{
		System.out.println("Dein akuteller Cashback-Betrag: " + logged_in_user.getCashback());

	}

	//Shops auswählen
	public void goShop() throws IOException
	{
		System.out.println("Bitte gib den Namen des Shops, bei dem du etwas kaufen willst: ");

		//Name einlesen
		BufferedReader shopname = new BufferedReader(new InputStreamReader(System.in));
		String input = (String) shopname.readLine();

		//Passenden Shop finden
		for (int i = 0; i < shop_list.size(); i++) {
			if (shop_list.get(i).getName().equals(input)) {
				Shop chosen = shop_list.get(i);
				System.out.println(input);
				startSaleFunnel(chosen);
			}
			else 
			{

			}
		}
		

		
	}

	//Weiterleitung zum Shop simulieren
	public void startSaleFunnel(Shop shop) throws IOException
	{
		System.out.println("Okay, du wirst nun zur Seite des Shops weitergeleitet. Dein Cashback in Höhe von " + shop.getCashback() + " wurde aktiviert.");

		System.out.println("Für welche Summe möchstest du bei " + shop.getName() + " einkaufen?");

		//Betrag einlesen
		Scanner scanner = new Scanner(System.in);
		Float addcashback = scanner.nextFloat();

		//Cashback mithilfe des Shops berechnen
		Float totalcashback = addcashback * (shop.getCashback() / 100);

		System.out.println("Dein Cashback in Höhe von " + totalcashback + "€ wurde dir auf dein Konto gutgeschrieben.");
		//Cashback zum restlichen Cashback addieren
		logged_in_user.addCashback(totalcashback);

		//User nochmals in File schreiben, um Cashback zu speichern
		fm.WriteUserToFile(logged_in_user);

		//Zurück zum Menü
		secondMenu();
	}

	//Auszahlung simulieren
	public void payCashbackOut() throws IOException {
		System.out.println("Über welches Zahlungsportal möchtest du Cashback auszahlen lassen?");
		System.out.println("(P)aypal oder (B)anküberweisung");
		
		//Zahlungsportal einlesen
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		char input = (char) reader.read();
		
		switch (input) {
		case 'P':
			//User will sich Geld über Paypal auszahlen lassen
			System.out.println("Du wirst nun zu Paypal weitergeleitet...");
			System.out.println("Dein Cashback wurde erfolgreich auf dein Konto ausgezahlt.");
			//Guthaben auszahlen
			logged_in_user.payout();
			//User speichern, damit Cashback bei nächsten login auf 0 ist
			fm.WriteUserToFile(logged_in_user);
			break;
		case 'K':			
			//User will sich Geld per Banküberweisung auszahlen lassen
			System.out.println("Du wirst nun zum Onlinebanking deiner Bank weitergeleitet...");
			System.out.println("Dein Cashback wurde erfolgreich auf dein Konto ausgezahlt.");
			//Guthaben auszahlen
			logged_in_user.payout();
			//User speichern, damit Cashback bei nächsten login auf 0 ist
			fm.WriteUserToFile(logged_in_user);
		default:
			System.out.println("Falsche Eingabe! Korrekte eingaben sind: P; B");
			break;
		}
		
	}
	
	
}
