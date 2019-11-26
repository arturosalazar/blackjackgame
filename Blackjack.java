import java.util.Scanner;
import java.util.Random;

//Ver.1 - return true if you win, false if you lose
public class Blackjack {
	//Intance Variables
	public Scanner userInput;
	private Deck deck; 
	private Player dealerPlayer, threePlayer, twoPlayer, onePlayer, userPlayer;
	private Hand dealerHand, threeHand, twoHand, oneHand, userHand;
	private int dealerBet, threeBet, twoBet, oneBet, userBet;
	private Player[] playerArray;
	private Hand[] handArray;
	private int[] betArray;
	private Random randomBetNum;
	private int userIndex;
	final private int AI_CARD_STOP_DRAWING_POINT = 16; //TODO: Maybe we can make this a value for each player to change difficulty
	private String garbage;
	
	//Constructor
	public Blackjack(Player player4, Player player3, Player player2, Player player1, Player player0)
	{
		userInput = new Scanner (System.in);
		deck = new Deck ();
		
		dealerPlayer = player4;
		threePlayer = player3;
		twoPlayer = player2;
		onePlayer = player1;
		userPlayer = player0;
		
		dealerHand = player4.gethand();
		threeHand = player3.gethand();
		twoHand = player2.gethand();
		oneHand = player1.gethand();
		userHand = player0.gethand();
		playerArray = new Player[] {dealerPlayer, threePlayer, twoPlayer, onePlayer, userPlayer};
		handArray = new Hand[] {dealerHand, threeHand, twoHand, oneHand, userHand};
		betArray = new int[] {dealerBet, threeBet, twoBet, oneBet, userBet};
		userIndex = playerArray.length - 1;
		garbage = "";
		deck.shuffle();
		randomBetNum = new Random ();
	}
	
	private void playerWins (Player winningPlayer, int winningIndex) 
	{
		winningPlayer.addchips(betArray[winningIndex]);
	}
	
	private void playerLoses(int winningPlayerIndex) 
	{
		for (int j = 0; j < playerArray.length; j++)
		{
			if (j != winningPlayerIndex) {
				
				playerArray[j].removechips(betArray[j]);
			}
		}
	}
	
	private int randomBet ()
	{
		return randomBetNum.nextInt(75) + 50;
	}
	
	public boolean RoundOfBlackjack() 
	{
		int dealerBet, threeBet, twoBet, oneBet, userBet = 0;
		//store chips balance to compare against input
		int chipsBalance = userPlayer.getchips();
		
		//Set bets for AI players, not user player
		for (int i = 0; i < betArray.length; i++)
		{
			if (i != userIndex)
			{
				betArray[i] = randomBet();
			}
			
		}
		
		//Print out balances for each player
		System.out.println("\nChips Balance : ");
		for (int i = 0; i < playerArray.length - 1; i++)
		{
			String chipsBalanceString = Integer.toString(playerArray[i].getchips());
			String betBalanceString = Integer.toString(betArray[i]);
			System.out.println("\t"+ handArray[i].getId() + " :" + chipsBalanceString + "\tBet: " + betBalanceString);
		}
		
		System.out.println("\n\tYour Chips : " + chipsBalance);
		
		System.out.println("\nMinimum bet is 50 Chips");
				
		do
		{
			System.out.println("\nPlace a whole number bet between 50 and your total chips:");
			while ( !userInput.hasNextInt())
			{
				garbage = userInput.nextLine();
				System.out.println("\nPlace a whole number bet between 50 and your total chips:");
			}
			betArray[userIndex]= userInput.nextInt();
		} while (betArray[userIndex] < 50 || betArray[userIndex] > chipsBalance);
		
		System.out.println("You bet :" + betArray[userIndex] + "\n");
		
		//Deal all the players in
		for (Hand playerHand: handArray)
		{
			//Clear each player's hand before starting a new set
			playerHand.clear();
			playerHand.addCard(deck.dealCards());
			playerHand.addCard(deck.dealCards());
			
			//In traditional blackjack, the Dealer only reveals one card
			if (playerHand.getId() == "Dealer")
			{
				System.out.println(playerHand.getId() +": " + playerHand.getCard(0));
			}
			else 
				System.out.println(playerHand.getId() +": " + playerHand);
		}
		
		//Do an initial check to see if someone won the deal
		for (int i = 0; i < handArray.length; i++)
		{
			if (handArray[i].valueOfHand() == 21 && handArray[i].getId() == "Agent")
			{
				System.out.println("\nYou won!");
				playerWins(playerArray[i], i);
				playerLoses(i);
				return true;
			}
			else if (handArray[i].valueOfHand() == 21)
			{
				System.out.println("\n" + handArray[i].getId() + " got 21 and won. You have lost.");
				playerWins(playerArray[i], i);
				playerLoses(i);
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
						keepGoing = false;
					} else if (userHand.valueOfHand() == 21)
					{
						System.out.println("You won!");
						playerWins(userPlayer, userIndex);
						playerLoses(userIndex);
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
					garbage = userInput.nextLine();
					System.out.print("Invalid input, please choose either \"Hit\" or \"Stand\" >");
				}
			}
			while (!answer.equalsIgnoreCase("Hit") && !answer.equalsIgnoreCase("Stand"));
		}
		
		//The AI will attempt to play - AI will hit while they have less than 16 points.
		//Per above, we can probably change this value to change how well/poorly the AI plays
		for (int i = 0; i < handArray.length; i++)
		{
			if (!(i == userIndex))
			{
				System.out.println("\n" + handArray[i].getId() + "'s turn");
				System.out.println(handArray[i]);
				while (handArray[i].valueOfHand() < AI_CARD_STOP_DRAWING_POINT) {
					handArray[i].addCard(deck.dealCards());
					System.out.println(handArray[i]);
				}
				if (handArray[i].valueOfHand() == 21)
				{
					System.out.println(handArray[i].getId() + " has won. You have lost");
					playerWins(playerArray[i], i);
					playerLoses(i);
					return false;
				} else if (handArray[i].valueOfHand() > 21)
				{
					System.out.println(handArray[i].getId() + " has busted");
				} else	
				{
					System.out.println(handArray[i].getId() + " has completed turn");
				}
			}
		}
		
		//Final comparison
		System.out.println("\nTime to find out who is closest to 21");
		
		//Find the maximum number, ignore anything over 21 points
		Hand winner = handArray[userIndex];
		int winnerIndex = 4;
		for (int i = 0; i < handArray.length; i++)
		{
			if (handArray[i].valueOfHand() <= 21) 
			{
				if (handArray[i].valueOfHand() > winner.valueOfHand() || winner.valueOfHand() > 21)
				{
					winner = handArray[i];
					winnerIndex = i;
					
				}
			}
		}
		
		//Declare the winner
		if (handArray[userIndex].valueOfHand() == winner.valueOfHand())
		{
			System.out.println("You won!\n");
			playerWins(playerArray[winnerIndex], winnerIndex);
			playerLoses(winnerIndex);
			return true;
		}
		else
			System.out.println(winner.getId() + " won. You lost\n");
			playerWins(playerArray[winnerIndex], winnerIndex);
			playerLoses(winnerIndex);

		
	return false;
	}

}


