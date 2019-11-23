
public class Deck {
	private Card[] deck;
	private int cardsUsed;
	
	//Constructor
	public Deck () 
	{
		deck = new Card[ 52 ];
		int cardCount = 0;
		for ( int suit = 0; suit < 4; suit++ )
		{
			for ( int cardValue = 1; cardValue < 14; cardValue++ )
			{
				deck[cardCount] = new Card(cardValue, suit);
				cardCount++;
			}
		}
		cardsUsed = 0;
	}
	
	//Accessor
	public int cardsLeft () {
		return 52 - cardsUsed;
	}
	
	@Override
	public String toString() 
	{
		String deckString = "";
		for (int i = 0; i < deck.length; i++)
		{
			deckString += "\n" + deck[i];
		}
		return deckString;
	}
	
	//Mutators
	public void shuffle() {
		for ( int i = 51; i > 0; i-- )
		{
			int random = (int)(Math.random() * ( i + 1 )); //Use (int) to convert double into an integer value with no tail
			Card temp = deck[ i ];
			deck[ i ] = deck[ random ];
			deck[ random ] = temp;
		}
		cardsUsed = 0;
	}
	
	public Card dealCards()
	{
		if ( cardsUsed == 52 )
		{
			this.shuffle();
		}
		cardsUsed++;
		return deck[ cardsUsed-1 ];
	}
}
