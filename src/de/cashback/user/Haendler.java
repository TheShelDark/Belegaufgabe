package de.cashback.user;

//Händlerklasse
public class Haendler extends User
{
	private Float cashback_amount;
	
	//Konstruktor
	public Haendler(String username, String email, String password) {
		super(username, email, password);
		this.cashback_amount = 0.0f;
	}

	//Cashback addieren
	public void addCashback(Float betrag)
	{
		this.cashback_amount = this.cashback_amount + betrag;
	}
	
	//Cashback abrufen
	public Float getCashback()
	{
		return cashback_amount;
	}

	//Geld auszahlen
	public void payout() 
	{
		this.cashback_amount = 0.0f;
	}

	//Status abrufen
	public String getState() 
	{
		return "Haendler";
	}

}
