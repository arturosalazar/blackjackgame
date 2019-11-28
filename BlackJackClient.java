import java.util.Random;

public class BlackJackClient {
	public static void main(String[] args) {
		Random random = new Random ();

		//set up players - 5 players including one human 
		Player[] players = new Player[5];
		
		//Set up human player and put player in players array
		Player human = new Player(new Hand ("Agent") , "Human" , 0 , 1000);
		players[0] = human;
		
		
		// randomly assign AITendency
		int[] tendencyArray = new int[4];
		for (int i=0; i <= 3; i++) {tendencyArray[i]=(int)(Math.random()*4 +0);}//assume AITendency range from 0 to 4

		//complete players array
		for (int i = 1; i < 5; i++) {
			String playerRole = "Player " + i;
			int playerSkill = (int) (random.nextInt()*4);
			if (i == 4) {
				playerRole = "Dealer";
			}
			players[i] = new Player(new Hand(), playerRole, playerSkill, 1000);
		}
		
		int game_end = 0;
		int round_count = 0;

		while (game_end == 0) {
			round_count++;
			System.out.println("\nRound "+ round_count);
			
			// blackjack game
			Blackjack blackjack = new Blackjack(players[4], players[3], players[2], players[1], players[0]);
			
			blackjack.RoundOfBlackjack();
			
			if (players[0].getChips() <= 0)
			{
				System.out.println("\n\nYou are out of money and have lost the game :(");
				game_end = 1;
				break;
			} else if (players[0].getChips() >= 2000)
			{
				System.out.println("\n\nYour have 2000 chips and have won the game");
				game_end = 1;
				break;
			}
			
		}		
	}
}
