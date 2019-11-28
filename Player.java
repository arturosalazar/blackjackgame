
public class Player {
	private Hand hand;
	private String role;
	private int skill;
	private int chips;
	
	public Player (Hand hand, String role, int skill, int chips)
	{
		this.hand = hand;
		this.role = role;
		this.skill = skill;
		this.chips = chips;
	}
	
	
	//Accessor Methods
	public Hand getHand() 
	{
		return this.hand;
	}
	
	public String getRole()
	{
		return this.role;
	}
	
	public int getSkill() 
	{
		return this.skill;
	}
	
	public int getChips()
	{
		return this.chips;
	}
	
	
	//Mutator methods
	public void setChips(int chips)
	{
		this.chips = chips;
	}
	
	public void addChips (int chipsToAdd)
	{
		this.chips += chipsToAdd;
	}
	
	public void removeChips (int chipsToRemove)
	{
		this.chips -= chipsToRemove;
	}
}
