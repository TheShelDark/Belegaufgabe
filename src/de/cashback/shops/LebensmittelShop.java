package de.cashback.shops;

//Shop der Kategorie "Lebensmittel"
public class LebensmittelShop extends Shop
{
	//Konstruktor
	public LebensmittelShop(String name, Float cashback_percentage, String url, Boolean deal) 
	{
		super(name, cashback_percentage, url, deal);
	}
	
	//Kategorie abrufen
	public String getKategorie()
	{
		return "Lebensmittel";
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
