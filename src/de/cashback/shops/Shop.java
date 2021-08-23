package de.cashback.shops;

import java.io.Serializable;

//Shopklasse
public abstract class Shop implements Serializable
{
	
	//Variablen
	private static final long serialVersionUID = 1L;
	private String name;
	private Float cashback_percentage;
	private String url;
	private boolean deal;
	
	//Konstruktor
	public Shop(String name, Float cashback_percentage, String url, Boolean deal)
	{
		this.name = name;
		this.cashback_percentage = cashback_percentage;
		this.url = url;
		this.deal = deal;
	}
	
	//Name abrufen
	public String getName()
	{
		return name;
	}
	
	//Cashback des Shops abrufen
	public Float getCashback()
	{
		return cashback_percentage;
	}
	
	//Url abrufen
	public String getLink()
	{
		return url;
	}
	
	//Deal zurückgeben
	public String getDeal()
	{
		if (deal == true)
		{
			return "Ja";
		}
		else
		{
			return "Nein";
		}
	}
	
	//Deal setzen
	public void setDeal(boolean deal)
	{
		this.deal = deal;
	}
	
	//Abstrakte Methoden
	abstract public void display();
	abstract public String getKategorie();
	

}
