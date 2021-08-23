package de.cashback.user;

//Konsumentenklasse
//Tochter von User
public class Konsument extends User
{

	private Float cashback_amount;
	
	//Konstruktor
	public Konsument(String username, String email, String password) {
		super(username, email, password);
		this.cashback_amount = 0.0f;
	}
	
	//Cashback abrufen
	public Float getCashback()
	{
		return cashback_amount;
	}
	
	//Cashback addieren
	public void addCashback(Float betrag)
	{
		this.cashback_amount = this.cashback_amount + betrag;
	}

	//Geld auszahlen
	public void payout() 
	{
		this.cashback_amount = 0.0f;
	}

	//Status abrufen
	public String getState() 
	{
		return "Konsument";
	}
}
