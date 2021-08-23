package de.cashback.user;

import java.io.Serializable;

//Userklasse
public abstract class User implements Serializable
{
	//Variablen initialisieren
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	private String password;
	
	
	//Konstruktor
	public User(String username, String email, String password)
	{
		//Variablen setzen
		this.username = username;
		this.email = email;
		this.password = password;
		
	}
	
	//Username abrufen Methode
	public String getName()
	{
		return username;
	}
	
	//Passwort abfragen
	public String getPassword()
	{
		return password;
	}
	
	//Abstrakte Methoden
	//Cashback abfragen
	public abstract Float getCashback();

	//Cashback hinzufügen
	public abstract void addCashback(Float totalcashback);

	//Cashback auszahlen
	public abstract void payout();
	
	//Händer/Konsumentenstatus abfragen
	public abstract String getState();
	
	
	

}
