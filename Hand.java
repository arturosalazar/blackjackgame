import java.util.ArrayList;

public class Hand {
	
	private ArrayList <Card> hand;
	private String id;
	private boolean human;
	
	//Constructor
	public Hand(){
		hand = new ArrayList <Card> (2);
	}
	
	public Hand(String id){
		hand = new ArrayList <Card> (2);
		this.id = id;
	}
	
	//Accessors
	public int getCardCount ()
	{
		return hand.size();
	}
	
	public String getId ()
	{
		return id;
	}
	
	public Boolean getHumanStatus()
	{
		return human;
	}
	
	public Card getCard (int position)
	{
		if ( position >= 0 && position < hand.size() )
			return (hand.get(position)); 
		else
			return null;
	}
		
	@Override
	public String toString()
	{
		String handString = "";
		for (Card card: hand)
		{
			handString += card;
		}
		return handString;
	}
	
	//Determine the value of the hand based on blackjack rules
	public int valueOfHand() 
	{
		int value = 0;
		boolean ace = false;
		int handSize = getCardCount();
		
		for ( int i = 0; i < handSize; i++ )
		{
			int cardValue;
			Card card = getCard(i);
			cardValue = card.getValue();
			
			//check if jack, queen or king. If so, set equal to 10
			if (cardValue > 10)
				cardValue = 10;
			
			//check if ace
			if (cardValue == 1)
				ace = true;
			
			value = value + cardValue;
		}
		
		if (ace == true)
		{
			if (value + 10 <=21)
			{
				value += 10;
			}
		}
		return value;
	}
	
	//Mutators
	public void clear()
	{
		hand.clear();
	}
	
	public void addCard( Card newCard )
	{
		hand.add( newCard );
	}
	
}
