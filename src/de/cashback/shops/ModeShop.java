package de.cashback.shops;

//Shop der Kategorie "Mode"
public class ModeShop extends Shop
{

	//Konstruktor
	public ModeShop(String name, Float cashback_percentage, String url, Boolean deal) 
	{
		super(name, cashback_percentage, url, deal);
	}
	
	//Kategorie abrufen
	public String getKategorie()
	{
		return "Mode";
	}
	
	//Display-Methode
	public void display() 
	{
		System.out.print("Name: " + this.getName() + "\n");
		System.out.print("Kategorie: " + this.getKategorie() + "\n");
		System.out.print("Aktueller Cashback: " + this.getCashback() + "\n");
		System.out.print("Besonderer Deal? " + this.getDeal() + "\n");
		System.out.print("URL: " + this.getLink() + "\n");
	}

}
