import java.util.Scanner;

public class BlackJackClient {

	public static void main(String[] args) {
		Scanner userInput = new Scanner (System.in); 
		
		Blackjack blackjack = new Blackjack("Dealer", "Player 2", "Player 3", "Agent");
		
		Boolean playagain = true;
		
		blackjack.RoundOfBlackjack();
		
		while(playagain)
		{
			System.out.print("\nWould you like to keep playing? Yes or No ? > ");
			String answer;
			do
			{
				answer = userInput.next();
				if (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("Y"))
				{
					blackjack.RoundOfBlackjack();
				}
				else if (answer.equalsIgnoreCase("No") || answer.equalsIgnoreCase("N"))
				{
					System.out.println("Ending Blackjack");
					playagain = false;
				}
				else
				{
					String garbage = userInput.nextLine();
					System.out.print("Invalid input, please choose either \"Yes\" or \"No\" >");
				}
			}
			while (!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No"));
		}

	}
}
