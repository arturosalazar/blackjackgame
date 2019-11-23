import java.util.Scanner;

//Ver.1 - return true if you win, false if you lose
public class Blackjack {
	//Intance Variables
	public Scanner userInput;
	private Deck deck; 
	private Hand dealerHand;
	private Hand twoHand;
	private Hand threeHand;
	private Hand userHand;
	private Hand[] playersArray;
	final private int AI_CARD_STOP_DRAWING_POINT = 16; //TODO: Maybe we can make this a value for each player to change difficulty
	
	//Constructor
	public Blackjack(String player1, String player2, String player3, String player4)
	{
		userInput = new Scanner (System.in);
		deck = new Deck ();
		dealerHand = new Hand (player1);
		twoHand = new Hand (player2);
		threeHand = new Hand (player3);
		userHand = new Hand (player4);
		playersArray = new Hand[] {dealerHand, twoHand, threeHand, userHand};
		deck.shuffle();
	}
	
	public boolean RoundOfBlackjack() 
	{
		//Deal all the players in
		for (Hand player: playersArray)
		{
			//Clear each player's hand before starting a new set
			player.clear();
			player.addCard(deck.dealCards());
			player.addCard(deck.dealCards());
			
			//In traditional blackjack, the Dealer only reveals one card
			if (player.getId() == "Dealer")
			{
				System.out.println(player.getId() +": " + player.getCard(0));
			}
			else 
				System.out.println(player.getId() +": " + player);
		}
		
		//Do an initial check to see if someone won the deal
		for (Hand player: playersArray)
		{
			if (player.valueOfHand() == 21 && player.getId() == "Agent")
			{
				System.out.println("\nYou won!");
				return true;
			}
			else if (player.valueOfHand() == 21)
			{
				System.out.println("\n" + player.getId() + " got 21 and won. You have lost.");
				return false;
			}		
		}		

		//If no one won the initial deal, user can make hits or stand. Keep asking until user busts or asks to stand
		boolean keepGoing = true;
		while(keepGoing)
		{
			System.out.print("\nHit or Stand? > ");
			String answer;
			do
			{
				answer = userInput.next();
				if (answer.equalsIgnoreCase("Hit"))
				{
					userHand.addCard(deck.dealCards());
					System.out.println(userHand);
					if (userHand.valueOfHand() > 21)
					{
						System.out.println("Bust! You lost");
						return false;
					} else if (userHand.valueOfHand() == 21)
					{
						System.out.println("You won!");
						return true;
					}
				}
				else if (answer.equalsIgnoreCase("Stand"))
				{
					System.out.println("You're standing, Other player's turn");
					keepGoing = false;
				}
				else
				{
					String garbage = userInput.nextLine();
					System.out.print("Invalid input, please choose either \"Hit\" or \"Stand\" >");
				}
			}
			while (!answer.equalsIgnoreCase("Hit") && !answer.equalsIgnoreCase("Stand"));
		}
		
		//The AI will attempt to play - AI will hit while they have less than 16 points.
		//Per above, we can probably change this value to change how well/poorly the AI plays
		for (Hand player: playersArray)
		{
			if (player.getHumanStatus() == false)
			{
				System.out.println("\n" + player.getId() + "'s turn");
				System.out.println(player);
				while (player.valueOfHand() < AI_CARD_STOP_DRAWING_POINT) {
					player.addCard(deck.dealCards());
					System.out.println(player);
				}
				if (player.valueOfHand() == 21)
				{
					System.out.println(player.getId() + " has won. You have lost");
					return false;
				} else if (player.valueOfHand() > 21)
				{
					System.out.println(player.getId() + " has busted");
				} else	
				{
					System.out.println(player.getId() + " has completed turn");
				}
			}
		}
		
		//Final comparison
		System.out.println("\nTime to find out who is closest to 21");
		
		//Find the maximum number, ignore anything over 21 points
		Hand winner = userHand;
		for (Hand player: playersArray)
		{
			if (player.valueOfHand() < 21) 
			{
				if (player.valueOfHand() > winner.valueOfHand())
				{
					winner = player;
				}
			}
		}
		
		//Declare the winner
		if (userHand.valueOfHand() == winner.valueOfHand())
		{
			System.out.println("You won!\n");
			return true;
		}
		else
			System.out.println(winner.getId() + " won. You lost\n");
		
	return false;
	}

}


