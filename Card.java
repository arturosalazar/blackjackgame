
public class Card {
	
	private int value;
	private int suit;
	
	//Constructor
	public Card (int value, int suit) 
	{
		this.value = value;
		this.suit = suit;
	}
	
	//Accessor Variables
	public int getValue ()
	{
		return this.value;
	}
	
	public int getSuit ()
	{
		return this.suit;
	}
	
	public String getSuitString () 
	{
		switch(suit) 
		{
			case 0: 
				return "♠️";
			case 1: 
				return "♣️";
			case 2: 
				return "♦️";
			case 3: 
				return "❤️";
			default:
				return "?";
		}
	}
	
	public String getValueString()
	{
		switch(value) 
		{
			case 1: 
				return "Ace";
			case 2: 
				return "2";
			case 3: 
				return "3";
			case 4: 
				return "4";
			case 5: 
				return "5";
			case 6: 
				return "6";
			case 7: 
				return "7";
			case 8: 
				return "8";
			case 9: 
				return "9";
			case 10: 
				return "10";
			case 11: 
				return "Jack";
			case 12: 
				return "Queen";
			case 13: 
				return "King";
			default:
				return "?";
		}
	}
	
	@Override
	public String toString()
	{
		return getSuitString() + getValueString();
	}
}
