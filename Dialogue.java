import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dialogue{
	/*
	in dialoge class, it creates and stores the cleues of enemy, 
	and once a player requests a clues, it returns randomely one of the clues.
	*/

	private int enemyID;
	private String list_clothing;
	private String[][] featuretext = {{"wearing suit","named Sims","parked in area A", "arrived 1 hour ago" , "comes from Illinois","which is a Drug lord","likes Jewelry","study Chemistry"}, {"wearing shirt","named Gardner","parked in area B", "arrived 2 hour ago" , "comes from Indiana","which is a Bank Robber","likes money","study Business"}, {"wearing jacket","named Ross","parked in area C", "arrived 3 hour ago" , "comes from Wisconsin","which is a Hacker ","likes ADHD","study Computer"}, {"wearing hoodie","named Brady","parked in area D", "arrived 4 hour ago" , "comes from Michigan","which is a Cyber stalker","has drug Obsession","study Math"}};
	private String[][] featuretable;
	private List<String> clues;
	

	Dialogue(int enemyID){
		this.enemyID = enemyID;
		this.list_clothing = "";
		this.featuretable = new String[4][8];
		this.clues = new ArrayList<String>();
	}

	//once a player requests a clue, it reorganized the clue list, removing a returned clue.
	public void reorganizeClues(){
		this.clues.remove(0);
	}

	public void setFeaturetable(){
		//based on the data list of AI features, create a new data set of AI features 
		List<Integer> features_index_list = new ArrayList<Integer>();
		
		for(int i=0; i<4; i++){
			features_index_list.add(i);
		} 
		
		for(int i=0; i<8; i++){
      		Collections.shuffle(features_index_list);
			for(int j=0; j<features_index_list.size(); j++){
				this.featuretable[j][i] = this.featuretext[features_index_list.get(j)][i];
			}
		}

	}

	public void setClues(){
		System.out.println("create clues");
		//based on the features table, create a clueList
		int random_opponent = new java.util.Random().nextInt(4);
		int random_feature = new java.util.Random().nextInt(8);

		//store the privious infomation
		int prev_opponent = random_opponent; 
		int prev_feature = random_feature;
		int[] prev_node = {prev_opponent, prev_feature};
		List<int[]> past_node =  new ArrayList<int[]>(); 
		past_node.add(prev_node);

		//this is a starting clue
		this.clues.add("Player " + this.featuretable[random_opponent][0] + " " + this.featuretable[random_opponent][random_feature]);
		


		random_feature = new java.util.Random().nextInt(8);
		int end_feature = random_feature;
		int[] end_node = {this.enemyID, end_feature};
		past_node.add(end_node);
		
		//this is an end clue
		this.clues.add("Enemy agent " + this.featuretable[this.enemyID][random_feature]);

		for(int i=0; i<6; i++){ //to make 10 clues in the list
			int random_number = new java.util.Random().nextInt(2);
			switch(random_number){
				case 0: //same opponent but different feature
					random_feature = new java.util.Random().nextInt(8);
					int[] temp_node0 = {prev_opponent, random_feature};

					while(past_node.contains(temp_node0)){
						random_feature = new java.util.Random().nextInt(8);
						temp_node0[1] = random_feature;
					}

					past_node.add(temp_node0);

					this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is also the player " + this.featuretable[prev_opponent][random_feature]);
					
					prev_feature = random_feature;
					break;

				case 1: //same feature but different opponent
					random_opponent = new java.util.Random().nextInt(4);
					int[] temp_node1 = {random_opponent, prev_feature};

					while(past_node.contains(temp_node1)){
						random_opponent = new java.util.Random().nextInt(4);
						temp_node1[0] = random_opponent;
					}

					past_node.add(temp_node1);

					switch(random_opponent - prev_opponent){
						case -1:
							this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is first on the right hand side of player " + this.featuretable[random_opponent][prev_feature]);
							break;
						case 1:
							this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is first on the left hand side of player " + this.featuretable[random_opponent][prev_feature]);
							break;
						case 2:
						case -3:
							this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is second on the left hand side of player " + this.featuretable[random_opponent][prev_feature]);
							break;
						case 3:
						case -2:
							this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is second on the right hand side of player " + this.featuretable[random_opponent][prev_feature]);
							break;
					}

					prev_opponent = random_opponent;
					break;
			}

		}

		
		int random_number = new java.util.Random().nextInt(2);
		switch(random_number){
			case 0: 
				this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is also the player " + this.featuretable[prev_opponent][end_feature]);

				switch(this.enemyID - prev_opponent){
					case -1:
						this.clues.add("Player " + this.featuretable[prev_opponent][end_feature] + " is first on the right hand side of player " + this.featuretable[this.enemyID][end_feature]);
						break;
					case 1:
						this.clues.add("Player " + this.featuretable[prev_opponent][end_feature] + " is first on the left hand side of player " + this.featuretable[this.enemyID][end_feature]);
						break;
					case 2:
					case -3:
						this.clues.add("Player " + this.featuretable[prev_opponent][end_feature] + " is second on the left hand side of player " + this.featuretable[this.enemyID][end_feature]);
						break;
					case 3:
					case -2:
						this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is second on the right hand side of player " + this.featuretable[this.enemyID][end_feature]);
						break;
				}

				break;

			case 1:
				switch(this.enemyID - prev_opponent){
					case -1:
						this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is first on the right hand side of player " + this.featuretable[this.enemyID][prev_feature]);
						break;
					case 1:
						this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is first on the left hand side of player " + this.featuretable[this.enemyID][prev_feature]);
						break;
					case 2:
					case -3:
						this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is second on the left hand side of player " + this.featuretable[this.enemyID][prev_feature]);
						break;
					case 3:
					case -2:
						this.clues.add("Player " + this.featuretable[prev_opponent][prev_feature] + " is second on the right hand side of player " + this.featuretable[this.enemyID][prev_feature]);
						break;
				}


				this.clues.add("Player " + this.featuretable[this.enemyID][prev_feature] + " is also the player " + this.featuretable[this.enemyID][end_feature]);


				break;
		}

		//reorganize the clue list in a random order
		Collections.shuffle(this.clues);
	}


	//when a player request a clue, it returns a clue at index 0, which is randomly ordered.
	public String getClues(){
		return this.clues.get(0);
	}


	//call it when the game starts to describe the opponent's clothing
	public String toString(){	
		for(int i=0; i<3; i++){
      		list_clothing+=this.featuretable[i][0] + ", ";
    	}
    	list_clothing+=this.featuretable[3][0];
		
		return "Opponents's clothing listed in counterclock wise order: " + list_clothing;
	}



	
}